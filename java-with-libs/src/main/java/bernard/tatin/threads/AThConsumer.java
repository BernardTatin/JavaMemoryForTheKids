package bernard.tatin.threads;

import bernard.tatin.common.IThConsumer;
import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;

/*
compiling and running:

export CLASSPATH2=~/.m2/repository/io/vavr/vavr/0.9.2/vavr-0.9.2.jar:~/.m2/repository/io/vavr/vavr-match/0.9.2/vavr-match-0.9.2.jar:/home/bernard/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:java-1.8-with-libs-1.4.0.jar:/home/bernard/git/recallMeJava/java-common/target/java-1.8-common-1.4.0.jar:~/.m2/repository/co/paralleluniverse/quasar-core/0.7.7/quasar-core-0.7.7-jdk8.jar:~/.m2/repository/com/esotericsoftware/kryo/4.0.0/kryo-4.0.0.jar:~/.m2/repository/com/googlecode/concurrentlinkedhashmap/concurrentlinkedhashmap-lru/1.4/concurrentlinkedhashmap-lru-1.4.jar:~/.m2/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar:~/.m2/repository/com/google/guava/guava/20.0/guava-20.0.jar

 mvn package -P sunjdk8 && (echo ${CLASSPATH2}; cd java-with-libs/target; java -Xmx412m -classpath ${CLASSPATH2} -javaagent:/home/bernard/.m2/repository/co/paralleluniverse/quasar-core/0.7.7/quasar-core-0.7.7-jdk8.jar bernard.tatin.JavaMemoryForTheKids)

TODO suppress current warning:

   3016.684M |    394.621M |    467.551M |    260.000M |    366.500M |     23.959M |     11.500M |	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:545)

	at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
	at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:438)
	at bernard.tatin.tools.ThMemoryFiller.lambda$innerLoop$87346f04$1(ThMemoryFiller.java:43)
	at bernard.tatin.tools.ThMemoryFiller$$Lambda$4/1133999099.apply(Unknown Source)
	at io.vavr.control.Try.of(Try.java:75)
	at bernard.tatin.tools.ThMemoryFiller.innerLoop(ThMemoryFiller.java:41)
	at bernard.tatin.threads.AThConsumer.consume(AThConsumer.java:38)
	at bernard.tatin.threads.AThConsumer.run(AThConsumer.java:32)
	at bernard.tatin.threads.AThConsumer$1.run(AThConsumer.java:20)
	at bernard.tatin.threads.AThConsumer$1.run(AThConsumer.java:17)
WARNING: fiber Fiber@10000001:fiber-10000001[task: ParkableForkJoinTask@4f82effd(Fiber@10000001), target: null, scheduler: co.paralleluniverse.fibers.FiberForkJoinScheduler@2c9b2193] is blocking a thread (Thread[ForkJoinPool-default-fiber-pool-worker-1,5,main]).

ThMemoryFiller.java:43): Stream.concat(Arrays.stream(memory), Arrays.stream(memory_unit)).
                                    toArray(Byte[]::new)
 */
public abstract class AThConsumer implements IThConsumer, Runnable {
    public static final ProtectedValue<Boolean> isRunning = new ProtectedValue<Boolean>(true);

    @Override
    public void initialize() {
        AThConsumer me = this;
        try {
            Fiber<AThConsumer> fiber = new Fiber<AThConsumer>() {
                protected AThConsumer run() throws SuspendExecution, InterruptedException {
                    // your code
                    me.run();
                    return me;
                }
            }.start();
        } catch (Exception e) {
            System.err.println(">>>>>>>>>>>> ERROR");
            System.err.println("............ " + e.toString());
        }
    }

    @Override
    public void run() {
        consume();
    }

    @Override
    public void consume() {
        while (AThConsumer.isRunning.get()) {
            innerLoop();
        }
    }

    protected void doSleep(long ms) {
        try {
            sleep100B(ms);
        } catch (SuspendExecution e) {
            System.err.println(">>>>>>>>>>>> ERROR");
            System.err.println("............ " + e.toString());
        } catch (Exception e) {
            System.err.println(">>>>>>>>>>>> ERROR");
            System.err.println("............ " + e.toString());
        }
//        Try<Boolean> sleep100 = Try.of(() -> sleep100B(ms));
//        if (sleep100.isFailure()) {
//            // TODO: must use ThPrinter
//            System.err.println("ERROR (ThMemoryFiller::consume): "
//                    + sleep100.getCause().toString());
//        }
    }

    private Boolean sleep100B(long ms) throws InterruptedException, SuspendExecution {
        Fiber.sleep(ms);
        return new Boolean(true);
    }

}
