package bernard.tatin.Tools.counting.tests

import bernard.tatin.Tools.counting.Counter

class CounterTest(private val max: Int, private val loops: Int) {
    fun testing() : Boolean {
        val counter = Counter(max);
        var currentLoop = -1;

        while (currentLoop < loops) {
            val i = counter.value;
            if (i != (currentLoop + 1) % max) {
                return false
            }
            currentLoop++
        }
        return true;
    }
    companion object {
        @JvmStatic public fun main(args: Array<String>) {
            val counterTest = CounterTest(15, 3)
            if (!counterTest.testing()) {
                java.lang.System.err.println("Test failed..")
            } else {
                java.lang.System.out.println("Test OK !!!")
            }
        }

    }
}

