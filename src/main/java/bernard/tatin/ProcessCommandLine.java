package bernard.tatin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessCommandLine {
    private static ProcessCommandLine ourInstance = new ProcessCommandLine();
    private long pid = ProcessID.getPID();
    private String commandLine = null;

    private Path procPathName()  {
        return Paths.get("/proc/" + String.valueOf(pid) + "/cmdline");
    }

    private String innerGetCommandLine() {
        if (commandLine == null) {
            try {
                byte[] aLine = Files.readAllBytes(procPathName());
                int l = aLine.length - 1;
                if (l > 0) {
                    commandLine = new String(aLine, "UTF-8");
                } else {
                    commandLine = "<no command line found>";
                }
            }
            catch (Exception e) {
                commandLine = "<no command line found>";
            }
        }
        return commandLine;
    }

    public static String getCommandLine() {
        return ourInstance.innerGetCommandLine();
    }

    private ProcessCommandLine() {
    }
}
