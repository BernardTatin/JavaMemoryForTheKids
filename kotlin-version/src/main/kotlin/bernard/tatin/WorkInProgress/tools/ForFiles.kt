package bernard.tatin.Tools.tools

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

// a lot of things to learn here about collections,
// types and map
object ForFiles {
    fun loadTextFile(path: String): String? {
        try {
            val file = File(path)
            val fileBytes : ByteArray = file.inputStream().readBytes()
            val l = fileBytes.size

            if (l > 0) {
                // '\0' is a word separator
                val chars : Array<Char> = fileBytes.map(
                        fun(c: Byte) : Char =
                                if (c.toInt() == 0)
                                    32.toChar()
                                else
                                    c.toChar()).toTypedArray()
                return chars.toString()
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }

    }

    fun loadLinesFromFiles(path: String, separators: String): List<String>? {
        val fileContent = ForFiles.loadTextFile(path)
        return fileContent?.split(separators)
    }
}
