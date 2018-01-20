package bernard.tatin.Tools

import bernard.tatin.Constants.ApplicationConstants
import bernard.tatin.Threads.*

import java.util.Arrays
import java.util.stream.Stream

class ThMemoryFiller private constructor() : AThConsumer(), IThPrinterClient {
    private val mutex = Mutex()
    private var memory: Array<Byte>? = null
    private var memory_size: Long = 0
    private val memory_unit = fillMemory(ApplicationConstants.MEMORY_INCREMENT).toArray(Byte[]::new  /* Currently unsupported in Kotlin */)

    val memorySize: Long
        get() {
            val rmem: Long
            try {
                mutex.lock()
            } catch (e: Exception) {
                printError("ERROR : cannot acquire mutex " + e.toString())
            }

            rmem = memory_size
            mutex.unlock()
            return rmem
        }

    @Override
    fun innerLoop() {
        try {
            memory = if (memory != null)
                Stream.concat(Arrays.stream(memory), Arrays.stream(memory_unit)).toArray(Byte[]::new  /* Currently unsupported in Kotlin */)
            else
                memory_unit

        } catch (e: OutOfMemoryError) {
            printError("ERROR (ThMemoryFiller::consume): " + e.toString())
            memory = null
        }

        setMemorySize()
        try {
            Thread.sleep(100L)
        } catch (e: InterruptedException) {
            printError("ERROR InterruptedException : " + e.getMessage())
        }

    }

    private fun setMemorySize() {
        try {
            mutex.lock()
        } catch (e: InterruptedException) {
            printError("ERROR : cannot acquire mutex " + e.toString())
        }

        memory_size = 0
        if (memory != null) {
            memory_size = memory!!.size.toLong()
        }
        mutex.unlock()
    }

    private fun fillMemory(bytes: Int): Stream<Byte> {
        return Stream.iterate(0.toByte(),
                { b -> ((b + 1.toByte()) % 253.toByte()).toByte() }).limit(bytes)
    }

    fun printString(str: String) {
        ThPrinter.getMainInstance().printString(str)
    }

    fun printStrings(strings: Array<String>) {
        ThPrinter.getMainInstance().printStrings(strings)
    }

    fun printError(str: String) {
        ThPrinter.getMainInstance().printError(str)
    }

    companion object {
        val mainInstance = ThMemoryFiller()
    }
}
