package bernard.tatin.Threads

import java.util.concurrent.Semaphore

class Mutex {
    private val mutex = Semaphore(1, true)

    @Throws(InterruptedException::class)
    fun lock() {
        mutex.acquire()
    }

    fun unlock() {
        mutex.release()
    }
}
