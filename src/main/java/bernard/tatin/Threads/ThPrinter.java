package bernard.tatin.Threads;

import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class PrintElement {
    public PrintStream stream;
    public String line;

    public PrintElement(PrintStream s, String l) {
        stream = s;
        line = l;
    }
}

public class ThPrinter implements ThConsumer, Runnable {
    private final static ThPrinter mainPrinter = new ThPrinter();
    private final BlockingQueue<PrintElement> queue = new LinkedBlockingDeque<>(10);

    private ThPrinter() {
    }

    public static ThPrinter getMainInstance() {
        return mainPrinter;
    }

    public void consume() {
        PrintElement pElement;
        while (true) {
            try {
                wait();
                while (!queue.isEmpty()) {
                    pElement = queue.take();
                    pElement.stream.println(pElement.line);
                }
            } catch (Exception e) {
                System.err.println("ERROR (ThPrinter::consume): " + e.toString());
            }
        }
    }

    public synchronized void printStrings(String[] strings) {
        try {
            for (String str: strings) {
                queue.put(new PrintElement(System.out, str));
            }
            notify();
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString());
        }
    }
    public synchronized void printString(String str) {
        try {
            queue.put(new PrintElement(System.out, str));
            notify();
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString());
        }
    }

    public synchronized void printError(String str) {
        try {
            queue.put(new PrintElement(System.err, str));
            notify();
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printError): " + e.toString());
        }
    }

    public void initialize() {
        Thread thePrinter = new Thread(this, "ThPrinter");

        thePrinter.setDaemon(true);
        thePrinter.start();
    }

    public synchronized void run() {
        consume();
    }
}
