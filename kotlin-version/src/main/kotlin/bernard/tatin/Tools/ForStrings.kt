package bernard.tatin.Tools

import bernard.tatin.Constants.ApplicationConstants

import java.util.stream.Stream

object ForStrings {
    private var whites: String = initWhites()

    fun rightFormat(inStr: String, expectedLength: Int): String {
        var str = inStr
        val strLength = str.length
        if (strLength < expectedLength) {
            str += getWhites(
                    Math.min(expectedLength - strLength, ApplicationConstants.MAX_STRING_LENGTH))
            return str
        } else if (strLength > expectedLength) {
            return str.substring(0, expectedLength)
        }
        return inStr
    }

    fun leftFormat(str: String, expectedLength: Int): String {
        val strLength = str.length
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

    private fun initWhites() : String {
        whites = " "
        while (whites.length < ApplicationConstants.MAX_STRING_LENGTH)
            whites += " "
        return whites
    }
    private fun getWhites(length: Int): String {
        return whites!!.substring(0, length)
    }
}
