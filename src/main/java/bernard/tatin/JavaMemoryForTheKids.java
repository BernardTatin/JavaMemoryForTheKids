package bernard.tatin;

/**
 * Hello world!
 *
 */
public class JavaMemoryForTheKids
{
    public static void main( String[] args ) {
        System.out.println( "PID          " + String.valueOf(ProcessID.getPID()) );
        System.out.println( "Command line " + ProcessCommandLine.getCommandLine());
    }
}
