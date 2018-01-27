package bernard.tatin.Tools

import bernard.tatin.Constants.ApplicationConstants
import bernard.tatin.Threads.*

import java.util.Arrays
import java.util.stream.Stream

class ThMemoryFiller private constructor() : AThConsumer(), IThPrinterClient {
    private val mutex = Mutex()
    private var memory: ByteArray? = null
    private var theMemorySize: Long = 0
    val memory_unit = ByteArray(ApplicationConstants.MEMORY_INCREMENT,
            { b -> ((b + 1.toByte()) % 253.toByte()).toByte() })

    fun memorySize(): Long {
            val rmem: Long
            try {
                mutex.lock()
            } catch (e: Exception) {
                printError("ERROR : cannot acquire mutex " + e.toString())
            }

            rmem = theMemorySize
            mutex.unlock()
            return rmem
        }
    private fun setMemorySize() {
        try {
            mutex.lock()
        } catch (e: InterruptedException) {
            printError("ERROR : cannot acquire mutex " + e.toString())
        }

        theMemorySize = 0
        if (memory != null) {
            theMemorySize = memory!!.size.toLong()
        }
        mutex.unlock()
    }

    fun concatArrays() : ByteArray {
        val ab = ByteArray(theMemorySize.toInt() + ApplicationConstants.MEMORY_INCREMENT)
        System.arraycopy(memory, 0, ab, 0, theMemorySize.toInt())
        System.arraycopy(memory_unit, 0, ab, theMemorySize.toInt(), ApplicationConstants.MEMORY_INCREMENT)
        return ab
    }
    override fun innerLoop() {
        try {
            memory = if (memory != null) {
                concatArrays()
            } else {
                memory_unit
            }
        } catch (e: OutOfMemoryError) {
            printError("ERROR (ThMemoryFiller::consume): " + e.toString())
            memory = null
        }

        setMemorySize()
        try {
            Thread.sleep(100L)
        } catch (e: InterruptedException) {
            printError("ERROR InterruptedException : " + e.toString())
        }

    }


    override fun printString(str: String) {
        ThPrinter.mainInstance.printString(str)
    }

    override fun printStrings(strings: Array<String>) {
        ThPrinter.mainInstance.printStrings(strings)
    }

    override fun printError(str: String) {
        ThPrinter.mainInstance.printError(str)
    }

    companion object {
        val mainInstance = ThMemoryFiller()
    }
}
