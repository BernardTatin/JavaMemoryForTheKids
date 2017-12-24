package bernard.tatin.Tools;

import java.util.stream.Stream;

public class MemoryFiller {
    public static Stream<Byte> fillMemory(int bytes) {

        try {
            return Stream.iterate((byte) 0,
                    b -> (byte) ((b + (byte) 1) % (byte) 253)).limit(bytes);
        } catch (OutOfMemoryError e) {
            System.err.println("ERROR (MemoryFiller): " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("ERROR (MemoryFiller): " + e.getMessage());
            return null;
        }
    }

    private MemoryFiller() {
    }
}
