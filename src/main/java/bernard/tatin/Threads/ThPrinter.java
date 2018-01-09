package bernard.tatin.Threads;

import java.util.concurrent.BlockingQueue;

public class ThPrinter implements ThConsumer, Runnable {
    private final BlockingQueue<String> queue;
    public static ThPrinter mainPrinter;

    public ThPrinter(BlockingQueue<String> q) {
        queue = q;
        ThPrinter.mainPrinter = this;
    }

    public boolean consume() {
        String line;
        while (true) {
            try {
                wait();
                while (!queue.isEmpty()) {
                    line = queue.take();
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.err.println("ERROR (ThPrinter::consume): " + e.toString());
            }
        }
    }

    public synchronized void sendString(String str) {
        try {
            queue.put(str);
            notify();
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::sendString): " + e.toString());
        }
    }

    public synchronized void sendError(String str) {
        try {
            System.err.println(str);
            notify();
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::sendError): " + e.toString());
        }
    }

    public synchronized void sendError(Exception e) {
        sendError(e.toString());
    }

    public synchronized void run() {
        consume();
    }
}
