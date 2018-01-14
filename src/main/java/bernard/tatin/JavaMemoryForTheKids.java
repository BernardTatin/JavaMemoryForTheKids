package bernard.tatin;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Constants.LinuxConstants;
import bernard.tatin.ProcFS.ProcessCommandLine;
import bernard.tatin.ProcFS.ProcessID;
import bernard.tatin.ProcFS.StatM;
import bernard.tatin.Threads.ThPrinter;
import bernard.tatin.Threads.ThPrinterClient;
import bernard.tatin.Tools.Counter;
import bernard.tatin.Tools.ForStrings;
import bernard.tatin.Tools.ThMemoryFiller;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Hello world!
 */
class JavaMemoryForTheKids extends ThPrinterClient {
    private static String titleLine = null;
    private final Counter count = new Counter(25);

    public static void main(String[] args) {
        JavaMemoryForTheKids jm = new JavaMemoryForTheKids();

        ThPrinter.mainPrinter.initialize();
        ThMemoryFiller.mainMemoryFiller.initialize();

        jm.innerLoop();
    }

    private void showTitle() {
        if (titleLine == null) {
            String[] aTitle = StatM.getStatsTitle();
            titleLine = Arrays.stream(aTitle).reduce("", String::concat);
        }

        sendStrings(
                new String[] {"PID          " + String.valueOf(ProcessID.getPID()),
                        "Command line " + ProcessCommandLine.getCommandLine(),
                        titleLine});
    }

    private void innerLoop() {
        while (true) {
            long memory_size = ThMemoryFiller.mainMemoryFiller.getMemorySize();
            String[] aString = StatM.getStats();

            if (count.getValue() == 0) {
                showTitle();
            }

            if (aString != null) {
                aString[ApplicationConstants.FIELD_COUNT-1] =
                        ForStrings.leftFormat(String.valueOf(memory_size /
                                LinuxConstants.MEGABYTE),
                                ApplicationConstants.FIELD_LENGTH);

                sendString(Arrays.stream(aString).reduce("", String::concat));
            } else {
                sendError("ERROR reading statm file");
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                sendError("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }
}
