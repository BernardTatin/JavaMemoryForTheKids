package bernard.tatin.tools;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.Strand;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Printer {
    private static final Channel<PrintElement> printerChannel = Channels.newChannel(0);
    public static final Printer thePrinter = new Printer();
    private final BlockingQueue<PrintElement> queue = new LinkedBlockingDeque<>(10);

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

    public void run() {
        System.out.println("Start fiberWrite");
        fiberWrite.start();
        try {
            System.out.println("Start fiberRead");
            fiberRead.start().join();
            System.out.println("All fibers started");
        } catch(Exception e) {
            System.err.println("ERROR: " + e.toString());
        }
    }

    public void printString(String str) {
        try {
            queue.put(new PrintElement(System.out, str));
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString());
        }
    }

    public void printStrings(String[] strings) {
        try {
            for (String str : strings) {
                queue.put(new PrintElement(System.out, str));
            }
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString());
        }
    }

    public void printError(String str) {
        try {
            queue.put(new PrintElement(System.err, str));
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printError): " + e.toString());
        }
    }

}
