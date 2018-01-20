package bernard.tatin.ProcFS

import bernard.tatin.Tools.ForFiles

object ProcessCommandLine {
    //    final private static ProcessCommandLine ourInstance = new ProcessCommandLine();
    private var commandLine: String? = null

    fun getCommandLine(): String {
        if (commandLine == null) {
            try {
                commandLine = ForFiles.loadTextFile(LinuxProc.procPathName("cmdline"))
                if (commandLine == null) {
                    commandLine = "<no command line found>"
                }
            } catch (e: Exception) {
                commandLine = "<no command line found>"
            }

        }
        return commandLine
    }
}
