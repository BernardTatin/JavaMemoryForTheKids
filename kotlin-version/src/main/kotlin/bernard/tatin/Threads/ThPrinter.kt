package bernard.tatin.Threads

import java.io.PrintStream
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

internal class PrintElement(val stream: PrintStream, val line: String)

class ThPrinter private constructor() : AThConsumer() {
    private val queue = LinkedBlockingDeque(10)

    fun innerLoop() {
        var pElement: PrintElement
        try {
            wait()
            while (!queue.isEmpty()) {
                pElement = queue.take()
                pElement.stream.println(pElement.line)
            }
        } catch (e: Exception) {
            System.err.println("ERROR (ThPrinter::consume): " + e.toString())
        }

    }

    @Synchronized
    fun printStrings(strings: Array<String>) {
        try {
            for (str in strings) {
                queue.put(PrintElement(System.out, str))
            }
            notify()
        } catch (e: Exception) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString())
        }

    }

    @Synchronized
    fun printString(str: String) {
        try {
            queue.put(PrintElement(System.out, str))
            notify()
        } catch (e: Exception) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString())
        }

    }

    @Synchronized
    fun printError(str: String) {
        try {
            queue.put(PrintElement(System.err, str))
            notify()
        } catch (e: Exception) {
            System.err.println("ERROR (ThPrinter::printError): " + e.toString())
        }

    }

    companion object {
        val mainInstance = ThPrinter()
    }

}
