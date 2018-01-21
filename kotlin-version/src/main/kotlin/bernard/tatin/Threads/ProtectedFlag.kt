package bernard.tatin.Threads

class ProtectedFlag {
    private var flag = false
    private val mutex = Mutex()

    constructor() {

    }

    constructor(initialValue: Boolean) {
        flag = initialValue
    }

    private fun setV(newValue: Boolean) {
        try {
            mutex.lock()
        } catch (e: InterruptedException) {

        }

        flag = newValue
        mutex.unlock()
    }

    fun set() {
        setV(true)
    }

    fun reset() {
        setV(false)
    }

    fun get(): Boolean {
        try {
            mutex.lock()
        } catch (e: InterruptedException) {

        }

        val r = flag
        mutex.unlock()
        return r
    }

}
