package bernard.tatin.WorkInProgress.testers

import bernard.tatin.Tools.counting.Counter
import bernard.tatin.WorkInProgress.testers.ITest

class CounterTests(private val max: Int, private val iloops: Int) : ITest {
    override val name = "Counter"
    override val loops = iloops

    override fun innerTest(currentLoop : Int) : Boolean {
        val counter = Counter(max);
        var i = 0;
        var v = 0
        while (i < max) {
            v = counter.value
            if (i != v) {
                java.lang.System.err.println("ERROR: $name failed on loop $currentLoop expected = $i get counter = $v with max = $max")
                return false
            }
            i++
        }
        v = counter.value
        if (v != 0) {
            java.lang.System.err.println("ERROR: $name failed on loop $currentLoop expected = 0 get counter = $v with max = $max")
            return false
        }
        return true
    }
}

