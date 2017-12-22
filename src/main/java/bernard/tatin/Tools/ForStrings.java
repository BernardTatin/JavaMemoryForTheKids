package bernard.tatin.Tools;

public class ForStrings {
    private static ForStrings ourInstance = new ForStrings();
    private String whites = null;
    private final int MAX_STRING_LENGTH = 256;

    private void initWhites() {
        whites = "";
        for (int i=0; i<MAX_STRING_LENGTH; i++) {
            whites += " ";
        }
    }

    public static String rightFormat(String str, int expectedLength) {
        int strLength = str.length();
        if (strLength < expectedLength) {
            if (ourInstance.whites == null) {
                ourInstance.initWhites();
            }
            str += ourInstance.whites.substring(0,
                    Math.min(expectedLength-strLength, ourInstance.MAX_STRING_LENGTH));
        } else if (strLength > expectedLength) {
            str = str.substring(0, expectedLength);
        }
        return str;
    }
    public static String leftFormat(String str, int expectedLength) {
        int strLength = str.length();
        if (strLength < expectedLength) {
            if (ourInstance.whites == null) {
                ourInstance.initWhites();
            }
            str = ourInstance.whites.substring(0,
                    Math.min(expectedLength-strLength, ourInstance.MAX_STRING_LENGTH)) + str;
        } else if (strLength > expectedLength) {
            str = str.substring(0, expectedLength);
        }
        return str;
    }
    private ForStrings() {
    }
}
