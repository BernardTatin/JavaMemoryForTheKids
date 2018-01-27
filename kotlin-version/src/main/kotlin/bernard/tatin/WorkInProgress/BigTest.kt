package bernard.tatin.WorkInProgress

import bernard.tatin.WorkInProgress.testers.ITest
import bernard.tatin.WorkInProgress.testers.KTests
import bernard.tatin.WorkInProgress.testers.ProcessIDTests
import bernard.tatin.WorkInProgress.testers.CounterTests
import bernard.tatin.WorkInProgress.testers.ForFilesTests

class BigTest(private val iloops: Int) : ITest {
    override val name = "BigTest"
    override val loops = iloops
    val tests: Array<ITest> = arrayOf(
            ForFilesTests(iloops),
            CounterTests(15, iloops),
            KTests(iloops),
            ProcessIDTests(iloops))
    var okCount: Int = 0
    val totalTests: Int = tests.size

    override fun innerTest(currentLoop: Int): Boolean {
        okCount = tests.fold(0,
                fun(count: Int, test: ITest): Int =
                        if (test.testing()) {
                            count + 1
                        } else {
                            println("   ...ERROR")
                            count
                        }
        )
        return okCount > 0
    }

    fun doAllTests() {
        val isItOk = testing()

        println("Good tests $name : $okCount / $totalTests")
    }

    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {
            val bigTest: BigTest = BigTest(15)

            bigTest.doAllTests()
        }
    }
}
