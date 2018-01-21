package bernard.tatin.ProcFS.processid.tests

/*
 * here is how to test :
 * java -jar target/recallMeKotlin-1.0.0-jar-with-dependencies.jar &; ps
 *
 */
import bernard.tatin.ProcFS.processid.ProcessID

class ProcessIDTests(private val loops : Int) {
    private val name = "ProcessID"

    fun header() {
        System.out.println("testing $name on $loops loops...")
    }
    fun getPID() : Long {
        return ProcessID.getPID()
    }

    fun testing() : Boolean {
        var currentLoop : Int = 0;
        var lastPID : Long  = getPID()

        header()
        while (currentLoop < loops) {
            val newPID : Long  = getPID()
            System.out.println("PID -> $newPID")
            if (newPID != lastPID) {
                return false
            }
            lastPID = newPID
            currentLoop++
        }
        return true;
    }

    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {
            val counterTest = ProcessIDTests(15)
            if (!counterTest.testing()) {
                java.lang.System.err.println("Test failed..")
            } else {
                java.lang.System.out.println("Test OK !!!")
            }
        }
    }


}
