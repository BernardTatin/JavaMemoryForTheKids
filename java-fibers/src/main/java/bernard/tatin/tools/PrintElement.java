package bernard.tatin.tools;

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
