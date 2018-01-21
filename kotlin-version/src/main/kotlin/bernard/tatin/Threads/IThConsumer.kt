package bernard.tatin.Threads

interface IThConsumer {
    fun consume()
    fun initialize()
    fun innerLoop()
}
