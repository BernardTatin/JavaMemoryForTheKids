package bernard.tatin;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Constants.LinuxConstants;
import bernard.tatin.ProcFS.ProcessCommandLine;
import bernard.tatin.ProcFS.ProcessID;
import bernard.tatin.ProcFS.StatM;
import bernard.tatin.Threads.ThPrinter;
import bernard.tatin.Tools.Counter;
import bernard.tatin.Tools.ForStrings;
import bernard.tatin.Tools.MemoryFiller;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

/**
 * Hello world!
 */
class JavaMemoryForTheKids {
    private static String titleLine = null;
    private Counter count = new Counter(25);
    private BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);

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

    private void sendError(Exception e) {
        ThPrinter.mainPrinter.sendError(e);
    }

    private void showTitle() {
        if (titleLine == null) {
            String[] aTitle = StatM.getStatsTitle();
            titleLine = Arrays.stream(aTitle).reduce("", String::concat);
        }
        // System.out.println("PID          " + String.valueOf(ProcessID.getPID()));
        // System.out.println("Command line " + ProcessCommandLine.getCommandLine());
        // System.out.println(titleLine);

        sendString("PID          " + String.valueOf(ProcessID.getPID()));
        sendString("Command line " + ProcessCommandLine.getCommandLine());
        sendString(titleLine);
    }

    private void innerLoop() {
        Byte[] memory = null;
        long memory_size = 0;
        boolean success = false;
        Thread thePrinter;
        ThPrinter printer = new ThPrinter(queue);

        thePrinter = new Thread(printer);
        thePrinter.start();

        while (true) {
            if (count.getValue() == 0) {
                showTitle();
            }

            try {
                memory = memory != null ?
                        Stream.concat(Arrays.stream(memory), MemoryFiller.fillMemory(ApplicationConstants.MEMORY_INCREMENT)).toArray(Byte[]::new) :
                        MemoryFiller.fillMemory(ApplicationConstants.MEMORY_INCREMENT).toArray(Byte[]::new);

                success = true;
            } catch (OutOfMemoryError e) {
                sendError("ERROR : yes, we can catch java.lang.OutOfMemoryError !!!!");
                success = false;
            } catch (Exception e) {
                sendError("ERROR (memory): " + e.getMessage());
                success = false;
            }
            if (!success) {
                memory = null;
                memory_size = 0;
                if (count.getValue() == 0) {
                    showTitle();
                }
            } else {
                memory_size = memory.length;
            }
            String[] aString = StatM.getStats();
            if (aString != null) {
                String line = Arrays.stream(aString).reduce("", String::concat);
                sendString(line + ForStrings.leftFormat(String.valueOf(memory_size / LinuxConstants.MEGABYTE), ApplicationConstants.FIELD_LENGTH));
            } else {
                sendError("ERROR reading statm file");
            }
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                sendError("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }
}
