package bernard.tatin.ProcFS;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Constants.LinuxConstants;
import bernard.tatin.Tools.ForFiles;
import bernard.tatin.Tools.ForStrings;

public class StatM {
    private final static StatM ourInstance = new StatM();
    private String[] statsTitles = null;

    private final int FPROG_SIZE = 0;
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
            int field_len = ApplicationConstants.FIELD_LENGTH - 2;
            statsTitles = new String[ApplicationConstants.FIELD_COUNT];
            statsTitles[FPROG_SIZE] =
                    ForStrings.rightFormat("Prog. Size", field_len) + " | ";
            statsTitles[FRESIDENT] =
                    ForStrings.rightFormat("Resident", field_len) + "| ";
            statsTitles[FDATA] =
                    ForStrings.rightFormat("Data", field_len) + "| ";
            statsTitles[FJTOTAL] =
                    ForStrings.rightFormat("JVM total memory", field_len) + "| ";
            statsTitles[FJMAX] =
                    ForStrings.rightFormat("JVM max memory", field_len) + "| ";
            statsTitles[FJFREE] =
                    ForStrings.rightFormat("JVM free mem", field_len) + "| ";
            statsTitles[FALLOCATED] =
                    ForStrings.rightFormat("Allocated memory", field_len) + " |";
        }
        return statsTitles;
    }

    public String[] getStats(long allocatedMemory) {
        long[] lstats = new long[ApplicationConstants.FIELD_COUNT];
        String[] stats = new String[ApplicationConstants.FIELD_COUNT];
        String[] strStats = ForFiles.loadLinesFromfiles(
                LinuxProc.procPathName("statm"),
                "[ \n]");
        if (strStats != null) {
            lstats[FALLOCATED] = allocatedMemory;
            lstats[FJFREE] = Runtime.getRuntime().freeMemory();
            lstats[FJMAX] = Runtime.getRuntime().maxMemory();
            lstats[FJTOTAL] = Runtime.getRuntime().totalMemory();
            lstats[FPROG_SIZE] = Long.parseLong(strStats[0]) * LinuxConstants.PAGE_SIZE;
            lstats[FRESIDENT] = Long.parseLong(strStats[1]) * LinuxConstants.PAGE_SIZE;
            lstats[FDATA] = Long.parseLong(strStats[5]) * LinuxConstants.PAGE_SIZE;
            for (int i = 0; i < ApplicationConstants.FIELD_COUNT; i++) {
                stats[i] = ForStrings.leftFormat(
                        String.valueOf(lstats[i] / LinuxConstants.MEGABYTE),
                        ApplicationConstants.FIELD_LENGTH - 3) + "M |";
            }
            return stats;
        } else {
            return null;
        }
    }

    private StatM() {
    }
}
