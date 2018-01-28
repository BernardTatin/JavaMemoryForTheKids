package bernard.tatin.WorkInProgress

import bernard.tatin.WorkInProgress.testers.ITest
import bernard.tatin.WorkInProgress.testers.ITestContainer
import bernard.tatin.WorkInProgress.testers.KTests
import bernard.tatin.WorkInProgress.testers.ProcessIDTests
import bernard.tatin.WorkInProgress.testers.CounterTests
import bernard.tatin.WorkInProgress.testers.ForFilesTests

class BigTest(private val iloops: Int) : ITestContainer {
    override val name = "BigTest"
    override val loops = iloops
    override val tests: Array<ITest> = arrayOf(
            ForFilesTests(iloops),
            CounterTests(15, iloops),
            KTests(iloops),
            ProcessIDTests(iloops))
    override var okCount: Int = 0
    override val totalTests: Int = tests.size

    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {
            val bigTest: BigTest = BigTest(15)

            bigTest.doAllTests()
        }
    }
}
