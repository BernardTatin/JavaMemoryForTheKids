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
    var okCount: Int = 0
    val totalTests: Int = tests.size

    override fun innerTest(currentLoop : Int) : Boolean {
        okCount = tests.fold(0,
                fun(count: Int, test: ITest): Int =
                        if (test.testing()) {
                            count + 1
                        } else {
                            count
                        }
        )
        return okCount > 0
//        for (item in tests) {
//            if (item is ITest && !item.testing()) {
//                return false
//            }
//        }
//        return true
    }

    fun doAllTests() {
        val isItOk = testing()

        println("Good tests : $okCount / $totalTests")
        if (!isItOk) {
            java.lang.System.err.println("$name failed..")
        } else {
            java.lang.System.out.println("$name OK !!!")
        }
    }
    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {
            val bigTest : BigTest = BigTest(15)

            bigTest.doAllTests()
        }
    }
}
