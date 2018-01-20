package bernard.tatin;

import bernard.tatin.ProcFS.ProcessCommandLine;
import bernard.tatin.ProcFS.ProcessID;
import bernard.tatin.ProcFS.StatM;
import bernard.tatin.Threads.AThConsumer;
import bernard.tatin.Threads.ThPrinter;
import bernard.tatin.Threads.ThPrinterClient;
import bernard.tatin.Tools.Counter;
import bernard.tatin.Tools.ThMemoryFiller;

/**
 * Hello world!
 */
class JavaMemoryForTheKids extends ThPrinterClient {
    private static String titleLine = null;
    private final Counter count = new Counter(25);

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            AThConsumer.isRunning.reset();
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
            System.out.println("W: interrupt received, exit...");
        }));
        JavaMemoryForTheKids jm = new JavaMemoryForTheKids();

        ThPrinter.getMainInstance().initialize();
        ThMemoryFiller.getMainInstance().initialize();

        jm.innerLoop();
    }

    private void showTitle() {
        if (titleLine == null) {
            String aTitle = StatM.getMainInstance().getStatsTitle();
            if (aTitle != null) {
                titleLine = aTitle;
            } else {
                titleLine ="titleLine is NULLLLLL";
            }
        }

        printStrings(
                new String[] {"PID          " + String.valueOf(ProcessID.getPID()),
                        "Command line " + ProcessCommandLine.getCommandLine(),
                        titleLine});
    }

    private void innerLoop() {
        while (true) {
            long memory_size = ThMemoryFiller.getMainInstance().getMemorySize();
            String aString = StatM.getMainInstance().getStats(memory_size);

            if (count.getValue() == 0) {
                showTitle();
            }

            if (aString != null) {
                printString(aString);
            } else {
                printError("ERROR reading statm file");
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                printError("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }
}
