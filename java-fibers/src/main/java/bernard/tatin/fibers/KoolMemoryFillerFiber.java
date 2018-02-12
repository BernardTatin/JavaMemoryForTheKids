package bernard.tatin.fibers;

import bernard.tatin.tools.Printer;
import bernard.tatin.tools.LArray;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public class KoolMemoryFillerFiber implements SuspendableRunnable {
    Tools tools = Tools.tools;
    private LArray<Byte> memory = null;


    private int memSize() {
        if (memory != null) {
            return memory.getLArraySize(0);
        } else {
            return 0;
        }
    }

    @Override
    public void run() throws SuspendExecution {
        boolean memory_error = false;
        OutOfMemoryError memoryException = null;
        Exception genericException = null;
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
            Strand.parkNanos(300000000);
        }
    }
}

