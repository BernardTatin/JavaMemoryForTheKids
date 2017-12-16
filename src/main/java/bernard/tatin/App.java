package bernard.tatin;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) {
        int pid = ProcessID.getPID();

        System.out.println( "Hello World!" );
        System.out.println( "My PID is " + String.valueOf(pid) );
    }
}
