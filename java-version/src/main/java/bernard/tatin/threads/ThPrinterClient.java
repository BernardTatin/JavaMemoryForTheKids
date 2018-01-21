package bernard.tatin.threads;

public class ThPrinterClient implements IThPrinterClient {
    public void printString(String str) {
        ThPrinter.getMainInstance().printString(str);
    }
    public void printStrings(String[] strings) {
        ThPrinter.getMainInstance().printStrings(strings);
    }

    public void printError(String str) {
        ThPrinter.getMainInstance().printError(str);
    }

}
