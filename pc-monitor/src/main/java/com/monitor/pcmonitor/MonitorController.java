package com.monitor.pcmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller — espone le metriche di sistema e il kill dei processi.
 *
 * GET  /api/status       → snapshot corrente (JSON)
 * POST /api/kill/{pid}   → termina il processo con il PID indicato
 */
@RestController
@RequestMapping("/api")
public class MonitorController {

    private static final Logger log = LoggerFactory.getLogger(MonitorController.class);

    private final SystemDataCollector collector;

    public MonitorController(SystemDataCollector collector) {
        this.collector = collector;
    }

    /**
     * Restituisce l'ultimo snapshot delle metriche di sistema.
     * Esempio risposta:
     * {
     *   "cpu": 15.3,
     *   "ram_total": 16384, "ram_used": 8192, "ram_free": 8192,
     *   "disk_total": 512000, "disk_used": 250000, "disk_free": 262000,
     *   "top_processes": [ {"pid":1234,"name":"chrome.exe","ramUsedMb":512}, ... ]
     * }
     */
    @GetMapping("/status")
    public ResponseEntity<SystemMetrics> getStatus() {
        return ResponseEntity.ok(collector.getLatestMetrics());
    }

    /**
     * Termina il processo identificato dal PID.
     * Restituisce 200 OK se ucciso, 404 se non trovato, 403 se accesso negato.
     */
    @PostMapping("/kill/{pid}")
    public ResponseEntity<Map<String, Object>> killProcess(@PathVariable int pid) {
        log.info("Richiesta di terminazione del processo PID {}", pid);

        // Protezione: non permette di terminare il processo JVM corrente
        if (pid == ProcessHandle.current().pid()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Non puoi terminare il processo del monitor stesso."));
        }

        boolean killed = collector.killProcess(pid);
        if (killed) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Processo " + pid + " terminato."));
        } else {
            return ResponseEntity.status(403)
                    .body(Map.of("success", false, "message",
                            "Impossibile terminare PID " + pid + ". Permessi insufficienti o processo già terminato."));
        }
    }
}
