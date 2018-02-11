package bernard.tatin.tools;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;


public class Printer {
    public static final Printer thePrinter = new Printer();
    public static final Channel<PrintElement> printerChannel = Channels.newChannel(50);
    private Fiber<Void> fiberRead;

    private Printer() {
    }

    private void write(PrintElement elt) {
        try {
            Printer.printerChannel.send(elt);
        } catch(Exception e) {
            System.err.println("ERROR printerChannel send: " + e.toString());
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
