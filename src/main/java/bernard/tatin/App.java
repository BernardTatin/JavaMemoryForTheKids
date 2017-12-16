package bernard.tatin;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) {
        long pid = ProcessID.getPID();

        System.out.println( "My PID is " + String.valueOf(pid) );
    }
}
