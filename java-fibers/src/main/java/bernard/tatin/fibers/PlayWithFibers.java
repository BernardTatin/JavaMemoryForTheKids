package bernard.tatin.fibers;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.FiberForkJoinScheduler;
import co.paralleluniverse.strands.SuspendableRunnable;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;

import java.util.concurrent.*;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.lang.System.out;

//import bernard.tatin.fork.*;

class PlayWithFibers {
    private PlayWithFibers() {

    }

    private static <Message> Channel<Message> newChannel() {
        int mailboxSize = 5;
        Channels.OverflowPolicy policy = Channels.OverflowPolicy.BLOCK;
        boolean singleProducer = true;
        boolean singleConsumer = true;

        return Channels.newChannel(mailboxSize, policy, singleProducer, singleConsumer);
    }

    public static void main(String[] arguments) throws ExecutionException, InterruptedException {
        final Channel<String> ch = newChannel();
//        final ForkJoinPool forkJoinPool =
//                new ForkJoinPool(4,
//                        ForkJoinPool.defaultForkJoinWorkerThreadFactory,
//                        null,
//                        true);
        final FiberForkJoinScheduler joinScheduler = FiberForkJoinScheduler("Scheduler", 4);
        final ForkJoinPool forkJoinPool = joinScheduler.getForkJoinPool();
        final Fiber receiver = new Fiber("receiver", joinScheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                while (true) {
                    Fiber.sleep(2000L);
                    String s = ch.receive();
                    out.printf("                   -> '%s'\n", s);
                }
            }
        }).start();
        final Fiber sender = new Fiber("sender", joinScheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                for (int i=1; ; i++) {
                    Fiber.sleep(200L);
                    String message = format("Hello %4d", i);
                    ch.send(message);
                    out.printf("'%s' ->\n", message);
                }
            }
        }).start();

        sender.join();
        receiver.join();
    }
}
