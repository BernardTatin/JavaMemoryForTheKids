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
                // CharArray(size: Int, init: (Int) -> Char)
                var index0 : Int = 0
                val chars : CharArray = CharArray(
                        l,
                        fun(index : Int) : Char =
                                if (fileBytes[index].toInt() == 0)
                                    32.toChar()
                                else
                                    fileBytes[index].toChar())

                val c0 = chars[0]
                println("chars: ${chars::class.qualifiedName} - ${chars::class.simpleName}")
                println("c0: ${c0::class.qualifiedName} - ${c0::class.simpleName}")
                println("----------------------------------")
                chars.forEach( fun(cc: Char) = print("$cc") )
                println("----------------------------------")
                println("----------------------------------")
                chars.fold(
                        "",
                        fun(acc : String, cc: Char) : String = "/${acc}${cc}/"
                )
                println("----------------------------------")
                return chars.toString()
//                return chars.fold(
//                        "",
//                        fun(acc : String, cc: Char) : String = "${acc}${cc}"
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
        val fileContent = ForFiles.loadTextFile(path)
        if (fileContent != null) {
            println("fileContent: ${fileContent::class.qualifiedName} - ${fileContent::class.simpleName}")
            println("----------------------------------")
            println(fileContent)
            println("----------------------------------")
        }
        return fileContent?.split(separators)
    }
}
