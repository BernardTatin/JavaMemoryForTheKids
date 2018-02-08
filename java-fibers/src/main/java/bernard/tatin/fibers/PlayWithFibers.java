package bernard.tatin.fibers;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.Strand;
import java.util.concurrent.*;

import bernard.tatin.tools.Printer;

import static java.lang.System.out;

/*
export M2=~/.m2/repository
export QUASAR=${M2}/co/paralleluniverse

export CLASSPATH_FIBER=java-1.8-fibers-1.4.0.jar:${QUASAR}/quasar-core/0.7.9/quasar-core-0.7.9-jdk8.jar:${M2}/com/esotericsoftware/kryo/4.0.0/kryo-4.0.0.jar:${M2}/com/googlecode/concurrentlinkedhashmap/concurrentlinkedhashmap-lru/1.4/concurrentlinkedhashmap-lru-1.4.jar:${M2}/com/google/collections/google-collections/1.0/google-collections-1.0.jar:/${M2}/com/google/guava/guava/20.0/guava-20.0.jar


(cd target; java -Xmx412m -classpath ${CLASSPATH_FIBER} -javaagent:${QUASAR}/quasar-core/0.7.9/quasar-core-0.7.9-jdk8.jar=m -Dco.paralleluniverse.fibers.verifyInstrumentation=true bernard.tatin.fibers.PlayWithFibers)
 */

class PlayWithFibers {
    private PlayWithFibers() {

    }


    public static void main(String[] args) throws Exception {

        new Fiber<Void>(() -> {
            String xxline;
            int i = 0;
            while (true) {
                xxline = "xxline " + String.format("%6d", i++);
                Printer.thePrinter.printString(xxline);
                Strand.sleep(100);
            }
        }).start();
        Printer.thePrinter.run();
    }
}
