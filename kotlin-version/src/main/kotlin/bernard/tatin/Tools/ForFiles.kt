package bernard.tatin.Tools

import java.nio.file.Files
import java.nio.file.Path

object ForFiles {
    fun loadTextFile(path: Path): String? {
        try {
            val aLine = Files.readAllBytes(path)
            val l = aLine.size

            if (l > 0) {
                // '\0' is a word separator
                for (i in 0 until l) {
                    if (aLine[i].toInt() == 0) {
                        aLine[i] = 32
                    }
                }
                return String(aLine, "UTF-8")
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }

    }

    fun loadLinesFromFiles(path: Path, separators: String): Array<String>? {
        val fileContent = ForFiles.loadTextFile(path)
        return fileContent?.split(separators)
    }
}
