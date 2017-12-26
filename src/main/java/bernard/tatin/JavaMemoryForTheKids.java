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
    Counter count = new Counter(25);
    private BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);
    private ThPrinter printer;
    private Thread thePrinter;

    public static void main(String[] args) {
        JavaMemoryForTheKids jm = new JavaMemoryForTheKids();
        jm.innerLoop();

    }

    private synchronized void sendString(String str) {
        printer.sendString(str);
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

    public void innerLoop() {
        Byte[] memory = null;
        long memory_size = 0;
        boolean success = false;

        printer = new ThPrinter(queue);
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
                // System.err.println("ERROR : yes, we can catch java.lang.OutOfMemoryError !!!!");
                sendString("ERROR : yes, we can catch java.lang.OutOfMemoryError !!!!");
                success = false;
            } catch (Exception e) {
                // System.err.println("ERROR (memory): " + e.getMessage());
                sendString("ERROR (memory): " + e.getMessage());
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
                // System.out.println(line + ForStrings.leftFormat(String.valueOf(memory_size / LinuxConstants.MEGABYTE), ApplicationConstants.FIELD_LENGTH));
                sendString(line + ForStrings.leftFormat(String.valueOf(memory_size / LinuxConstants.MEGABYTE), ApplicationConstants.FIELD_LENGTH));
            } else {
                // System.out.println("ERROR reading statm file");
                sendString("ERROR reading statm file");
            }
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                // System.err.println("ERROR InterruptedException : " + e.getMessage());
                sendString("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }
}
