package bernard.tatin.fibers;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.fibers.FiberForkJoinScheduler;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.concurrent.ReentrantLock;

import java.lang.InterruptedException;
import java.util.concurrent.*;

import bernard.tatin.tools.Printer;
import bernard.tatin.tools.TaskManager;

import static java.lang.System.out;

/*
export M2=~/.m2/repository
export QUASAR=${M2}/co/paralleluniverse

export CLASSPATH_FIBER=java-1.8-fibers-1.4.0.jar:${QUASAR}/quasar-core/0.7.9/quasar-core-0.7.9-jdk8.jar:${M2}/com/esotericsoftware/kryo/4.0.0/kryo-4.0.0.jar:${M2}/com/googlecode/concurrentlinkedhashmap/concurrentlinkedhashmap-lru/1.4/concurrentlinkedhashmap-lru-1.4.jar:${M2}/com/google/collections/google-collections/1.0/google-collections-1.0.jar:/${M2}/com/google/guava/guava/20.0/guava-20.0.jar


(cd target; java -Xmx412m -classpath ${CLASSPATH_FIBER} -javaagent:${QUASAR}/quasar-core/0.7.9/quasar-core-0.7.9-jdk8.jar=m -Dco.paralleluniverse.fibers.verifyInstrumentation=true bernard.tatin.fibers.PlayWithFibers)
 */

class PlayWithFibers {
    private static final PlayWithFibers app = new PlayWithFibers();

    private PlayWithFibers() {

    }

    private static void loop(String name) {
            String xxline;
            int i = 0;
            while (true) {
                xxline = name + " line " + String.format("%6d", i++);
                Printer.thePrinter.printString(xxline);
//                try {
//                    Fiber.sleep(100);
//                } catch (Exception e) {
//                    System.err.println("ERROR " + e.toString());
//                }
            }
    }

    public static void main(String[] args) {
        Fiber[] fibers = new Fiber[4];
        ReentrantLock lock = new ReentrantLock(true);

        Printer.thePrinter.run();
        for (int i=0; i<4; i++) {
            final int id = i;
            TaskManager.taskManager.addRunnable(new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution {
                    int count = 0;
                    String name = "ww " + String.format("%d", id);
                    try {
                        Fiber.sleep(200);
                    } catch(InterruptedException e) {
                        System.err.println("Fiber.Sleep interrupted");
                    }
                    while (true) {
                        Strand.parkNanos(100000000);
                        lock.lock();
                        try {
                            Printer.thePrinter.printString(name + " line " + String.format("%6d", count++));
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            });
        }
        TaskManager.taskManager.startAll();
        TaskManager.taskManager.joinLast();
/*        for (int i=0; i<4; i++) {
            fibers[i].start();
        }
        try {
            fibers[3].join();
        } catch (Exception e) {
            System.err.println("ERROR on join");
        }*/
    }
}
