package bernard.tatin;

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
        Integer[] memory = new Integer[1];

        memory[0] = new Integer(0);
        System.out.println( "PID          " + String.valueOf(ProcessID.getPID()) );
        System.out.println( "Command line " + ProcessCommandLine.getCommandLine());

        while (true) {
            if (count.getValue() == 0) {
                JavaMemoryForTheKids.showTitle();
            }

            String[] aString = StatM.getStats();
            for (int i=0; i<aString.length; i++) {
                System.out.print(aString[i] + " ");
            }
            try {
                Thread.sleep(800L);
            }
            catch (Exception e) {

            }
            memory = Stream.concat(Arrays.stream(memory),
                    Arrays.stream(MemoryFiller.fillMemory(MEMORY_INCREMENT)))
                    .toArray(Integer[]::new);

            memory_size += MEMORY_INCREMENT / 4096;
            System.out.println(ForStrings.leftFormat(String.valueOf(memory_size), 10));
        }
    }
}
