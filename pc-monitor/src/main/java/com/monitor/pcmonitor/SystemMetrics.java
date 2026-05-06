package com.monitor.pcmonitor;

import java.util.List;

/**
 * DTO che rappresenta uno snapshot delle metriche di sistema.
 * Tutte le dimensioni di memoria/disco sono in MB.
 */
public class SystemMetrics {

    // CPU
    private double cpuLoad;           // percentuale 0-100

    // RAM (MB)
    private long ramTotal;
    private long ramUsed;
    private long ramFree;

    // Disco (MB) — somma di tutti i drive fisici
    private long diskTotal;
    private long diskUsed;
    private long diskFree;

    // Top-5 processi per RAM
    private List<ProcessInfo> topProcesses;

    // ── Constructors ──────────────────────────────────────────────────────────

    public SystemMetrics() {}

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public double getCpuLoad()                    { return cpuLoad; }
    public void   setCpuLoad(double cpuLoad)      { this.cpuLoad = cpuLoad; }

    public long getRamTotal()                     { return ramTotal; }
    public void setRamTotal(long ramTotal)        { this.ramTotal = ramTotal; }

    public long getRamUsed()                      { return ramUsed; }
    public void setRamUsed(long ramUsed)          { this.ramUsed = ramUsed; }

    public long getRamFree()                      { return ramFree; }
    public void setRamFree(long ramFree)          { this.ramFree = ramFree; }

    public long getDiskTotal()                    { return diskTotal; }
    public void setDiskTotal(long diskTotal)      { this.diskTotal = diskTotal; }

    public long getDiskUsed()                     { return diskUsed; }
    public void setDiskUsed(long diskUsed)        { this.diskUsed = diskUsed; }

    public long getDiskFree()                     { return diskFree; }
    public void setDiskFree(long diskFree)        { this.diskFree = diskFree; }

    public List<ProcessInfo> getTopProcesses()              { return topProcesses; }
    public void setTopProcesses(List<ProcessInfo> topProcesses) { this.topProcesses = topProcesses; }

    // ── Inner class: ProcessInfo ──────────────────────────────────────────────

    public static class ProcessInfo {
        private int    pid;
        private String name;
        private long   ramUsedMb;   // MB di RAM residenti

        public ProcessInfo() {}

        public ProcessInfo(int pid, String name, long ramUsedMb) {
            this.pid       = pid;
            this.name      = name;
            this.ramUsedMb = ramUsedMb;
        }

        public int    getPid()                  { return pid; }
        public void   setPid(int pid)           { this.pid = pid; }

        public String getName()                 { return name; }
        public void   setName(String name)      { this.name = name; }

        public long   getRamUsedMb()            { return ramUsedMb; }
        public void   setRamUsedMb(long mb)     { this.ramUsedMb = mb; }
    }
}
