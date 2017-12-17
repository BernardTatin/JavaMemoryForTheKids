package bernard.tatin.Tools;

public class MemoryFiller {
    private static MemoryFiller ourInstance = new MemoryFiller();

    public static Integer[] fillMemory(int bytes) {
        Integer[] table = new Integer[bytes/4];

        for (int i=0; i<bytes/4; i++) {
            table[i] = new Integer(i);
        }
        return table;
    }

    private MemoryFiller() {
    }
}
