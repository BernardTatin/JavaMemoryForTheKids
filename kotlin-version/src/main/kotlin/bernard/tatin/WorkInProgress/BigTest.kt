package bernard.tatin.WorkInProgress

import bernard.tatin.WorkInProgress.testers.ITest
import bernard.tatin.WorkInProgress.testers.ProcessIDTests
import bernard.tatin.WorkInProgress.testers.CounterTests
import bernard.tatin.WorkInProgress.testers.ForFilesTests

class BigTest(private val iloops : Int) : ITest  {
    override val name = "BigTest"
    override val loops = iloops
    val tests : Array<ITest> = arrayOf(
            ForFilesTests(iloops),
            CounterTests(15, iloops),
            ProcessIDTests(iloops))

    override fun innerTest(currentLoop : Int) : Boolean {
        for (item in tests) {
            if (item is ITest && !item.testing()) {
                return false
            }
        }
        return true
    }

    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {
            val bigTest : BigTest = BigTest(15)
//            bigTest.initialize()
            val name = bigTest.name
            if (!bigTest.testing()) {
                java.lang.System.err.println("$name failed..")
            } else {
                java.lang.System.out.println("$name OK !!!")
            }
        }
    }
}
