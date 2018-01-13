package bernard.tatin.Threads;

public class ThPrinterClient {
    public void sendString(String str) {
        ThPrinter.mainPrinter.sendString(str);
    }

    public void sendError(String str) {
        ThPrinter.mainPrinter.sendError(str);
    }

}
