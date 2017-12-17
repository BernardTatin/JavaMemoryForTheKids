package bernard.tatin.Tools;

public class Counter {
    private int current = -1;
    private int max = 0;

    public Counter(int theMax) {
        max = theMax;
    }

    public int getValue() {
        current++;
        if (current >= max) {
            current = 0;
        }
        return current;
    }
}
