package bernard.tatin.tools;

import bernard.tatin.common.Chrono;
import bernard.tatin.common.ChronoException;
import bernard.tatin.common.Constants;
import bernard.tatin.common.IThPrinterClient;
import bernard.tatin.threads.AThConsumer;
import bernard.tatin.threads.ProtectedValue;
import bernard.tatin.threads.ThPrinter;

import java.util.Arrays;
import java.util.stream.Stream;

public class ThMemoryFiller extends AThConsumer implements IThPrinterClient {
    private static final ThMemoryFiller mainMemoryFiller = new ThMemoryFiller();
    private Byte[] memory = null;
    private ProtectedValue<Long> memory_size = new ProtectedValue<Long>(0L);
    private final Byte[] memory_unit = fillMemory(Constants.MEMORY_INCREMENT).
            toArray(Byte[]::new);
    private ThPrinter mainPrinter = ThPrinter.getMainInstance();
    private final Chrono chrono = new Chrono(false);
    private long deltaT;

    private ThMemoryFiller() {
    }

    public static ThMemoryFiller getMainInstance() {
        return mainMemoryFiller;
    }

    @Override
    public String getName() {
        return "ThMemoryFiller";
    }

    @Override
    public void innerLoop() {
        setMemorySize();

        try {
            if (memory != null) {
                memory = Stream.concat(Arrays.stream(memory), Arrays.stream(memory_unit)).
                        toArray(Byte[]::new);
            } else {
                chrono.start();
                memory = memory_unit;
            }
        } catch (OutOfMemoryError e) {
            try {
                deltaT = chrono.stop();
            } catch(ChronoException anotherE) {
                deltaT = 0;
            }
            printError("ERROR (ThMemoryFiller::consume): " +
                    e.toString() +
                    " " +
                    Long.toString(deltaT) +
                    "ms");
            memory = null;
        }

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            printError("ERROR InterruptedException : " + e.getMessage());
        }
    }

    public long getMemorySize() {
        return memory_size.get();
    }

    private void setMemorySize() {
        if (memory == null) {
            memory_size.set(0L);
        } else {
            memory_size.set(new Long((long)memory.length));
        }
    }

    private Stream<Byte> fillMemory(int bytes) {
        return Stream.iterate((byte) 0,
                b -> (byte) ((b + 1) % 253)).limit(bytes);
    }

    @Override
    public void printString(String str) {
        mainPrinter.printString(str);
    }

    @Override
    public void printStrings(String[] strings) {
        mainPrinter.printStrings(strings);
    }

    @Override
    public void printError(String str) {
        mainPrinter.printError(str);
    }
}
