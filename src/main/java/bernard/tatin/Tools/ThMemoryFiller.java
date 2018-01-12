package bernard.tatin.Tools;

import bernard.tatin.Constants.ApplicationConstants;
import bernard.tatin.Threads.ThConsumer;
import bernard.tatin.Threads.ThPrinter;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class ThMemoryFiller implements ThConsumer, Runnable {
    public final static ThMemoryFiller mainMemoryFiller = new ThMemoryFiller();
    private final Semaphore mutex = new Semaphore(1, true);
    private Byte[] memory = null;
    private long memory_size = 0;

    private ThMemoryFiller() {
    }

    public boolean consume() {
        boolean success = false;
        while (true) {
            try {
                memory = memory != null ?
                        Stream.concat(Arrays.stream(memory),
                                fillMemory(ApplicationConstants.MEMORY_INCREMENT)).
                                toArray(Byte[]::new) :
                        fillMemory(ApplicationConstants.MEMORY_INCREMENT).
                                toArray(Byte[]::new);

                success = true;
            } catch (OutOfMemoryError e) {
                ThPrinter.mainPrinter.sendError("ERROR (ThMemoryFiller::consume): " + e.toString());
            } catch (Exception e) {
                ThPrinter.mainPrinter.sendError("ERROR (ThMemoryFiller::consume): " + e.toString());
            }

            if (success) {
                success = false;
            } else {
                memory = null;
            }
            setMemorySize();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                ThPrinter.mainPrinter.sendError("ERROR InterruptedException : " + e.getMessage());
            }
        }
    }

    public long getMemorySize() {
        long rmem = 0;
        try {
            mutex.acquire();
            rmem = memory_size;
            mutex.release();
        } catch (Exception e) {
            ThPrinter.mainPrinter.sendError("ERROR : cannot acquire mutex " + e.toString());
        } finally {
            return rmem;
        }
    }

    private void setMemorySize() {
        try {
            memory_size = 0;
            mutex.acquire();
            if (memory != null) {
                memory_size = memory.length;
            }
            mutex.release();
        } catch (InterruptedException e) {
            ThPrinter.mainPrinter.sendError("ERROR : cannot acquire mutex " + e.toString());
        } catch (Exception e) {
            ThPrinter.mainPrinter.sendError("ERROR : cannot acquire mutex " + e.toString());
        }
    }

    public ThConsumer initialize() {
        Thread theMemoryFiller = new Thread(this, "ThMemoryFiller");

        theMemoryFiller.setDaemon(true);
        theMemoryFiller.start();
        return this;
    }

    public synchronized void run() {
        consume();
    }

    private Stream<Byte> fillMemory(int bytes) {

        try {
            return Stream.iterate((byte) 0,
                    b -> (byte) ((b + (byte) 1) % (byte) 253)).limit(bytes);
        } catch (OutOfMemoryError e) {
            ThPrinter.mainPrinter.sendError("ERROR (ThMemoryFiller): " + e.getMessage());
            return null;
        } catch (Exception e) {
            ThPrinter.mainPrinter.sendError("ERROR (ThMemoryFiller): " + e.getMessage());
            return null;
        }
    }
}
