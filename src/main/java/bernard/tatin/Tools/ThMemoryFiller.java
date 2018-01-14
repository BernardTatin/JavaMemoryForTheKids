package bernard.tatin.Tools;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Threads.Mutex;
import bernard.tatin.Threads.ThConsumer;
import bernard.tatin.Threads.ThPrinterClient;

import java.util.Arrays;
import java.util.stream.Stream;

public class ThMemoryFiller extends ThPrinterClient implements ThConsumer, Runnable {
    public final static ThMemoryFiller mainMemoryFiller = new ThMemoryFiller();
    private final Mutex mutex = new Mutex();
    private Byte[] memory = null;
    private long memory_size = 0;

    private ThMemoryFiller() {
    }

    public void consume() {
        while (true) {
            try {
                memory = memory != null ?
                        Stream.concat(Arrays.stream(memory),
                                fillMemory(ApplicationConstants.MEMORY_INCREMENT)).
                                toArray(Byte[]::new) :
                        fillMemory(ApplicationConstants.MEMORY_INCREMENT).
                                toArray(Byte[]::new);

            } catch (OutOfMemoryError e) {
                printError("ERROR (ThMemoryFiller::consume): " + e.toString());
                memory = null;
            }

            setMemorySize();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                printError("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }

    public long getMemorySize() {
        long rmem = 0;
        try {
            mutex.lock();
        } catch (Exception e) {
            printError("ERROR : cannot acquire mutex " + e.toString());
        }
        rmem = memory_size;
        mutex.unlock();
        return rmem;
    }

    private void setMemorySize() {
        try {
            mutex.lock();
        } catch (InterruptedException e) {
            printError("ERROR : cannot acquire mutex " + e.toString());
        }
        memory_size = 0;
        if (memory != null) {
            memory_size = memory.length;
        }
        mutex.unlock();
    }

    public void initialize() {
        Thread theMemoryFiller = new Thread(this, "ThMemoryFiller");

        theMemoryFiller.setDaemon(true);
        theMemoryFiller.start();
    }

    public synchronized void run() {
        consume();
    }

    private Stream<Byte> fillMemory(int bytes) {
        return Stream.iterate((byte) 0,
                b -> (byte) ((b + (byte) 1) % (byte) 253)).limit(bytes);
    }
}
