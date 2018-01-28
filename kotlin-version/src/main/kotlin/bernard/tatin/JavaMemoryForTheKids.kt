package bernard.tatin

import bernard.tatin.ProcFS.ProcessCommandLine
import bernard.tatin.ProcFS.ProcessID
import bernard.tatin.ProcFS.StatM
import bernard.tatin.Threads.AThConsumer
import bernard.tatin.Threads.ThPrinter
import bernard.tatin.Threads.ThPrinterClient
import bernard.tatin.Tools.counting.Counter
import bernard.tatin.Tools.ThMemoryFiller

/**
 * Hello world!
 */
internal class JavaMemoryForTheKids : ThPrinterClient() {
    private val count = Counter(25)

    private fun showTitle() {
        if (titleLine == null) {
            val aTitle = StatM.mainInstance.getStatsTitleLine()
            if (aTitle != null) {
                titleLine = aTitle
            } else {
                titleLine = "titleLine is NULLLLLL"
            }
        }

        printStrings(
                arrayOf<String>("PID          " + String.valueOf(ProcessID.getPID()), "Command line " + ProcessCommandLine.getCommandLine(), titleLine))
    }

    private fun innerLoop() {
        while (true) {
            val memorySize = ThMemoryFiller.mainInstance.getMemorySize()
            val aString = StatM.mainInstance.getStatsLine(memorySize)

            if (count.getValue() === 0) {
                showTitle()
            }

            if (aString != null) {
                printString(aString)
            } else {
                printError("ERROR reading statm file")
            }
            try {
                Thread.sleep(100L)
            } catch (e: InterruptedException) {
                printError("ERROR InterruptedException : " + e.getMessage())
            }

        }
    }

    companion object {
        private var titleLine: String? = null

        fun main(args: Array<String>) {
            Runtime.getRuntime().addShutdownHook(Thread {
                // System signals handled
                // it works, but I don't like this

                // stop other threads
                AThConsumer.isRunning.reset()
                // wait a little
                try {
                    Thread.sleep(100)
                } catch (e: Exception) {
                    // don't use printError, ThPrinter is stopped
                    System.err.println("Signal catching interrupted...")
                }

                // don't use printString, ThPrinter is stopped
                System.out.println("Signal caught, interrupt received, exit...")
            })

            val jm = JavaMemoryForTheKids()
            // initialize and run threads
            ThPrinter.mainInstance.initialize()
            ThMemoryFiller.mainInstance.initialize()

            jm.innerLoop()
        }
    }
}
