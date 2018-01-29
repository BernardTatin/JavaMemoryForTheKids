package bernard.tatin.tools;

import bernard.tatin.constants.Constants;
import bernard.tatin.threads.*;

import java.util.Arrays;
import java.util.stream.Stream;

public class ThMemoryFiller extends AThConsumer implements IThPrinterClient {
    private final static ThMemoryFiller mainMemoryFiller = new ThMemoryFiller();
    private final Mutex mutex = new Mutex();
    private Byte[] memory = null;
    private ProtectedValue<Long> memory_size = new ProtectedValue<Long>(new Long(0));
    private Byte[] memory_unit = fillMemory(Constants.MEMORY_INCREMENT).
            toArray(Byte[]::new);

    private ThMemoryFiller() {
    }

    public static ThMemoryFiller getMainInstance() {
        return mainMemoryFiller;
    }

    @Override
    public void innerLoop() {
        try {
            memory = memory != null ?
                    Stream.concat(Arrays.stream(memory), Arrays.stream(memory_unit)).
                            toArray(Byte[]::new) :
                    memory_unit;

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

    public long getMemorySize() {
        return memory_size.get();
    }

    private void setMemorySize() {
        if (memory == null) {
            memory_size.set(new Long(0));
        } else {
            memory_size.set(new Long(memory.length));
        }
    }

    private Stream<Byte> fillMemory(int bytes) {
        return Stream.iterate((byte) 0,
                b -> (byte) ((b + (byte) 1) % (byte) 253)).limit(bytes);
    }
    public void printString(String str) {
        ThPrinter.getMainInstance().printString(str);
    }
    public void printStrings(String[] strings) {
        ThPrinter.getMainInstance().printStrings(strings);
    }

    public void printError(String str) {
        ThPrinter.getMainInstance().printError(str);
    }
}
