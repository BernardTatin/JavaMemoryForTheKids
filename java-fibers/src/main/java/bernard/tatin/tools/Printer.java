package bernard.tatin.tools;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;


public class Printer {
    public static final Printer thePrinter = new Printer();
    private final Channel<PrintElement> printerChannel = Channels.newChannel(50);
    private Fiber<Void> fiberRead;

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
                        System.err.println("ERROR printerChannel receive is interrupted: " +
                                ie.toString());
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
        write(new PrintElement(System.out, str));
    }

    public void printStrings(String[] strings) {
        for (String str : strings) {
            write(new PrintElement(System.out, str));
        }
    }

    public void printError(String str) {
        write(new PrintElement(System.err, str));
    }

}
