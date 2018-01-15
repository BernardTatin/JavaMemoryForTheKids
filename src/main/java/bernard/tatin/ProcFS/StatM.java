package bernard.tatin.ProcFS;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Constants.LinuxConstants;
import bernard.tatin.Tools.ForFiles;
import bernard.tatin.Tools.ForStrings;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;

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

    public static String rightFormat(String s) {
        return ForStrings.rightFormat(s,
                ApplicationConstants.FIELD_LENGTH - 2) +
                "|";
    }
    public String[] getStatsTitle() {
        if (statsTitles == null) {
            statsTitles = new String[ApplicationConstants.FIELD_COUNT];
            statsTitles[FPROG_SIZE] = "Prog. Size";
            statsTitles[FRESIDENT] = "Resident";
            statsTitles[FDATA] = "Data";
            statsTitles[FJTOTAL] = "JVM total memory";
            statsTitles[FJMAX] = "JVM max memory";
            statsTitles[FJFREE] = "JVM free mem";
            statsTitles[FALLOCATED] = "Allocated memory";
            statsTitles = Arrays.stream(statsTitles).map(StatM::rightFormat).toArray(String[]::new);
        }
        return statsTitles;
    }

    public String[] getStats(long allocatedMemory) {
        Long[] lstats = new Long[ApplicationConstants.FIELD_COUNT];
        String[] stats = new String[ApplicationConstants.FIELD_COUNT];
        String[] strStats = ForFiles.loadLinesFromfiles(
                LinuxProc.procPathName("statm"),
                "[ \n]");
        if (strStats != null) {
            lstats[FALLOCATED] = allocatedMemory / LinuxConstants.MEGABYTE;
            lstats[FJFREE] = Runtime.getRuntime().freeMemory() / LinuxConstants.MEGABYTE;
            lstats[FJMAX] = Runtime.getRuntime().maxMemory() / LinuxConstants.MEGABYTE;
            lstats[FJTOTAL] = Runtime.getRuntime().totalMemory() / LinuxConstants.MEGABYTE;
            lstats[FPROG_SIZE] = (Long.parseLong(strStats[0]) * LinuxConstants.PAGE_SIZE) / LinuxConstants.MEGABYTE;
            lstats[FRESIDENT] = (Long.parseLong(strStats[1]) * LinuxConstants.PAGE_SIZE) / LinuxConstants.MEGABYTE;
            lstats[FDATA] = (Long.parseLong(strStats[5]) * LinuxConstants.PAGE_SIZE) / LinuxConstants.MEGABYTE;
            return convertArray(lstats, v ->
                    ForStrings.leftFormat( String.valueOf(v), ApplicationConstants.FIELD_LENGTH-3) + "M |" ,
                    String[]::new);
        } else {
            return null;
        }
    }
    // cf :
    // https://stackoverflow.com/questions/23057549/lambda-expression-to-convert-array-list-of-string-to-array-list-of-integers
    public <T, U> U[] convertArray(T[] from,
                                          Function<T, U> func,
                                          IntFunction<U[]> generator) {
        return Arrays.stream(from).map(func).toArray(generator);
    }
    private StatM() {
    }
}
