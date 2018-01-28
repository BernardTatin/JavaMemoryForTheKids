package bernard.tatin.threads;

public interface IThPrinterClient {
    void printString(String str);
    void printStrings(String[] strings);
    void printError(String str);
}
