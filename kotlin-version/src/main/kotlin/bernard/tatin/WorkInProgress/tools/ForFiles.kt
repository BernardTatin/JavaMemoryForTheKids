package bernard.tatin.Tools.tools

import java.nio.file.Files
import java.nio.file.Path

object ForFiles {
    fun loadTextFile(path: Path): String? {
        try {
            val file = File(path)
            val fileBytes : ByteArray = File.readBytes()
            val l = fileBytes.size

            if (l > 0) {
                // '\0' is a word separator
                val chars : java.util.ArrayList<Byte> =
                        java.util.ArrayList(
                                fileBytes.map{
                                    c -> return if (c.toInt == 0)  32 else c})
                return chars.toString()
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
