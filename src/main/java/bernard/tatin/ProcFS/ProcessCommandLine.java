package bernard.tatin.ProcFS;

import bernard.tatin.Tools.ForFiles;

public class ProcessCommandLine {
    final private static ProcessCommandLine ourInstance = new ProcessCommandLine();
    private static String commandLine = null;

    public static String getCommandLine() {
        if (commandLine == null) {
            try {
                commandLine = ForFiles.loadTextFile(LinuxProc.procPathName("cmdline") );
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
