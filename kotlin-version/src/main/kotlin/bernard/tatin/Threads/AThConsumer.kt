package bernard.tatin.Threads

abstract class AThConsumer : IThConsumer, Runnable {

    @Override
    fun initialize() {
        val theThread = Thread(this, "AThConsumer")

        theThread.setDaemon(true)
        theThread.start()
    }

    @Override
    @Synchronized
    fun run() {
        consume()
    }

    @Override
    fun consume() {
        while (AThConsumer.isRunning.get()) {
            innerLoop()
        }
    }

    companion object {
        val isRunning = ProtectedFlag(true)
    }
}
