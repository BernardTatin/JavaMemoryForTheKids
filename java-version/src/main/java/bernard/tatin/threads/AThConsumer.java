package bernard.tatin.threads;


public abstract class AThConsumer implements IThConsumer, Runnable {
    public static final ProtectedValue<Boolean> isRunning = new ProtectedValue<Boolean>(true);

    @Override
    public void initialize() {
        Thread theThread = new Thread(this, "AThConsumer");

        theThread.setDaemon(true);
        theThread.start();
    }

    @Override
    public synchronized void run() {
        consume();
    }

    @Override
    public void consume() {
        while (AThConsumer.isRunning.get()) {
            innerLoop();
        }
    }
}
