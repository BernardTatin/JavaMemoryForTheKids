package bernard.tatin.fibers;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.Strand;
import java.util.concurrent.*;

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
        final Channel<Integer> chan=Channels.newChannel(0);
        new Fiber<Void>(() -> {
            for (int i=0; i < 10; i++) {
                Strand.sleep(100);
                chan.send(i);
            }
            chan.close();
        }
        ).start();
        new Fiber<Void>(() -> {
            Integer x;
            while ((x=chan.receive()) != null) {
                System.out.println("Received from channel: " + x);
            }
        }
        ).start().join();
    }
}
