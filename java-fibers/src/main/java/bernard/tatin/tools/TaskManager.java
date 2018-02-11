package bernard.tatin.tools;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.SuspendableRunnable;

public class TaskManager {
    public final static TaskManager taskManager = new TaskManager();
    public final static int MAX_RUNNABLE = 75;
    private Fiber[] fibers = new Fiber[TaskManager.MAX_RUNNABLE];
    private int firstEmptyRunnable = 0;

    private TaskManager() {
        for (int i=0; i<TaskManager.MAX_RUNNABLE; i++) {
            fibers[i] = null;
        }
    }

    public void addRunnable(SuspendableRunnable sr) {
        if (firstEmptyRunnable < TaskManager.MAX_RUNNABLE) {
            fibers[firstEmptyRunnable++] = new Fiber(sr);
        }
    }

    public void startAll() {
        for (int i=0; i<firstEmptyRunnable; i++) {
            fibers[i].start();
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
