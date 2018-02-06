package bernard.tatin.threads;

import bernard.tatin.common.IThConsumer;
import io.vavr.control.Try;

public abstract class AThConsumer implements IThConsumer, Runnable {
    public static final ProtectedValue<Boolean> isRunning = new ProtectedValue<Boolean>(true);

    @Override
    public void initialize() {
        Thread theThread = new Thread(this, getName());

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

    protected void doSleep(long ms) {
        Try<Boolean> sleep100 = Try.of(() -> sleep100B(ms));
        if (sleep100.isFailure()) {
            // TODO: must use ThPrinter
            System.err.println("ERROR (ThMemoryFiller::consume): "
                    + sleep100.getCause().toString());
        }
    }
    private Boolean sleep100B(long ms) throws InterruptedException {
        Thread.sleep(ms);
        return new Boolean(true);
    }

}
