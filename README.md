
# Java Memory For the Kids

Last time I worked with Java, it was with version 1.4 while version 1.5 was announced. Today, I need to understand how Java manage its memory while I need to code with it.

Here is a tool to experiment with memory in Java. It works only on Linux and at least with Java 1.8. I get memory and process informations from the _proc_ file system specific to Unix. Today, it works only on Linux but I hope I can extend it to FreeBSD.

The current code is a field of experiments and can't be a model to follow. 

Documentation will grow - slowly - with the rest of the project. I use Maven which is well supported by a lot of modern IDEs. 

## JDKs

With JDK 9, tests failed, even if they're empty. Run maven with the option `-Dmaven.test.skip=true`.