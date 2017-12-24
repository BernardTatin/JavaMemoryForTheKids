package bernard.tatin;

import bernard.tatin.ProcFS.LinuxConstants;
import bernard.tatin.ProcFS.ProcessCommandLine;
import bernard.tatin.ProcFS.ProcessID;
import bernard.tatin.ProcFS.StatM;
import bernard.tatin.Tools.Counter;
import bernard.tatin.Tools.ForStrings;
import bernard.tatin.Tools.MemoryFiller;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Hello world!
 */
class JavaMemoryForTheKids {
    private final static int MEMORY_INCREMENT = 512 * LinuxConstants.KILOBYTE;
    private static String titleLine = null;

    private static void showTitle() {
        if (titleLine == null) {
            String[] aTitle = StatM.getStatsTitle();
            titleLine = Arrays.stream(aTitle).reduce("", String::concat);
        }
        System.out.println(titleLine);
    }

    public static void main(String[] args) {
        Counter count = new Counter(25);
        Byte[] memory = null;
        long memory_size = 0;
        boolean success = false;

        System.out.println("PID          " + String.valueOf(ProcessID.getPID()));
        System.out.println("Command line " + ProcessCommandLine.getCommandLine());

        while (true) {
            if (count.getValue() == 0) {
                JavaMemoryForTheKids.showTitle();
            }

            try {
                memory = memory != null ?
                        Stream.concat(Arrays.stream(memory), MemoryFiller.fillMemory(MEMORY_INCREMENT)).toArray(Byte[]::new) :
                        MemoryFiller.fillMemory(MEMORY_INCREMENT).toArray(Byte[]::new);

                memory_size += memory.length;
                success = true;
            } catch (OutOfMemoryError e) {
                System.err.println("ERROR : yes, we can catch java.lang.OutOfMemoryError !!!!");
                success = false;
            } catch (Exception e) {
                System.err.println("ERROR (memory): " + e.getMessage());
                success = false;
            }
            if (!success) {
                memory = null;
                memory_size = 0;
                if (count.getValue() == 0) {
                    JavaMemoryForTheKids.showTitle();
                }
            }
            String[] aString = StatM.getStats();
            if (aString != null) {
                String line = Arrays.stream(aString).reduce("", String::concat);
                System.out.println(line + ForStrings.leftFormat(String.valueOf(memory_size / LinuxConstants.MEGABYTE), StatM.FIELD_LENGTH));
            } else {
                System.out.println("ERROR reading statm file");
            }
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                System.err.println("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }
}
