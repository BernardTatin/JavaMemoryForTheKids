package bernard.tatin.threads;

import bernard.tatin.threads.ThPrinter;

public class ThPrinterClient implements IThPrinterClient {
    private ThPrinter mainPrinter = ThPrinter.getMainInstance();

    public void printString(String str) {
        mainPrinter.printString(str);
    }

    public void printStrings(String[] strings) {
        mainPrinter.printStrings(strings);
    }

    public void printError(String str) {
        mainPrinter.printError(str);
    }

}
