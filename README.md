jcommon
=======
Common utils, abstractions and tools that I use in my projects. They make Java pleasant.

##YCollections
Collections as they should be.
```java
    String names = al(new File("/home/user/").listFiles())
            .filter(File::isDirectory)                  //only dirs
            .map(File::getName)                         //get name
            .filter(n -> n.startsWith("."))             //only invisible
            .sorted()                                   //sorted
            .foldLeft("", (r, n) -> r + ", " + n);      //to print fine
    System.out.println(names);
```

* each collection extends standard java collection, so you can use it whenever standard collection would else be used
* just add some useful methods (java8's .stream() I consider not usable)
* all added methods are not modifying collection
* yes, there are copies everywhere, but hey! Optimize it when your profiler say you so!
* very convinient


##ANIO
A normal IO for Java. (because java.nio is not for human beings)

```java
    ASocket server = new ASocket(8000, socket -> {
        socket.send("hello!");
        int a = 3;
        socket.onData = data -> {
            System.out.println(a);
        };
    });

    client1 = new ASocket("localhost", 8000, socket -> {
        socket.send("hello1b");
        socket.onData = data -> {
            client1.clientSocket.send("hello1b");
        };
    });

```
* callbacks on connect, callbacks on data
* node.js style (in the good sense)
* replaceable serialization engine


##YADS
yet another data syntax (the perfect one actually)


