package bernard.tatin;

import java.lang.annotation.Native;

public class ProcessID {
    private static ProcessID ourInstance = new ProcessID();
    private int pid = -1;

    private int innerGetPID() {
        return pid;
    }

    public static int getPID() {
        return ourInstance.innerGetPID();
    }

    private interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary) Native.loadLibrary("c", CLibrary.class);
        int getpid ();
    }

    private ProcessID() {
        pid = CLibrary.INSTANCE.getpid();
    }
}
