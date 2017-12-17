package bernard.tatin;

import bernard.tatin.ProcFS.ProcessCommandLine;
import bernard.tatin.ProcFS.ProcessID;
import bernard.tatin.ProcFS.StatM;

/**
 * Hello world!
 *
 */
public class JavaMemoryForTheKids
{
    public static void main( String[] args ) {
        System.out.println( "PID          " + String.valueOf(ProcessID.getPID()) );
        System.out.println( "Command line " + ProcessCommandLine.getCommandLine());

        String[] aTitle = StatM.getStatsTitle();
        for (int i=0; i<aTitle.length; i++) {
            System.out.print(aTitle[i]);
        }
        while (true) {
            String[] aString = StatM.getStats();
            for (int i=0; i<aString.length; i++) {
                System.out.print(aString[i]);
            }
            try {
                Thread.sleep(800L);
            }
            catch (Exception e) {

            }
            // LinuxProc.busySleep(500000L);
            // System.out.println("");
        }
    }
}
