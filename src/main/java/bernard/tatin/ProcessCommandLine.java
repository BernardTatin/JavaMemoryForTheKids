package bernard.tatin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessCommandLine {
    private static ProcessCommandLine ourInstance = new ProcessCommandLine();
    private long pid = ProcessID.getPID();
    private String commandLine = null;

    private String innerGetCommandLine() {
        if (commandLine == null) {
            try {
                byte[] aLine = Files.readAllBytes(LinuxProc.procPathName("cmdline"));
                int l = aLine.length;

                if (l > 0) {
                    // '\0' id a word separator
                    for (int i=0; i<l; i++) {
                        if (aLine[i] == 0) {
                            aLine[i] = 32;
                        }
                    }
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
