jcommon
=======
Common utils, abstractions and tools that I use in my projects. They make Java pleasant.

##YCollections
**yk.jcommon.collections**

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
**yk.jcommon.net.anio**

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
* node.js style (in a good sense)
* replaceable serialization engine


##YADS
**yk.lang.yads**

Yet Another Data Syntax (the perfect one actually).

How would UI markup look like:

```
import=ui
HBox {
  pos=100, 200
  VBox {
    Input {text='input here'}
    Button {text='hello world'}
  }
}
```
How would some game config look lie:
```
import=my.game
Npc {
  name='Grumble Fingur'
  type=Goblin
  model={type=AngryBastard colorScheme=red}
  items= items.Hat{name='Hat of sun'}, random{type=ring}
  }
}
```

###syntax
* no commas or semicolons needed, so noise level is very low
* strings and keys without quotes (that is also reduces noise)
* but can use "" or '' (for strings with spaces, for example)
* ' ' for strings - so you can include YADS in java strings without escaping
* spaces and tabs don't have special meaning (like in yaml or python), so you can arrange data-text as you wish, even in one line
* multiline strings (with both "" and '' quotes)
* equal for key=value
* numbers, booleans
* utf8, no restriction on keys or strings
* comments (one line // and multiline /**/)
* carefully controlled comma use to aviod one level parentheses like in {pos=10, 10 size=100, 200}

###serialization
* simple parsing or additionally - deserialization
* serialization of any data structure to string
* key states field name
* value type is inferred from field type
* value type could be stated explicitly
* arrays are also supported
* if type is unknown - array, map, or special class is constructed
* all pares are initilizing into fields (or key=value for map)
* other elements are given to init(elements) method
* if value is string but type is not, then Type.parse with that string is called (TODO)
* you can specify imports, or fully qualified class name
* or do nothing, and get map, array, or YADClass with name explicitly specified

##mvn artifact
```xml
<repository>
   <id>yk.jcommon</id>
   <url>https://github.com/kravchik/mvn-repo/raw/master</url>
</repository>

<dependency>
    <groupId>yk</groupId>
    <artifactId>jcommon</artifactId>
    <version>0.101</version>
</dependency>
```


