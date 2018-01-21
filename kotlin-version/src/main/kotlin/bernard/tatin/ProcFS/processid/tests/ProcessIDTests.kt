package bernard.tatin.ProcFS.processid.tests

import bernard.tatin.ProcFS.processid.ProcessID

class ProcessIDTests(private val loops : Int) {
    fun getPID() : Long {
        return ProcessID.getPID()
    }

    fun testing() : Boolean {
        var currentLoop : Int = 0;
        var lastPID : Long  = getPID()

        while (currentLoop < loops) {
            val newPID : Long  = getPID()
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
