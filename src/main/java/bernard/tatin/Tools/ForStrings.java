package bernard.tatin.Tools;

import java.util.stream.Stream;

public class ForStrings {
    private static final ForStrings ourInstance = new ForStrings();
    private String whites = null;
    private final int MAX_STRING_LENGTH = 256;

    private void initWhites() {
        whites = Stream.iterate("", s -> " ").limit(MAX_STRING_LENGTH).reduce("", String::concat);
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
