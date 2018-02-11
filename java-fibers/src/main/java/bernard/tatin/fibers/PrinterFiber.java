package bernard.tatin.tools;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

public class PrinterFiber implements SuspendableRunnable {
    @Override
    public void run() throws SuspendExecution {
        PrintElement x;

        try {
            while ((x = Printer.printerChannel.receive()) != null) {
                x.println();
            }
        } catch (InterruptedException ie) {
            System.err.println("ERROR printerChannel receive is interrupted: " +
                    ie.toString());
        } catch (Exception ie) {
            System.err.println("ERROR printerChannel receive: " + ie.toString());
        }
    }
}
