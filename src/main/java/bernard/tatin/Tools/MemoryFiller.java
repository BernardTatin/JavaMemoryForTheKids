package bernard.tatin.Tools;

public class MemoryFiller {
    private static MemoryFiller ourInstance = new MemoryFiller();

    public static Byte[] fillMemory(int bytes) {
        Byte[] table = null;
        try {
            table = new Byte[bytes];
            byte b = (byte) 0;

            for (int i = 0; i < bytes; i++) {
                table[i] = b;
                if (b == (byte) 255) {
                    b = (byte) 0;
                } else {
                    b++;
                }
            }
        } catch (OutOfMemoryError e) {
            table = null;
        }
        return table;
    }

    private MemoryFiller() {
    }
}
