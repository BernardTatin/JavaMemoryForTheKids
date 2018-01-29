package bernard.tatin.threads;

public class ProtectedValue<T> {
    private T flag;
    private final Mutex mutex = new Mutex();

    private ProtectedValue() {

    }

    public ProtectedValue(T initialValue) {
        flag = initialValue;
    }

    private void setV(T newValue) {
        try {
            mutex.lock();
        } catch (InterruptedException e) {

        }
        flag = newValue;
        mutex.unlock();
    }

    public void set(T newValue) {
        setV(newValue);
    }
    public T get() {
        try {
            mutex.lock();
        } catch (InterruptedException e) {

        }
        T r = flag;
        mutex.unlock();
        return r;
    }

}
