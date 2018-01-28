package bernard.tatin.WorkInProgress.testers

import bernard.tatin.WorkInProgress.IThPrinterClient

interface ITest:IThPrinterClient {
    val name: String
    val loops: Int

    fun header() {
        printString("testing $name on $loops loops...")
    }

    fun innerTest(currentLoop: Int): Boolean

    fun testing(): Boolean {
        var currentLoop: Int = -1;

        header()
        return Array<Int>(loops + 1,
                fun(index: Int): Int = index - 1)
                .fold(
                        true,
                        fun(b: Boolean, v: Int): Boolean =
                                b && innerTest(currentLoop++)
                )
    }
    override fun printString(str: String) {
        println(str)
    }
    override fun printStrings(strings: Array<String>) {
        strings.forEach(fun(s:String) : Unit=println(s))
    }
    override fun printError(str: String) {
        printString("ERROR: $str")
    }
}
