package bernard.tatin.Threads;

public class ThPrinterClient {
    protected void printString(String str) {
        ThPrinter.getMainInstance().printString(str);
    }
    protected void printStrings(String[] strings) {
        ThPrinter.getMainInstance().printStrings(strings);
    }

    protected void printError(String str) {
        ThPrinter.getMainInstance().printError(str);
    }

}
