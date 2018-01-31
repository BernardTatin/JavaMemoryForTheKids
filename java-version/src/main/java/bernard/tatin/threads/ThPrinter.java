package bernard.tatin.threads;

import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class ThPrinter extends AThConsumer implements IThPrinterClient {
    private static final ThPrinter mainPrinter = new ThPrinter();
    private final BlockingQueue<PrintElement> queue = new LinkedBlockingDeque<>(10);

    private ThPrinter() {
    }

    public static ThPrinter getMainInstance() {
        return mainPrinter;
    }

    @Override
    public String getName() {
        return "ThPrinter";    
    }
    
    @Override
    public void innerLoop() {
        try {
            wait();
            while (!queue.isEmpty()) {
                queue.take().print();
            }
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::consume): " + e.toString());
        }
    }

    @Override
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

    @Override
    public synchronized void printString(String str) {
        try {
            queue.put(new PrintElement(System.out, str));
            notify();
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printString): " + e.toString());
        }
    }

    @Override
    public synchronized void printError(String str) {
        try {
            queue.put(new PrintElement(System.err, str));
            notify();
        } catch (Exception e) {
            System.err.println("ERROR (ThPrinter::printError): " + e.toString());
        }
    }

    private class PrintElement {
        public final PrintStream stream;
        public final String line;

        public PrintElement(PrintStream s, String l) {
            stream = s;
            line = l;
        }

        public void print() {
            stream.println(line);
        }
    }
}
