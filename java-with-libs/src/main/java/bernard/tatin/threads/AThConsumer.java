package bernard.tatin.threads;

import bernard.tatin.common.IThConsumer;
import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import io.vavr.control.Try;

/*
compiling and running:

export CLASSPATH2=~/.m2/repository/io/vavr/vavr/0.9.2/vavr-0.9.2.jar:~/.m2/repository/io/vavr/vavr-match/0.9.2/vavr-match-0.9.2.jar:/home/bernard/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:java-1.8-with-libs-1.4.0.jar:/home/bernard/git/recallMeJava/java-common/target/java-1.8-common-1.4.0.jar:~/.m2/repository/co/paralleluniverse/quasar-core/0.7.7/quasar-core-0.7.7-jdk8.jar:~/.m2/repository/com/esotericsoftware/kryo/4.0.0/kryo-4.0.0.jar:~/.m2/repository/com/googlecode/concurrentlinkedhashmap/concurrentlinkedhashmap-lru/1.4/concurrentlinkedhashmap-lru-1.4.jar:~/.m2/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar:~/.m2/repository/com/google/guava/guava/20.0/guava-20.0.jar

 mvn package -P sunjdk8 && (echo ${CLASSPATH2}; cd java-with-libs/target; java -Xmx412m -classpath ${CLASSPATH2} -javaagent:/home/bernard/.m2/repository/co/paralleluniverse/quasar-core/0.7.7/quasar-core-0.7.7-jdk8.jar bernard.tatin.JavaMemoryForTheKids)

 */
public abstract class AThConsumer implements IThConsumer, Runnable {
    public static final ProtectedValue<Boolean> isRunning = new ProtectedValue<Boolean>(true);

    @Override
    public void initialize() {
        AThConsumer me = this;
        Fiber<AThConsumer> fiber = new Fiber<AThConsumer>() {
            protected AThConsumer run() throws SuspendExecution, InterruptedException {
                // your code
                me.run();
                return me;
            }
        }.start();
//        Thread theThread = new Thread(this, getName());
//
//        theThread.setDaemon(true);
//        theThread.start();
    }

    @Override
    public synchronized void run() {
        consume();
    }

    @Override
    public void consume() {
        while (AThConsumer.isRunning.get()) {
            innerLoop();
        }
    }

    protected void doSleep(long ms) {
        Try<Boolean> sleep100 = Try.of(() -> sleep100B(ms));
        if (sleep100.isFailure()) {
            // TODO: must use ThPrinter
            System.err.println("ERROR (ThMemoryFiller::consume): "
                    + sleep100.getCause().toString());
        }
    }

    private Boolean sleep100B(long ms) throws InterruptedException {
        Thread.sleep(ms);
        return new Boolean(true);
    }

}
