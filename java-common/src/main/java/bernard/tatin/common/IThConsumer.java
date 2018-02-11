package bernard.tatin.common;

public interface IThConsumer {
    void consume();
    void initialize();
    void innerLoop();
    String getName();
}
