package bernard.tatin;

/*
Run :
java -Xmx412m -classpath /home/bernard/git/recallMeJava/java-with-libs/target/classes:/home/bernard/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:/home/bernard/.m2/repository/io/vavr/vavr/0.9.0/vavr-0.9.0.jar:/home/bernard/.m2/repository/io/vavr/vavr-match/0.9.0/vavr-match-0.9.0.jar:/home/bernard/.m2/repository/org/eclipse/collections/eclipse-collections-api/9.1.0/eclipse-collections-api-9.1.0.jar:/home/bernard/.m2/repository/org/eclipse/collections/eclipse-collections/9.1.0/eclipse-collections-9.1.0.jar bernard.tatin.JavaMemoryForTheKids 
export here=$(pwd)
export m2=$HOME/.m2/repository
java -Xmx412m -cp $here/target/classes:$m2/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:$m2/io/vavr/vavr/0.9.0/vavr-0.9.0.jar:$m2/io/vavr/vavr-match/0.9.0/vavr-match-0.9.0.jar
java -Xmx412m -classpath $here/target/classes:$m2/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:$m2/io/vavr/vavr/0.9.0/vavr-0.9.0.jar:$m2/io/vavr/vavr-match/0.9.0/vavr-match-0.9.0.jar
*/

import bernard.tatin.procfs.ProcessCommandLine;
import bernard.tatin.procfs.ProcessID;
import bernard.tatin.procfs.StatM;
import bernard.tatin.threads.AThConsumer;
import bernard.tatin.threads.ThPrinter;
import bernard.tatin.threads.ThPrinterClient;
import bernard.tatin.tools.Counter;
import bernard.tatin.tools.ThMemoryFiller;

/**
 * Hello world!
 */
class JavaMemoryForTheKids extends ThPrinterClient {
   private static String titleLine = null;
   private final Counter count = new Counter(25);

    /**
     * @param args command line args
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // System signals handled
            // it works, but I don't like this

            // stop other threads
            AThConsumer.isRunning.set(false);
            // wait a little
            try {
               Thread.sleep(100);
            } catch (Exception e) {
                // don't use printError, ThPrinter is stopped
               System.err.println("Signal catching interrupted...");
            }
            // don't use printString, ThPrinter is stopped
            System.out.println("Signal caught, interrupt received, exit...");
         }));

      JavaMemoryForTheKids jm = new JavaMemoryForTheKids();
        // initialize and run threads
      ThPrinter.getMainInstance().initialize();
      ThMemoryFiller.getMainInstance().initialize();

      jm.innerLoop();
   }

   private void showTitle() {
      if (titleLine == null) {
         String aTitle = StatM.getMainInstance().getStatsTitleLine();
         if (aTitle != null) {
            titleLine = aTitle;
         } else {
            titleLine ="titleLine is NULLLLLL";
         }
      }

      printStrings(
                new String[] {"PID          " + String.valueOf(ProcessID.getPID()),
                        "Command line " + ProcessCommandLine.getCommandLine(),
                        titleLine});
   }

   private void innerLoop() {
      while (true) {
         long memorySize = ThMemoryFiller.getMainInstance().getMemorySize();
         String aString = StatM.getMainInstance().getStatsLine(memorySize);

         if (count.getValue() == 0) {
            showTitle();
         }

         if (aString != null) {
            printString(aString);
         } else {
            printError("ERROR reading statm file");
         }
         try {
            Thread.sleep(100L);
         } catch (InterruptedException e) {
            printError("ERROR InterruptedException : " + e.getMessage());
         }
      }
   }
}
