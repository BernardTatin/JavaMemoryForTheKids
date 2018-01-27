package bernard.tatin.WorkInProgress.testers

/*
 * here is how to test :
 * java -jar target/recallMeKotlin-1.0.0-jar-with-dependencies.jar &; ps
 *
 */
import bernard.tatin.ProcFS.processid.ProcessID
import bernard.tatin.WorkInProgress.testers.ITest

class ProcessIDTests(private val iloops : Int) : ITest {
    override val name = "ProcessID"
    override val loops = iloops
    var lastPID : Long = getPID()

    fun getPID() : Long {
        return ProcessID.getPID()
    }

    override fun innerTest(currentLoop : Int) : Boolean {
        val newPID : Long  = getPID()
        if (newPID != lastPID) {
            return false
        }
        lastPID = newPID
        return true
    }
}
