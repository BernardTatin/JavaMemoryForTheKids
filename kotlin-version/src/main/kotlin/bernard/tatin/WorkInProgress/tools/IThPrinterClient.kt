package bernard.tatin.WorkInProgress

interface IThPrinterClient {
    fun printString(str: String)
    fun printStrings(strings: Array<String>)
    fun printError(str: String)
}
