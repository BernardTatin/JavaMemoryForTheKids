package bernard.tatin.WorkInProgress.testers


class KTests(private val iloops : Int) : ITest {
    override val name = "KTests"
    override val loops = iloops

    override fun innerTest(currentLoop: Int): Boolean {
        return true
    }
}
