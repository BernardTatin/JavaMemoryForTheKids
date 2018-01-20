package bernard.tatin.Tools

class Counter(private val max: Int) {
    private var current = -1

    val value: Int
        get() {
            current++
            if (current >= max) {
                current = 0
            }
            return current
        }
}
