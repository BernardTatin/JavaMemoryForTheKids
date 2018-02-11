package bernard.tatin.tools;

import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.concurrent.ReentrantLock;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.fibers.FiberForkJoinScheduler;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;


public class Printer {
    private final Channel<PrintElement> printerChannel = Channels.newChannel(50);
    public static final Printer thePrinter = new Printer();
    public static FiberScheduler scheduler;

    private Fiber<Void> fiberRead;
/*    private Fiber<Void> fiberWrite =
            new Fiber<Void>(() -> {
                while (true) {
                    while (!queue.isEmpty()) {
                        printerChannel.send(queue.take());
                    }
                    Strand.sleep(100);
                }
            });*/

    private Printer() {
    }

    private void write(PrintElement elt) {
        try {
            printerChannel.send(elt);
        } catch(Exception e) {
            System.err.println("ERROR printerChannel send: " + e.toString());
        }
    }

    public void run() {
        try {
            System.out.println("Printer.run(): create fiberRead");
            TaskManager.taskManager.addRunnable(new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution {
                    PrintElement x;

                    try {
                        while ((x = printerChannel.receive()) != null) {
                            x.println();
                        }
                    } catch (InterruptedException ie) {
                        System.err.println("ERROR printerChannel receive is interrupted: " + ie.toString());
                    } catch (Exception ie) {
                        System.err.println("ERROR printerChannel receive: " + ie.toString());
                    }
                }
            });
            System.out.println("Printer.run(): OKK");
        } catch(Exception e) {
            System.err.println("ERROR Printer.run(): " + e.toString());
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
