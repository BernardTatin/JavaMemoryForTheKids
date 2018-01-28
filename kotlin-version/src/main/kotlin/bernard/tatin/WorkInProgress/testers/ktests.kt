package bernard.tatin.WorkInProgress.testers

import bernard.tatin.WorkInProgress.testers.ITest
import bernard.tatin.WorkInProgress.testers.ITestContainer
import bernard.tatin.WorkInProgress.testers.KTests
import bernard.tatin.WorkInProgress.testers.ProcessIDTests
import bernard.tatin.WorkInProgress.testers.CounterTests
import bernard.tatin.WorkInProgress.testers.ForFilesTests

class KTests(private val iloops : Int) : ITestContainer {
    override val name = "KTests"
    override val loops = iloops
    override val tests: Array<ITest> = arrayOf(
    )
    override var okCount: Int = 0
    override val totalTests: Int = tests.size
}
