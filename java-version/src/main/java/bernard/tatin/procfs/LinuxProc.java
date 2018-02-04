package bernard.tatin.procfs;

import java.nio.file.Path;
import java.nio.file.Paths;

class LinuxProc {
    private static final LinuxProc ourInstance = new LinuxProc();
    private final long pid = ProcessID.getPID();
    private final String strPid = String.valueOf(pid);

    private Path innerProcPathName(String path) {
        return Paths.get("/proc/" + strPid + "/" + path);
    }

    public static Path procPathName(String path) {
        return ourInstance.innerProcPathName(path);
    }

    private LinuxProc() {

    }
}
