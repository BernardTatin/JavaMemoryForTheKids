package bernard.tatin.common;

import java.util.concurrent.Semaphore;

public class Mutex {
    private final Semaphore mutex = new Semaphore(1, true);


    public void lock() throws InterruptedException {
        mutex.acquire();
    }

    public void unlock() {
        mutex.release();
    }
}
