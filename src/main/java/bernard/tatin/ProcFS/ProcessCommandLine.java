package bernard.tatin;

public class ProcessCommandLine {
    final private static ProcessCommandLine ourInstance = new ProcessCommandLine();
    private String commandLine = null;

    private String innerGetCommandLine() {
        if (commandLine == null) {
            try {
                commandLine = Tools.loadTextFile(LinuxProc.procPathName("cmdline") );
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

    public static String getCommandLine() {
        return ourInstance.innerGetCommandLine();
    }

    private ProcessCommandLine() {
    }
}
