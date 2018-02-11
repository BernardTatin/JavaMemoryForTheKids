package bernard.tatin.fibers;

import bernard.tatin.tools.Printer;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;

import java.util.Arrays;
import java.util.stream.Stream;

public class MemoryFillerFiber implements SuspendableRunnable {
    Tools tools = Tools.tools;
    private Byte[] memory = null;

    private Byte[] fillMemory(int bytes) {
        return Stream.generate(() -> new Byte((byte) 85))
                .limit(bytes)
                .toArray(Byte[]::new);
    }

    private int memSize() {
        if (memory != null) {
            return memory.length;
        } else {
            return 0;
        }
    }

    @Override
    public void run() throws SuspendExecution {
        Byte[] memory_unit = fillMemory(tools.MEMORY_INCREMENT);
        boolean memory_error = false;
        OutOfMemoryError memoryException = null;
        Exception genericException = null;
        while (true) {
            try {
                memory = memory != null ?
                        Stream.
                                concat(Arrays.stream(memory),
                                        Arrays.stream(memory_unit)).
                                toArray(Byte[]::new) :
                        memory_unit;

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
                        Printer.thePrinter.printError("ERROR Memory: " +
                                memoryException.toString());
                        memoryException = null;
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
            tools.setMemorySize(memSize());
            Strand.parkNanos(300000000);
        }
    }
}


