package bernard.tatin.WorkInProgress.testers

import bernard.tatin.Tools.counting.Counter
import bernard.tatin.WorkInProgress.testers.ITest

class CounterTests(private val max: Int, private val iloops: Int) : ITest {
    override val name = "Counter"
    override val loops = iloops
    val counter = Counter(max);

    override fun innerTest(currentLoop : Int) : Boolean {
        val i = counter.value;
        if (i != (currentLoop + 1) % max) {
            java.lang.System.err.println("ERROR: $name failed on loop $currentLoop with i = $i and max = $max")
            return false
        }
        return true
    }

    companion object {
        @JvmStatic public fun main(args: Array<String>) {
            val counterTest = CounterTests(15, 3)
            if (!counterTest.testing()) {
                java.lang.System.err.println("Test failed..")
            } else {
                java.lang.System.out.println("Test OK !!!")
            }
        }

    }
}

