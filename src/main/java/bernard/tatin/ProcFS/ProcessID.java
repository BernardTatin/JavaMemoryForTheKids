package bernard.tatin;


public class ProcessID {
    final private static ProcessID ourInstance = new ProcessID();
    private long pid = -1;

    private long innerGetPID() {
        if (pid == -1) {
            String processName =
                    java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            pid = Long.parseLong(processName.split("@")[0]);
        }
        return pid;
    }

    public static long getPID() {
        return ProcessID.ourInstance.innerGetPID();
    }


    private ProcessID() {
    }
}
