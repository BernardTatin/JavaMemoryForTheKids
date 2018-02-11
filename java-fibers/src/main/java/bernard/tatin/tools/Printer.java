package bernard.tatin.tools;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.Strand;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import co.paralleluniverse.strands.concurrent.ReentrantLock;


public class Printer {
    private final Channel<PrintElement> printerChannel = Channels.newChannel(0);
    public static final Printer thePrinter = new Printer();
    private final BlockingQueue<PrintElement> queue = new LinkedBlockingDeque<>(100);
    private final ReentrantLock lock = new ReentrantLock(true);

    private Fiber<Void> fiberRead =
            new Fiber<Void>(() -> {
                PrintElement x;

                while ((x=printerChannel.receive()) != null) {
                    x.println();
                }
            });
    private Fiber<Void> fiberWrite =
            new Fiber<Void>(() -> {
                while (true) {
                    while (!queue.isEmpty()) {
                        printerChannel.send(queue.take());
                    }
                    Strand.sleep(100);
                }
            });

    private Printer() {

    }

    private void write(PrintElement elt) {
        boolean locked;
/*        if (elt == null) {
            System.err.println("ERROR write null elt");
            return;
        }
        if (printerChannel == null) {
            System.err.println("ERROR printerChannel is null");
            return;
        }
        if (lock == null) {
            System.err.println("ERROR lock is null");
            return;
        }*/
        lock.lock();
        locked = true;
        try {
            printerChannel.send(elt);
        } catch(Exception e) {
            System.err.println("ERROR printerChannel send: " + e.toString());
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public void run() {
        System.out.println("Start fiberWrite");
//        fiberWrite.start();
        try {
            System.out.println("Start fiberRead");
            fiberRead.start();
            System.out.println("All fibers started");
        } catch(Exception e) {
            System.err.println("ERROR: " + e.toString());
        }
    }

    public void printString(String str) {
        try {
//            queue.put(new PrintElement(System.out, str));
            write(new PrintElement(System.out, str));
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString());
        }
    }

    public void printStrings(String[] strings) {
        try {
            for (String str : strings) {
//                queue.put(new PrintElement(System.out, str));
                write(new PrintElement(System.out, str));
            }
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString());
        }
    }

    public void printError(String str) {
        try {
//            queue.put(new PrintElement(System.err, str));
            write(new PrintElement(System.err, str));
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printError): " + e.toString());
        }
    }

}
