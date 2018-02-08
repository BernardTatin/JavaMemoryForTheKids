package bernard.tatin.tools;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.Strand;
import java.io.PrintStream;

public class PrintElement {
    private final PrintStream stream;
    private final String line;

    public PrintElement(PrintStream s, String l) {
        stream = s;
        line = l;
    }

    public void print() {
        stream.print(line);
    }

    public void println() {
        stream.println(line);
    }
}
