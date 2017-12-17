package bernard.tatin.ProcFS;

import bernard.tatin.Tools.ForFiles;
import bernard.tatin.Tools.ForStrings;

public class StatM {
    private final static StatM ourInstance = new StatM();
    private final int FIELD_COUNT = 7;
    private final int FIELD_LENGTH = 9;
    private String[] stats = new String[FIELD_COUNT];
    private String[] statsTitles = null;

    private String[] innerGetStats() {
        String lineStats = ForFiles.loadTextFile(LinuxProc.procPathName("statm") );
        String[] strStats = lineStats.split(" ");
        for (int i=0; i<strStats.length && i<FIELD_COUNT; i++) {
            stats[i] = ForStrings.leftFormat(strStats[i], FIELD_LENGTH);
        }
        return stats;
    }
    public static String[] getStats() {
        return ourInstance.innerGetStats();
    }

    public static String[] getStatsTitle() {
        if (ourInstance.statsTitles == null) {
            int i = 0;
            ourInstance.statsTitles = new String[ourInstance.FIELD_COUNT];
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Prog. Size", ourInstance.FIELD_LENGTH);
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Resident", ourInstance.FIELD_LENGTH);
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Share", ourInstance.FIELD_LENGTH);
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Text", ourInstance.FIELD_LENGTH);
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Lib", ourInstance.FIELD_LENGTH);
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Data", ourInstance.FIELD_LENGTH);
            ourInstance.statsTitles[i++] = ForStrings.rightFormat("Dirty Pages", ourInstance.FIELD_LENGTH);
        }
        return ourInstance.statsTitles;
    }

    private StatM() {
    }
}
