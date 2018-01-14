package bernard.tatin.Threads;

public class ThPrinterClient {
    protected void sendString(String str) {
        ThPrinter.mainPrinter.sendString(str);
    }
    protected void sendStrings(String[] strings) {
        ThPrinter.mainPrinter.sendStrings(strings);
    }

    protected void sendError(String str) {
        ThPrinter.mainPrinter.sendError(str);
    }

}
