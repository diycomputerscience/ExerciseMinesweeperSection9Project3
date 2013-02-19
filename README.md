<h1>Section 9 Project 3</h1>

<h2>Overview</h2>

In the previous section, we introduced how to use a logging library, like   log4j to generate log statements. At this point the code is ready to be shipped. The code has several things that can be improved, but it works. 

Now, we don't really endorse shipping code which is not perfect. However, this is an unfortunate but true scenario, which we are trying to simulate.

Coming back to shipping code to the client – how do you think we should ship the code ? Should we just copy all the class files and email them to the client with instructions on how to run the code ? We could do that, but  sending such a large number of files is not a good idea. A better way is to create an executable jar file, and bundle it along with any dependencies (other libraries/jar files). Even though we provide an executable jar, it is always a good idea to create a .bat and .sh file to run the software from Windows and *nix respectively. 

Since we have decided to create an executable jar file, the next logical question to ask is - how do we create one ? Should e manually create it with all the class files, or should we sue an automated process ? We need to remember that the deployment team, may or may not consist of developers. Hence it would be very useful if we can provide a build file which they can execute, to compile and test the code, and to create the final distributable (the executable jar file).

If we were programming in C, we would have created a make file. However, in the java world, the common way of creating a build file is to use [ANT](http://ant.apache.org/). An ANT build file is typically called build.xml. We have provided you with a skeleton ```build.xml``` file with targets and their dependencies already in place. You have to actually implement the targets. You will have to download and install ANT, if you do not already have it on your system. From here on we stop execissive hand holding and hope you will figure out how to install ANT, do a brief read of a tutorial or manual, and implement the targets. Our forums are as always open for your queries :-)
