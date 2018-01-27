package bernard.tatin.WorkInProgress.testers

interface ITest {
    val name: String
    val loops: Int

    fun header() {
        System.out.println("testing $name on $loops loops...")
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
}
