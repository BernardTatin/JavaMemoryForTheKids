[![Known Vulnerabilities](https://snyk.io/test/github/bernardtatin/javamemoryforthekids/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/bernardtatin/javamemoryforthekids?targetFile=pom.xml)

# Java Memory For the Kids

Last time I worked with _Java_, it was with version 1.4 while version 1.5 was announced. Today, I need to understand how _Java_ manage its memory while I need to code with it.

Here is a tool to experiment with memory in _Java_. It works only on _Unix_ and at least with _Java_ 1.8. I get memory and process informations from the **proc** file system specific to _Unix_. Today, it is tested only on _Linux_ but I hope it works on _FreeBSD_.

The current code is a field of experiments and can't be a model to follow. 

Documentation will grow - slowly - with the rest of the project. 

I use **Maven** which is well supported by a lot of modern IDEs. 

## JDKs

With **JDK 9**, tests failed, even if they're empty. Run **maven** with the option `-Dmaven.test.skip=true`.
