package bernard.tatin.ProcFS;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Constants.LinuxConstants;
import bernard.tatin.Tools.ForFiles;
import bernard.tatin.Tools.ForStrings;

public class StatM {
    private final static StatM ourInstance = new StatM();
    private String[] statsTitles = null;

    public static String[] getStatsTitle() {
        if (ourInstance.statsTitles == null) {
            int i = 0;
            int field_len = ApplicationConstants.FIELD_LENGTH - 2;
            ourInstance.statsTitles = new String[ApplicationConstants.FIELD_COUNT];
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Prog. Size", field_len) + " | ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Resident", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Share", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("JVM total memory", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("JVM max memory", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Data", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("JVM free mem", field_len) + "| ";
            ourInstance.statsTitles[i] = ForStrings.rightFormat("Allocated memory", field_len) + " |";
        }
        return ourInstance.statsTitles;
    }

    public static String[] getStats() {
        return ourInstance.innerGetStats();
    }

    private String[] innerGetStats() {
        String[] stats = new String[ApplicationConstants.FIELD_COUNT];
        String[] strStats = ForFiles.loadLinesFromfiles(LinuxProc.procPathName("statm"), "[ \n]");
        if (strStats != null) {
            // ugly, ugly, ugly !!!
            strStats[6] = String.valueOf(Runtime.getRuntime().freeMemory() / LinuxConstants.PAGE_SIZE);
            strStats[4] = String.valueOf(Runtime.getRuntime().maxMemory() / LinuxConstants.PAGE_SIZE);
            strStats[3] = String.valueOf(Runtime.getRuntime().totalMemory() / LinuxConstants.PAGE_SIZE);
            for (int i = 0; i < strStats.length && i < ApplicationConstants.FIELD_COUNT; i++) {
                long v = (Long.parseLong(strStats[i]) * LinuxConstants.PAGE_SIZE) / LinuxConstants.MEGABYTE;
                stats[i] = ForStrings.leftFormat(String.valueOf(v), ApplicationConstants.FIELD_LENGTH - 3) + "M |";
            }
            return stats;
        } else {
            return null;
        }
    }

    private StatM() {
    }
}
