package bernard.tatin.Threads

open class ThPrinterClient : IThPrinterClient {
    fun printString(str: String) {
        ThPrinter.getMainInstance().printString(str)
    }

    fun printStrings(strings: Array<String>) {
        ThPrinter.getMainInstance().printStrings(strings)
    }

    fun printError(str: String) {
        ThPrinter.getMainInstance().printError(str)
    }

}
