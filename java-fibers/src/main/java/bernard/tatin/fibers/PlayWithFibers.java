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
/*
export M2=~/.m2/repository
export QUASAR=${M2}/co/paralleluniverse

export CLASSPATH_FIBER=java-1.8-fibers-1.4.0.jar:${QUASAR}/quasar-core/0.7.9/quasar-core-0.7.9-jdk8.jar:${M2}/com/esotericsoftware/kryo/4.0.0/kryo-4.0.0.jar:${M2}/com/googlecode/concurrentlinkedhashmap/concurrentlinkedhashmap-lru/1.4/concurrentlinkedhashmap-lru-1.4.jar:${M2}/com/google/collections/google-collections/1.0/google-collections-1.0.jar:/${M2}/com/google/guava/guava/20.0/guava-20.0.jar


(cd target; java -Xmx412m -classpath ${CLASSPATH_FIBER} -javaagent:${QUASAR}/quasar-core/0.7.9/quasar-core-0.7.9-jdk8.jar=m -Dco.paralleluniverse.fibers.verifyInstrumentation=true bernard.tatin.fibers.PlayWithFibers)
 */

class PlayWithFibers {

    public static void main(String[] args) {

        Printer.thePrinter.run();
        TaskManager.taskManager.addRunnable(
                new SuspendableRunnable() {
                    Tools tools = Tools.tools;

                    private String formatSize(long msize) {
                        return String.format("%9.1f",
                                new Long(msize).doubleValue() / tools.MEGABYTE);
                    }
                    private String formatSize(Integer msize) {
                        return String.format("%9.1f",
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
                });
        TaskManager.taskManager.addRunnable(
                new SuspendableRunnable() {
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
                                        Stream.concat(Arrays.stream(memory), Arrays.stream(memory_unit)).
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
                });
        TaskManager.taskManager.startAll();
        TaskManager.taskManager.joinLast();
    }
}
