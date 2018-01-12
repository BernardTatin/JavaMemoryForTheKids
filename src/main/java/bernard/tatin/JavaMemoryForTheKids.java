package bernard.tatin;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Constants.LinuxConstants;
import bernard.tatin.ProcFS.ProcessCommandLine;
import bernard.tatin.ProcFS.ProcessID;
import bernard.tatin.ProcFS.StatM;
import bernard.tatin.Threads.ThPrinter;
import bernard.tatin.Tools.Counter;
import bernard.tatin.Tools.ForStrings;
import bernard.tatin.Tools.ThMemoryFiller;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Hello world!
 */
class JavaMemoryForTheKids {
    private static String titleLine = null;
    private final Counter count = new Counter(25);
    private final BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);

    public static void main(String[] args) {
        JavaMemoryForTheKids jm = new JavaMemoryForTheKids();
        jm.innerLoop();
    }

    private void sendString(String str) {
        ThPrinter.mainPrinter.sendString(str);
    }

    private void sendError(String str) {
        ThPrinter.mainPrinter.sendError(str);
    }

    private void showTitle() {
        if (titleLine == null) {
            String[] aTitle = StatM.getStatsTitle();
            titleLine = Arrays.stream(aTitle).reduce("", String::concat);
        }

        sendString("PID          " + String.valueOf(ProcessID.getPID()));
        sendString("Command line " + ProcessCommandLine.getCommandLine());
        sendString(titleLine);
    }

    private void innerLoop() {
        long memory_size;

        ThPrinter.mainPrinter.initialize(queue);
        ThMemoryFiller.mainMemoryFiller.initialize();

        while (true) {
            if (count.getValue() == 0) {
                showTitle();
            }

            memory_size = ThMemoryFiller.mainMemoryFiller.getMemorySize();
            String[] aString = StatM.getStats();
            if (aString != null) {
                String line = Arrays.stream(aString).reduce("", String::concat);
                sendString(line + ForStrings.leftFormat(String.valueOf(memory_size / LinuxConstants.MEGABYTE), ApplicationConstants.FIELD_LENGTH));
            } else {
                sendError("ERROR reading statm file");
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                sendError("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }
}
