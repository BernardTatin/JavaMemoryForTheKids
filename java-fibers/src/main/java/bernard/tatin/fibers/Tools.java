package bernard.tatin.fibers;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.concurrent.ReentrantLock;
import co.paralleluniverse.strands.dataflow.Var;

public class Tools {
    public static final Tools tools = new Tools();
    public static final int KILOBYTE = 1024;
    public static final int MEGABYTE = KILOBYTE * KILOBYTE;
    public static final int MEMORY_INCREMENT = 788 * KILOBYTE;
    public final ReentrantLock lockPrinter = new ReentrantLock(true);
    private final Var<Integer> vMemorySize = new Var<Integer>();

    private Tools() {

    }

    public Integer getMemorySize() throws InterruptedException, SuspendExecution {
        return vMemorySize.getNext();
    }

    public void setMemorySize(Integer msize) {
        vMemorySize.set(msize);
    }
    public void setMemorySize(int msize) {
        vMemorySize.set(new Integer(msize));
    }

}
