package bernard.tatin.fibers;

import bernard.tatin.tools.Chrono;
import bernard.tatin.tools.ChronoException;
import bernard.tatin.tools.LArray;
import bernard.tatin.tools.Printer;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;

public class KoolMemoryFillerFiber implements SuspendableRunnable {
    private final long nanoToMs = 1000000;
    private final long normalWait = 50 * nanoToMs;
    private final long longWait = 1500 * nanoToMs;

    private final Tools tools = Tools.tools;
    private final Runtime rtime = Runtime.getRuntime();

    private final Chrono chrono = new Chrono(false);
    private LArray<Byte> memory = null;


    private int memSize() {
        if (memory != null) {
            return memory.getLArraySize(0);
        } else {
            return 0;
        }
    }

    private void doPark() throws SuspendExecution {
        long waitTime = normalWait;
        if (rtime.freeMemory() < 30 * tools.MEGABYTE) {
            waitTime = longWait;
        }
        Strand.parkNanos(waitTime);
    }

    @Override
    public void run() throws SuspendExecution {
        boolean memory_error = false;
        OutOfMemoryError memoryException = null;
        Exception genericException = null;
        long deltaT;

        chrono.start();
        while (true) {
            tools.setMemorySize(memSize());
            try {
                memory = new LArray(tools.MEMORY_INCREMENT,
                        memory,
                        85);
            } catch (OutOfMemoryError e) {
                memoryException = e;
                memory_error = true;
                memory = null;
            } catch (Exception e) {
                genericException = e;
                memory_error = true;
            }
            if (memory_error) {
                memory_error = false;
                tools.lockPrinter.lock();
                try {
                    if (memoryException != null) {
                        try {
                            deltaT = chrono.stop();
                        } catch(ChronoException e) {
                            deltaT = 0;
                        }
                        Printer.thePrinter.printError("ERROR Memory: " +
                                memoryException.toString() +
                                " " +
                                Long.toString(deltaT) +
                                "ms"
                        );
                        memoryException = null;
                        chrono.start();
                    }
                    if (genericException != null) {
                        Printer.thePrinter.printError("ERROR Memory: " +
                                genericException.toString());
                        genericException = null;
                    }
                } finally {
                    tools.lockPrinter.unlock();
                }
            }
            doPark();
        }
    }
}

