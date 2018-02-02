package bernard.tatin.threads;

public class ProtectedValue<T> extends ThPrinterClient {
    private T pValue;
    private final Mutex mutex = new Mutex();


    public ProtectedValue(T initialValue) {
        pValue = initialValue;
    }

    public void set(T newValue) {
        try {
            mutex.lock();
        } catch (InterruptedException e) {
            printError("Protected value, mutex lock failed in set. " + e.getMessage());
        }
        pValue = newValue;
        mutex.unlock();
    }

    
    public T get() {
        try {
            mutex.lock();
        } catch (InterruptedException e) {
            printError("Protected value, mutex lock failed in get. " + e.getMessage());
        }
        T r = pValue;
        mutex.unlock();
        return r;
    }

}
