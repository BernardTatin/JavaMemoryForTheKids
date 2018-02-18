package bernard.tatin.tools;

public class LArray<T> {
    private T array[] = null;
    private LArray<T> next = null;
    private int arraySize = 0;

    public LArray(final int arraySize, final LArray<T> next, final T initValue) {
        this.next = next;
        this.arraySize = arraySize;

        array = (T[])new Object[arraySize];
        for (int i=0; i<arraySize; i++) {
            array[i] = initValue;
        }
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getLArraySize(final int initValue) {
        if (getNext() == null) {
            return initValue + getArraySize();
        } else {
            return next.getLArraySize(initValue + getArraySize());
        }
    }

    public LArray<T> getNext() {
        return next;
    }

    public T get(final int index) {
        return array[index];
    }
}
