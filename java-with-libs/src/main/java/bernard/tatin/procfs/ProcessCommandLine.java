package bernard.tatin.procfs;

import java.nio.file.Path;
import bernard.tatin.tools.ForFiles;

public class ProcessCommandLine {
    private static String commandLine = null;
    private static final Path cmdLinePath = LinuxProc.procPathName("cmdline");

    public static String getCommandLine() {
        if (commandLine == null) {
            try {
                commandLine = ForFiles.loadTextFile(cmdLinePath);
                if (commandLine == null) {
                    commandLine = "<no command line found>";
                }
            }
            catch (Exception e) {
                commandLine = "<no command line found>";
            }
        }
        return commandLine;
    }

    private ProcessCommandLine() {
    }
}
