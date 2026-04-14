package com.monitor.pcmonitor;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class SystemDataCollector {
    public static void main(String[] args) throws InterruptedException {
        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();
        System.out.println("Current Processor: " + processor.getProcessorIdentifier().getName());

        long[] ticks = processor.getSystemCpuLoadTicks();
        Thread.sleep(1000);
        double bw_ticks = processor.getSystemCpuLoadBetweenTicks(ticks) * 100;
        System.out.println("Current percentual ticks: " + bw_ticks);
    }
}