package bernard.tatin.WorkInProgress.testers

import bernard.tatin.Tools.tools.ForFiles
import bernard.tatin.WorkInProgress.testers.ITest

/*
processor	: 0
vendor_id	: GenuineIntel
cpu family	: 6
model		: 58
model name	: Intel(R) Core(TM) i5-3320M CPU @ 2.60GHz
stepping	: 9

 */
class ForFilesTests(private val iloops : Int) : ITest {
    override val name = "ForFiles"
    override val loops = iloops
    val fileName = "/proc/cpuinfo"
    val referenceLines: Array<String> = arrayOf(
            "processor	: 0",
            "vendor_id	: GenuineIntel",
            "cpu family	: 6",
            "model		: 58",
            "model name	: Intel(R) Core(TM) i5-3320M CPU @ 2.60GHz",
            "stepping	: 9"
    )

    override fun innerTest(currentLoop: Int): Boolean {
        val listOfCurrentLines: List<String>? = ForFiles.loadLinesFromFiles(fileName, "\n")
        if (listOfCurrentLines != null) {
            val currentLines: Array<String>? = listOfCurrentLines.toTypedArray()
            var i: Int = 0

            if (currentLines != null) {
                while (i < referenceLines.size) {
                    val line = currentLines[i] // .toString()
                    val rline = referenceLines[i] // .toString()
                    if (line.compareTo(rline) != 0) {
                        println("ERROR: $name failed on loop $currentLoop expected = <${referenceLines[i]}> get line = <${line}>")
                        return false
                    }
                    i++
                }
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
}

