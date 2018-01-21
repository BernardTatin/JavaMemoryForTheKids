package bernard.tatin.procfs;


public class ProcessID {
    private static long pid = -1;

    public static long getPID() {
        if (pid == -1) {
            String processName =
                    java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            pid = Long.parseLong(processName.split("@")[0]);
        }
        return pid;
    }


    private ProcessID() {
    }
}
