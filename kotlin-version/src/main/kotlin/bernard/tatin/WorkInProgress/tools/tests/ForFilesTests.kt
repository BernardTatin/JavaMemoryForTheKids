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
    val referenceLines = arrayOf(
            "processor	: 0",
            "vendor_id	: GenuineIntel",
            "cpu family	: 6",
            "model		: 58",
            "model name	: Intel(R) Core(TM) i5-3320M CPU @ 2.60GHz",
            "stepping	: 9"
    )

    override fun innerTest(currentLoop : Int) : Boolean {
        Array<String> currentLines = ForFiles.loadLinesFromFiles(
                fileName, "\n"
                )
        var i : Int = 0

        while (i < referenceLines.size) {
            if (currentLines[i].compareTo(referenceLines[i]) != 0) {
                println("ERROR: $name failed on loop $currentLoop expected = ${referenceLines[i]} get counter = ${currentLines[i]}")
                return false
            }
        }
        return true
    }

