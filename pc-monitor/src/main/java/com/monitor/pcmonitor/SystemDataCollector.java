package com.monitor.pcmonitor;

import com.monitor.pcmonitor.SystemMetrics.ProcessInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Bean Spring che raccoglie le metriche di sistema ogni 2 secondi usando OSHI.
 * L'ultimo snapshot è sempre disponibile via {@link #getLatestMetrics()}.
 */
@Component
public class SystemDataCollector {

    private static final Logger log = LoggerFactory.getLogger(SystemDataCollector.class);
    private static final int SAMPLE_INTERVAL_SEC = 2;
    private static final int TOP_PROCESSES       = 5;

    // OSHI objects (thread-safe per lettura dopo init)
    private final SystemInfo              si        = new SystemInfo();
    private final HardwareAbstractionLayer hal       = si.getHardware();
    private final CentralProcessor        processor = hal.getProcessor();
    private final GlobalMemory            memory    = hal.getMemory();
    private final OperatingSystem         os        = si.getOperatingSystem();

    // Ticks precedenti per il calcolo delta CPU
    private long[] prevTicks = processor.getSystemCpuLoadTicks();

    // Ultimo snapshot disponibile (accesso concorrente)
    private final AtomicReference<SystemMetrics> latest = new AtomicReference<>(new SystemMetrics());

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "metrics-collector");
        t.setDaemon(true);
        return t;
    });

    @SuppressWarnings("unused")
    private ScheduledFuture<?> task;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @PostConstruct
    public void start() {
        log.info("SystemDataCollector avviato — intervallo {} s", SAMPLE_INTERVAL_SEC);
        task = scheduler.scheduleAtFixedRate(this::collect, 0, SAMPLE_INTERVAL_SEC, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void stop() {
        scheduler.shutdownNow();
        log.info("SystemDataCollector fermato.");
    }

    // ── Core collection ───────────────────────────────────────────────────────

    private void collect() {
        try {
            SystemMetrics m = new SystemMetrics();

            // ── CPU ──────────────────────────────────────────────────────────
            long[] ticks   = processor.getSystemCpuLoadTicks();
            double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100.0;
            prevTicks      = ticks;
            m.setCpuLoad(Math.round(cpuLoad * 10.0) / 10.0);   // 1 decimale

            // ── RAM ──────────────────────────────────────────────────────────
            long totalRamB = memory.getTotal();
            long freeRamB  = memory.getAvailable();
            long usedRamB  = totalRamB - freeRamB;

            m.setRamTotal(toMB(totalRamB));
            m.setRamFree (toMB(freeRamB));
            m.setRamUsed (toMB(usedRamB));

            // ── Disco ─────────────────────────────────────────────────────────
            long diskTotalB = 0, diskFreeB = 0;
            for (OSFileStore fs : os.getFileSystem().getFileStores()) {
                diskTotalB += fs.getTotalSpace();
                diskFreeB  += fs.getUsableSpace();
            }
            long diskUsedB = diskTotalB - diskFreeB;
            m.setDiskTotal(toMB(diskTotalB));
            m.setDiskFree (toMB(diskFreeB));
            m.setDiskUsed (toMB(diskUsedB));

            // ── Top-5 processi per RAM ────────────────────────────────────────
            List<ProcessInfo> top = os.getProcesses(null, OperatingSystem.ProcessSorting.RSS_DESC, TOP_PROCESSES)
                    .stream()
                    .map(p -> new ProcessInfo(p.getProcessID(), p.getName(), toMB(p.getResidentSetSize())))
                    .toList();
            m.setTopProcesses(top);

            latest.set(m);

            log.debug("CPU: {}%  RAM: {}/{} MB  Disco: {}/{} MB",
                    m.getCpuLoad(), m.getRamUsed(), m.getRamTotal(),
                    m.getDiskUsed(), m.getDiskTotal());

        } catch (Exception e) {
            log.error("Errore durante la raccolta delle metriche", e);
        }
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /** Restituisce l'ultimo snapshot disponibile (mai null). */
    public SystemMetrics getLatestMetrics() {
        return latest.get();
    }

    /**
     * Termina un processo per PID usando ProcessHandle (API standard Java 9+).
     * @return true se il processo è stato trovato e il segnale di terminazione inviato.
     */
    public boolean killProcess(int pid) {
        // Verifica prima tramite OSHI che il processo esista
        OSProcess oshiProc = os.getProcess(pid);
        if (oshiProc == null) {
            log.warn("Processo PID {} non trovato.", pid);
            return false;
        }
        String procName = oshiProc.getName();

        // Usa ProcessHandle (Java 9+) per terminare il processo
        return ProcessHandle.of(pid).map(handle -> {
            boolean destroyed = handle.destroy();
            if (destroyed) {
                log.info("Processo PID {} ({}) terminato con successo.", pid, procName);
            } else {
                log.warn("Impossibile terminare il processo PID {} ({}).", pid, procName);
            }
            return destroyed;
        }).orElseGet(() -> {
            log.warn("ProcessHandle per PID {} non disponibile.", pid);
            return false;
        });
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    private static long toMB(long bytes) {
        return bytes / (1024L * 1024L);
    }
}