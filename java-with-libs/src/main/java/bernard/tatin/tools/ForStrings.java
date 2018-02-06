package bernard.tatin.tools;

import bernard.tatin.common.Constants;

import java.util.stream.Stream;

public class ForStrings {
    private static String whites = null;

    public static String rightFormat(final String str, final int expectedLength) {
        final int strLength = str.length();
        if (strLength < expectedLength) {
            return str + getWhites(
                    Math.min(expectedLength - strLength, Constants.MAX_STRING_LENGTH));
        } else if (strLength > expectedLength) {
            return str.substring(0, expectedLength);
        }
        return str;
    }

    public static String leftFormat(final String str, final int expectedLength) {
        final int strLength = str.length();
        if (strLength < expectedLength) {
            return getWhites(
                    Math.min(expectedLength - strLength,
                            Constants.MAX_STRING_LENGTH)) +
                    str;
        } else if (strLength > expectedLength) {
            return str.substring(0, expectedLength);
        } else {
            return str;
        }
    }

    private static String getWhites(final int length) {
        if (whites == null) {
            whites = Stream.iterate("", s -> " ").
                    limit(Constants.MAX_STRING_LENGTH).
                    reduce("", String::concat);
        }
        return whites.substring(0, length);
    }

    private ForStrings() {
    }
}
