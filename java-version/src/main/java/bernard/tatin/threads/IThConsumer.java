package bernard.tatin.threads;

public interface IThConsumer {
    void consume();
    void initialize();
    void innerLoop();
}
