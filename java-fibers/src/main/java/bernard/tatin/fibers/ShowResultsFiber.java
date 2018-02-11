package bernard.tatin.fibers;

import bernard.tatin.tools.Printer;
import bernard.tatin.tools.TaskManager;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;
import co.paralleluniverse.strands.concurrent.ReentrantLock;
import co.paralleluniverse.strands.dataflow.Var;

import java.util.Arrays;
import java.util.stream.Stream;

public class ShowResultsFiber implements SuspendableRunnable {
    Tools tools = Tools.tools;

    private String formatSize(long msize) {
        return String.format("%9.2f",
                new Long(msize).doubleValue() / tools.MEGABYTE);
    }

    private String formatSize(Integer msize) {
        return String.format("%9.2f",
                msize.doubleValue() / tools.MEGABYTE);
    }

    @Override
    public void run() throws SuspendExecution {
        Integer memorySize;
        while (true) {
            try {
                memorySize = tools.getMemorySize();
            } catch (InterruptedException e) {
                continue;
            }
            tools.lockPrinter.lock();
            try {
                Runtime rtime = Runtime.getRuntime();
                String line =
                        formatSize(rtime.totalMemory()) +
                                " | " +
                                formatSize(rtime.maxMemory()) +
                                " | " +
                                formatSize(rtime.freeMemory()) +
                                " | " +
                                formatSize(memorySize) +
                                " | ";
                Printer.thePrinter.printString(line);
            } finally {
                tools.lockPrinter.unlock();
            }
        }
    }
}
