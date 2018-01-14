package bernard.tatin.Threads;

public class ThPrinterClient {
    protected void printString(String str) {
        ThPrinter.mainPrinter.printString(str);
    }
    protected void printStrings(String[] strings) {
        ThPrinter.mainPrinter.printStrings(strings);
    }

    protected void printError(String str) {
        ThPrinter.mainPrinter.printError(str);
    }

}
