package bernard.tatin.ProcFS;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LinuxProc {
    final private static LinuxProc ourInstance = new LinuxProc();
    final private long pid = ProcessID.getPID();

    private Path innerProcPathName(String path) {
        return Paths.get("/proc/" + String.valueOf(pid) + "/" + path);
    }

    public static Path procPathName(String path) {
        return ourInstance.innerProcPathName(path);
    }

    public static void busySleep(long nanos) {
        long elapsed;
        final long startTime = System.nanoTime();
        do {
            elapsed = System.nanoTime() - startTime;
        } while (elapsed < nanos);
    }

    private LinuxProc() {

    }
}
