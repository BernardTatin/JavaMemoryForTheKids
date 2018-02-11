package bernard.tatin.tools;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;

public class TaskManager {
    public final static TaskManager taskManager = new TaskManager();
    public final static int MAX_RUNNABLE = 15;
    private Fiber[] fibers = new Fiber[MAX_RUNNABLE];
    private int firstEmptyRunnable = 0;

    private TaskManager() {
        for (int i=0; i<MAX_RUNNABLE; i++) {
            fibers[i] = null;
        }
    }

    public void addRunnable(SuspendableRunnable sr) {
        if (firstEmptyRunnable < MAX_RUNNABLE) {
            fibers[firstEmptyRunnable++] = new Fiber(sr);
        }
    }

    public void startAll() {
        for (int i=0; i<MAX_RUNNABLE; i++) {
            if (fibers[i] != null) {
                fibers[i].start();
            }
        }
    }

    public void joinLast() {
        try {
            fibers[firstEmptyRunnable - 1].join();
        } catch (Exception e) {
            System.err.println("ERROR TaskManager joinLast: " + e.toString());
        }
    }
}
