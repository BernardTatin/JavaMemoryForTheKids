package bernard.tatin.ProcFS;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Constants.LinuxConstants;
import bernard.tatin.Tools.ForFiles;
import bernard.tatin.Tools.ForStrings;

import java.util.Arrays;

public class StatM {
    private final static StatM ourInstance = new StatM();
    private String[] statsTitles = null;

    private final int FPROGRAM_SIZE = 0;
    private final int FRESIDENT = 1;
    private final int FDATA = 2;
    private final int FJTOTAL = 3;
    private final int FJMAX = 4;
    private final int FJFREE = 5;
    private final int FALLOCATED = 6;

    public static StatM getMainInstance() {
        return ourInstance;
    }

    public String[] getStatsTitle() {
        if (statsTitles == null) {
            statsTitles = new String[ApplicationConstants.FIELD_COUNT];
            statsTitles[FPROGRAM_SIZE] = "Prog. Size";
            statsTitles[FRESIDENT] = "Resident";
            statsTitles[FDATA] = "Data";
            statsTitles[FJTOTAL] = "JVM total memory";
            statsTitles[FJMAX] = "JVM max memory";
            statsTitles[FJFREE] = "JVM free mem";
            statsTitles[FALLOCATED] = "Allocated memory";
            statsTitles = Arrays.stream(statsTitles).map(s ->
                    ForStrings.rightFormat(s,
                            ApplicationConstants.FIELD_LENGTH - 3) +
                            " | ").toArray(String[]::new);
        }
        return statsTitles;
    }

    public String getStats(long allocatedMemory) {
        Long[] lstats = new Long[ApplicationConstants.FIELD_COUNT];
        String[] strStats = ForFiles.loadLinesFromFiles(
                LinuxProc.procPathName("statm"),
                "[ \n]");
        if (strStats != null) {
            lstats[FALLOCATED] = allocatedMemory;
            lstats[FJFREE] = Runtime.getRuntime().freeMemory();
            lstats[FJMAX] = Runtime.getRuntime().maxMemory();
            lstats[FJTOTAL] = Runtime.getRuntime().totalMemory();
            lstats[FPROGRAM_SIZE] = (Long.parseLong(strStats[0]) * LinuxConstants.PAGE_SIZE);
            lstats[FRESIDENT] = (Long.parseLong(strStats[1]) * LinuxConstants.PAGE_SIZE);
            lstats[FDATA] = (Long.parseLong(strStats[5]) * LinuxConstants.PAGE_SIZE);
            return Arrays.stream(lstats).map(v ->
                    ForStrings.leftFormat(longToMB(v),
                            ApplicationConstants.FIELD_LENGTH - 3) + "M |").
                    reduce("", String::concat);
        } else {
            return null;
        }
    }

    private String longToMB(Long l) {
        Double h = l.doubleValue() / LinuxConstants.MEGABYTE;
        Long lh = h.longValue();
        Double b = (h - lh.doubleValue()) * 1000.0;
        Long lb = b.longValue();
        String r = String.valueOf(lh) + ".";

        if (lb < 10) {
            r += "00";
        } else if (lb < 100) {
            r += "0";
        }
        r += String.valueOf(lb);
        return r;
    }
    private StatM() {
    }
}
