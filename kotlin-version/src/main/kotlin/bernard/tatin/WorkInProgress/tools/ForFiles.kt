package bernard.tatin.Tools.tools

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

object ForFiles {
    fun loadTextFile(path: Path): String? {
        try {
            val file = File(path.toString())
            val fileBytes : ByteArray = file.inputStream().readBytes()
            val l = fileBytes.size

            if (l > 0) {
                // '\0' is a word separator
                val bytes : java.util.ArrayList<Byte> =
                        java.util.ArrayList(
                                fileBytes.map{
                                    c ->
                                    return if (c.toInt() == 0)
                                        32
                                    else
                                        c}.toByte())
                return bytes.toString()
//                for (i in 0 until l) {
//                    if (fileBytes[i].toInt() == 0) {
//                        fileBytes[i] = 32
//                    }
//                }
//                return String(fileBytes, "UTF-8")
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }

    }

    fun loadLinesFromFiles(path: Path, separators: String): List<String>? {
        val fileContent = ForFiles.loadTextFile(path)
        return fileContent?.split(separators)
    }
}
