package bernard.tatin.ProcFS

import bernard.tatin.Constants.ApplicationConstants
import bernard.tatin.Tools.ForFiles
import bernard.tatin.Tools.ForStrings

import java.util.Arrays

class StatM private constructor() {
    private val F_PROGRAM_SIZE = 0
    private val F_RESIDENT = 1
    private val F_DATA = 2
    private val F_JAVA_TOTAL = 3
    private val F_JAVA_MAX = 4
    private val F_JAVA_FREE = 5
    private val F_ALLOCATED = 6
    private var lineOfTitle: String? = null

    val statsTitleLine: String
        get() {
            if (lineOfTitle == null) {
                val statsTitles = arrayOfNulls<String>(ApplicationConstants.FIELD_COUNT)
                statsTitles[F_PROGRAM_SIZE] = "Prog. Size"
                statsTitles[F_RESIDENT] = "Resident"
                statsTitles[F_DATA] = "Data"
                statsTitles[F_JAVA_TOTAL] = "JVM total memory"
                statsTitles[F_JAVA_MAX] = "JVM max memory"
                statsTitles[F_JAVA_FREE] = "JVM free mem"
                statsTitles[F_ALLOCATED] = "Allocated memory"
                lineOfTitle = Arrays.stream(statsTitles)
                        .map({ s ->
                            ForStrings.rightFormat(s,
                                    ApplicationConstants.FIELD_LENGTH - 2) + " |"
                        })
                        .reduce("", ???({ String.concat() }))
            }
            return lineOfTitle
        }

    fun getStatsLine(allocatedMemory: Long): String? {
        val lstats = arrayOfNulls<Long>(ApplicationConstants.FIELD_COUNT)
        val strStats = ForFiles.loadLinesFromFiles(
                LinuxProc.procPathName("statm"),
                "[ \n]")
        if (strStats != null) {
            lstats[F_ALLOCATED] = allocatedMemory
            lstats[F_JAVA_FREE] = Runtime.getRuntime().freeMemory()
            lstats[F_JAVA_MAX] = Runtime.getRuntime().maxMemory()
            lstats[F_JAVA_TOTAL] = Runtime.getRuntime().totalMemory()
            lstats[F_PROGRAM_SIZE] = Long.parseLong(strStats!![0]) * ApplicationConstants.PAGE_SIZE
            lstats[F_RESIDENT] = Long.parseLong(strStats!![1]) * ApplicationConstants.PAGE_SIZE
            lstats[F_DATA] = Long.parseLong(strStats!![5]) * ApplicationConstants.PAGE_SIZE
            return Arrays.stream(lstats)
                    .map({ v ->
                        ForStrings.leftFormat(longToMB(v),
                                ApplicationConstants.FIELD_LENGTH - 3) + "M |"
                    })
                    .reduce("", ???({ String.concat() }))
        } else {
            return null
        }
    }

    private fun longToMB(l: Long): String {
        val h = l.doubleValue() / ApplicationConstants.MEGABYTE
        return String.format("%.3f", h)
    }

    companion object {
        val mainInstance = StatM()
    }
}
