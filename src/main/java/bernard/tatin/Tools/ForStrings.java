package bernard.tatin.Tools;

import bernard.tatin.Constants.ApplicationConstants;

import java.util.stream.Stream;

public class ForStrings {
    private static String whites = null;

    public static String rightFormat(String str, int expectedLength) {
        int strLength = str.length();
        if (strLength < expectedLength) {
            str += getWhites(
                    Math.min(expectedLength - strLength, ApplicationConstants.MAX_STRING_LENGTH));
        } else if (strLength > expectedLength) {
            str = str.substring(0, expectedLength);
        }
        return str;
    }

    public static String leftFormat(String str, int expectedLength) {
        int strLength = str.length();
        if (strLength < expectedLength) {
            str = getWhites(
                    Math.min(expectedLength - strLength,
                            ApplicationConstants.MAX_STRING_LENGTH)) +
                    str;
        } else if (strLength > expectedLength) {
            str = str.substring(0, expectedLength);
        }
        return str;
    }

    private static String getWhites(final int length) {
        if (whites == null) {
            whites = Stream.iterate("", s -> " ").
                    limit(ApplicationConstants.MAX_STRING_LENGTH).
                    reduce("", String::concat);
        }
        return whites.substring(0, length);
    }

    private ForStrings() {
    }
}
