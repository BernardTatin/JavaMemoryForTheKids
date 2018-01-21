package bernard.tatin.procfs;

import java.nio.file.Path;
import java.nio.file.Paths;

class LinuxProc {
    final private static LinuxProc ourInstance = new LinuxProc();
    final private long pid = ProcessID.getPID();

    private Path innerProcPathName(String path) {
        return Paths.get("/proc/" + String.valueOf(pid) + "/" + path);
    }

    public static Path procPathName(String path) {
        return ourInstance.innerProcPathName(path);
    }

    private LinuxProc() {

    }
}
