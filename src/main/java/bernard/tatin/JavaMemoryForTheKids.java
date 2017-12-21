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
public class JavaMemoryForTheKids
{
    private final static int MEMORY_INCREMENT = 3200 * 1024;
    private static int memory_size = 0;

    private static void showTitle() {
        String[] aTitle = StatM.getStatsTitle();
        for (int i=0; i<aTitle.length; i++) {
            System.out.print(aTitle[i] + " ");
        }
        System.out.println("");
    }

    public static void main( String[] args ) {
        Counter count = new Counter(25);
        Byte[] memory = null;
        Byte[] new_memory = null;

        System.out.println( "PID          " + String.valueOf(ProcessID.getPID()) );
        System.out.println( "Command line " + ProcessCommandLine.getCommandLine());

        while (true) {
            if (count.getValue() == 0) {
                JavaMemoryForTheKids.showTitle();
            }
            new_memory = MemoryFiller.fillMemory(MEMORY_INCREMENT);
            if (memory != null) {
                memory = Stream.concat(Arrays.stream(memory),
                        Arrays.stream(new_memory))
                        .toArray(Byte[]::new);
            } else {
                memory = new_memory;
            }

            memory_size += MEMORY_INCREMENT / LinuxConstants.KILOBYTE;

            String[] aString = StatM.getStats();
            for (int i=0; i<aString.length; i++) {
                System.out.print(aString[i] + " ");
            }
            System.out.println(ForStrings.leftFormat(String.valueOf(memory_size), StatM.FIELD_LENGTH));
            try {
                Thread.sleep(800L);
            }
            catch (Exception e) {

            }
        }
    }
}
