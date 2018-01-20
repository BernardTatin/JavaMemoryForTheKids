package bernard.tatin.Tools

import bernard.tatin.Constants.ApplicationConstants

import java.util.stream.Stream

object ForStrings {
    private var whites: String? = null

    fun rightFormat(str: String, expectedLength: Int): String {
        var str = str
        val strLength = str.length()
        if (strLength < expectedLength) {
            str += getWhites(
                    Math.min(expectedLength - strLength, ApplicationConstants.MAX_STRING_LENGTH))
        } else if (strLength > expectedLength) {
            str = str.substring(0, expectedLength)
        }
        return str
    }

    fun leftFormat(str: String, expectedLength: Int): String {
        val strLength = str.length()
        return if (strLength < expectedLength) {
            getWhites(
                    Math.min(expectedLength - strLength,
                            ApplicationConstants.MAX_STRING_LENGTH)) + str
        } else if (strLength > expectedLength) {
            str.substring(0, expectedLength)
        } else {
            str
        }
    }

    private fun getWhites(length: Int): String {
        if (whites == null) {
            whites = Stream.iterate("", { s -> " " }).limit(ApplicationConstants.MAX_STRING_LENGTH).reduce("", ???({ String.concat() }))
        }
        return whites!!.substring(0, length)
    }
}
