package bernard.tatin.procfs;

import bernard.tatin.common.Constants;
import bernard.tatin.tools.ForFiles;
import bernard.tatin.tools.ForStrings;

import java.nio.file.Path;
import java.util.Arrays;

public class StatM {
    private static final StatM ourInstance = new StatM();
    private final int F_PROGRAM_SIZE = 0;
    private final int F_RESIDENT = 1;
    private final int F_DATA = 2;
    private final int F_JAVA_TOTAL = 3;
    private final int F_JAVA_MAX = 4;
    private final int F_JAVA_FREE = 5;
    private final int F_ALLOCATED = 6;
    private String lineOfTitle = null;
    private final Path statmPath = LinuxProc.procPathName("statm");

    public static StatM getMainInstance() {
        return ourInstance;
    }

    public String getStatsTitleLine() {
        if (lineOfTitle == null) {
            String[] statsTitles = new String[Constants.FIELD_COUNT];
            statsTitles[F_PROGRAM_SIZE] = "Prog. Size";
            statsTitles[F_RESIDENT] = "Resident";
            statsTitles[F_DATA] = "Data";
            statsTitles[F_JAVA_TOTAL] = "JVM total memory";
            statsTitles[F_JAVA_MAX] = "JVM max memory";
            statsTitles[F_JAVA_FREE] = "JVM free mem";
            statsTitles[F_ALLOCATED] = "Allocated memory";
            lineOfTitle = Arrays.stream(statsTitles)
                    .map(s ->
                            ForStrings.rightFormat(s,
                                    Constants.FIELD_LENGTH - 2) + " |")
                    .reduce("", String::concat);
        }
        return lineOfTitle;
    }

    public String getStatsLine(long allocatedMemory) {
        Long[] lstats = new Long[Constants.FIELD_COUNT];
        String[] strStats = ForFiles.loadLinesFromFiles(
                statmPath,
                "[ \n]");
        if (strStats != null) {
            lstats[F_ALLOCATED] = allocatedMemory;
            lstats[F_JAVA_FREE] = Runtime.getRuntime().freeMemory();
            lstats[F_JAVA_MAX] = Runtime.getRuntime().maxMemory();
            lstats[F_JAVA_TOTAL] = Runtime.getRuntime().totalMemory();
            lstats[F_PROGRAM_SIZE] = (Long.parseLong(strStats[0]) * Constants.PAGE_SIZE);
            lstats[F_RESIDENT] = (Long.parseLong(strStats[1]) * Constants.PAGE_SIZE);
            lstats[F_DATA] = (Long.parseLong(strStats[5]) * Constants.PAGE_SIZE);
            return Arrays.stream(lstats)
                    .map(v ->
                            ForStrings.leftFormat(longToMB(v),
                                    Constants.FIELD_LENGTH - 3) + "M |")
                    .reduce("", String::concat);
        } else {
            return null;
        }
    }

    private String longToMB(Long l) {
        Double h = l.doubleValue() / Constants.MEGABYTE;
        return String.format("%.3f", h);
    }

    private StatM() {
    }
}
