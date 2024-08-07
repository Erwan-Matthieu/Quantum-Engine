package test;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class CPUMonitor {
    public static void main(String[] args) {
        OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();
        
        double systemCpuLoad = osMxBean.getSystemLoadAverage() / osMxBean.getAvailableProcessors();
        
        System.out.println("System CPU Load: " + (systemCpuLoad * 100) + "%");
    }
}
