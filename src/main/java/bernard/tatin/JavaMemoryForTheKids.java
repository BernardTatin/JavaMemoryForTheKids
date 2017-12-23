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
 *
 */
class JavaMemoryForTheKids
{
    private final static int MEMORY_INCREMENT = 5 * LinuxConstants.MEGABYTE;
    private static String titleLine = null;

    private static void showTitle() {
        if (titleLine == null) {
            String[] aTitle = StatM.getStatsTitle();
            for (String s : aTitle) {
                titleLine += s + " ";
            }
        }
        System.out.println(titleLine);
    }

    public static void main( String[] args ) {
        Counter count = new Counter(25);
        Byte[] memory = null;
        Byte[] new_memory = null;
        int memory_size = 0;

        System.out.println( "PID          " + String.valueOf(ProcessID.getPID()) );
        System.out.println( "Command line " + ProcessCommandLine.getCommandLine());

        while (true) {
            if (count.getValue() == 0) {
                JavaMemoryForTheKids.showTitle();
            }
            new_memory = MemoryFiller.fillMemory(MEMORY_INCREMENT);
            if (new_memory == null) {
                System.err.println("ERROR : out of memory, reset all");
                memory = null;
                new_memory = null;
                memory_size = 0;
            }
            if (memory != null) {
                try {
                    memory = Stream.concat(Arrays.stream(memory),
                            Arrays.stream(new_memory))
                            .toArray(Byte[]::new);
                } catch (OutOfMemoryError e) {
                    System.err.println("ERROR : yes, we can catch java.lang.OutOfMemoryError !!!!");
                    memory = null;
                    memory_size = 0;
                }
            } else {
                memory = new_memory;
            }

            memory_size += MEMORY_INCREMENT / LinuxConstants.MEGABYTE;

            String[] aString = StatM.getStats();
            if (aString != null) {
                String line = "";
                for (String s : aString) {
                    line += s + " ";
                }
                System.out.println(line + ForStrings.leftFormat(String.valueOf(memory_size), StatM.FIELD_LENGTH));
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
