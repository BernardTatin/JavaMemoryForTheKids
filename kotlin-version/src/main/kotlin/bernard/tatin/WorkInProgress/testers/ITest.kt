package bernard.tatin.WorkInProgress.testers

interface ITest {
    val name : String
    val loops : Int

    fun header() {
        System.out.println("testing $name on $loops loops...")
    }
    fun innerTest(currentLoop : Int) : Boolean
    fun testing() : Boolean {
        var currentLoop : Int = -1;
        header()
        while (currentLoop < loops) {
            if (! innerTest(currentLoop)) {
                return false
            }
            currentLoop++
        }
        return true
    }
}
