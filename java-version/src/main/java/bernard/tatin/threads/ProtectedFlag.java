package bernard.tatin.threads;

public class ProtectedFlag {
    private boolean flag = false;
    private final Mutex mutex = new Mutex();

    public ProtectedFlag() {

    }

    public ProtectedFlag(boolean initialValue) {
        flag = initialValue;
    }

    private void setV(boolean newValue) {
        try {
            mutex.lock();
        } catch (InterruptedException e) {

        }
        flag = newValue;
        mutex.unlock();
    }

    public void set() {
        setV(true);
    }
    public void reset() {
        setV(false);
    }
    public boolean get() {
        try {
            mutex.lock();
        } catch (InterruptedException e) {

        }
        boolean r = flag;
        mutex.unlock();
        return r;
    }

}
