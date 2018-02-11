package bernard.tatin.tools;

import bernard.tatin.common.Constants;
import bernard.tatin.common.IThPrinterClient;
import bernard.tatin.threads.AThConsumer;
import bernard.tatin.threads.ProtectedValue;
import bernard.tatin.threads.ThPrinter;
import io.vavr.control.Try;

import java.util.Arrays;
import java.util.stream.Stream;

public class ThMemoryFiller extends AThConsumer implements IThPrinterClient {
    private static final ThMemoryFiller mainMemoryFiller = new ThMemoryFiller();
    private Byte[] memory = null;
    private ProtectedValue<Long> memory_size = new ProtectedValue<Long>(0L);
    private final Byte[] memory_unit = fillMemory(Constants.MEMORY_INCREMENT);
    private ThPrinter mainPrinter = ThPrinter.getMainInstance();
    private final Byte[] nullByteArray = null;


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
        try {
            // unecessary used of Try because OutOfMemoryError
            // is a System Exception...
            // Nice try, Bernard! (ouaaaahhhh)
            Try<Byte[]> computedMemory =
                    Try.of(() -> memory != null ?
                            Stream.concat(Arrays.stream(memory), Arrays.stream(memory_unit)).
                                    toArray(Byte[]::new) :
                            memory_unit);
            if (computedMemory.isFailure()) {
                printError("ERROR (ThMemoryFiller::consume): "
                        + computedMemory.getCause().toString());
            }
            // nullByteArray helps differntiation between 2 methods
            // with different types of parameter, which null can't do
            memory = computedMemory.getOrElse(nullByteArray);
        } catch (OutOfMemoryError e) {
            printError("ERROR (ThMemoryFiller::consume): " + e.toString());
            memory = null;
        }

        setMemorySize();

        doSleep(100L);
    }


    public long getMemorySize() {
        return memory_size.get();
    }

    private void setMemorySize() {
        if (memory == null) {
            memory_size.set(0L);
        } else {
            memory_size.set(new Long((long) memory.length));
        }
    }

    private Byte[] fillMemory(int bytes) {
        return Stream.generate(() -> new Byte((byte) 85))
                .limit(bytes)
                .toArray(Byte[]::new);
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
