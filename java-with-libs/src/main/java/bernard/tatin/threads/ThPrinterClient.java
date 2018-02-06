package bernard.tatin.threads;


import bernard.tatin.common.IThPrinterClient;

public class ThPrinterClient implements IThPrinterClient {
    private ThPrinter mainPrinter = ThPrinter.getMainInstance();

    @Override
    public void printString(String str) {
        mainPrinter.printString(str);
    }

    @Override
    public void printStrings(String[] strings) {
        mainPrinter.printStrings(strings);
    }

    @Override
    public void printError(String str) {
        mainPrinter.printError(str);
    }

}
