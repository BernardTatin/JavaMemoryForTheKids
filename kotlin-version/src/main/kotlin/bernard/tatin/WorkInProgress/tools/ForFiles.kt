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
            val byteCount = fileBytes.size

            if (byteCount > 0) {
                // '\0' is a word separator
                return CharArray(
                        byteCount,
                        fun(index : Int) : Char =
                                if (fileBytes[index].toInt() == 0)
                                    32.toChar()
                                else
                                    fileBytes[index].toChar())
                        .fold(
                                "",
                                fun(acc : String, cc: Char) : String =
                                        "${acc}${cc}"
                        )
            } else {
                println("ERROR: readBytes -> null")
                return null
            }
        } catch (e: Exception) {
            println("ERROR: readBytes -> ${e::toString}")
            return null
        }

    }

    fun loadLinesFromFiles(path: String, separators: String): List<String>? {
        return ForFiles.loadTextFile(path)?.split(separators)
    }
}
