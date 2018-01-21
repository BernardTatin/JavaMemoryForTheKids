package bernard.tatin.Threads

interface IThPrinterClient {
    fun printString(str: String)
    fun printStrings(strings: Array<String>)
    fun printError(str: String)
}
