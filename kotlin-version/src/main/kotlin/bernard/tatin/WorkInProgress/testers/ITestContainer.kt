package bernard.tatin.WorkInProgress.testers

import bernard.tatin.WorkInProgress.testers.ITest

interface ITestContainer : ITest {
    val tests: Array<ITest>
    var okCount: Int
    val totalTests: Int

    override fun innerTest(currentLoop: Int): Boolean {
        okCount = tests.fold(0,
                fun(count: Int, test: ITest): Int =
                        if (test.testing()) {
                            count + 1
                        } else {
                            printError("   ${test.name} failed")
                            count
                        }
        )
        return okCount > 0
    }

    fun doAllTests() {
        val isItOk = testing()

        printString("Good tests $name : $okCount / $totalTests")
    }
}
