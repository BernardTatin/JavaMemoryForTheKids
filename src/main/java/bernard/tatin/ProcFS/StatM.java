package bernard.tatin.ProcFS;

import bernard.tatin.Tools.ForFiles;
import bernard.tatin.Tools.ForStrings;

public class StatM {
    private final static StatM ourInstance = new StatM();
    private final int FIELD_COUNT = 7;
    public static final int FIELD_LENGTH = 14;
    private final String[] stats = new String[FIELD_COUNT];
    private String[] statsTitles = null;

    public static String[] getStatsTitle() {
        if (ourInstance.statsTitles == null) {
            int i = 0;
            int field_len = StatM.FIELD_LENGTH - 2;
            ourInstance.statsTitles = new String[ourInstance.FIELD_COUNT + 1];
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Prog. Size", field_len) + " | ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Resident", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Share", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Text", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Lib", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Data", field_len) + "| ";
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("JVM free mem", field_len) + "| ";
            ourInstance.statsTitles[i] = ForStrings.rightFormat("Allocated arrays", field_len) + " |";
        }
        return ourInstance.statsTitles;
    }

    public static String[] getStats() {
        return ourInstance.innerGetStats();
    }

    private String[] innerGetStats() {
        String[] strStats = ForFiles.loadLinesFromfiles(LinuxProc.procPathName("statm"), "[ \n]");
        if (strStats != null) {
            // ugly, ugly, ugly !!!
            strStats[6] = String.valueOf(Runtime.getRuntime().freeMemory() / LinuxConstants.PAGE_SIZE);
            for (int i = 0; i < strStats.length && i < FIELD_COUNT; i++) {
                long v = Long.parseLong(strStats[i]) * LinuxConstants.PAGE_SIZE / LinuxConstants.MEGABYTE;
                stats[i] = ForStrings.leftFormat(String.valueOf(v), FIELD_LENGTH - 3) + "M |";
            }
            return stats;
        } else {
            return null;
        }
    }

    private StatM() {
    }
}
