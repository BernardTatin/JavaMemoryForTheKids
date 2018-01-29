package bernard.tatin.threads;

import java.lang.Boolean;

public abstract class AThConsumer implements IThConsumer, Runnable {
    public final static ProtectedValue<Boolean> isRunning = new ProtectedValue<Boolean>(new Boolean(true));

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
