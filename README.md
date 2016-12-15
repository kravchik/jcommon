jcommon
=======
Common utils, abstractions and tools that I use in my projects. They make Java pleasant.

##YCollections
**yk.jcommon.collections**

Collections as they should be.

Example 1
```java
    String names = al(new File("/home/user/").listFiles())
            .filter(File::isDirectory)                  //only dirs
            .map(File::getName)                         //get name
            .filter(n -> n.startsWith("."))             //only invisible
            .sorted()                                   //sorted
            .foldLeft("", (r, n) -> r + ", " + n);      //to print fine
    System.out.println(names);
```
Example 2
```java
    YList<String> all = al("shift", "ctrl", "alt", "super");
    System.out.println(all.shuffle(all)
            .map(p -> p.toSet().toList().sorted().toString())
            .toSet().toList().sorted().toString("\n"));
```
Gives result:
```
[alt, ctrl]
[alt, shift]
[alt, super]
[alt]
[ctrl, shift]
[ctrl, super]
[ctrl]
[shift, super]
[shift]
[super]
```

* each collection extends standard java collection, so you can use it whenever standard collection would else be used
* just added some functional not modifying methods
* yes, there are copies everywhere, but hey! Optimize it when your profiler say you so!
* very convenient
* resulting code is looking like Xtend's or Scala's but with pure java
* java8's .stream() I consider not usable

##Pattern-matching

Flexible way to make 'selects' in your data-structures. Abstract syntax tree analysis, complex pattern matching on structured data.

[wiki](https://github.com/kravchik/jcommon/wiki/pattern-matching)
or
[habrahabr (russian)](http://habrahabr.ru/post/270173/)

##Vector arithmetics

Full set of common functions for 3D mathematics (for each x2/x3/x4 vector and vector/scalar):
 
* max
* min
* reflect
* smoothstep
* ...
* and many more

[look at Vec3f](https://github.com/kravchik/jcommon/blob/master/src/main/java/yk/jcommon/fastgeom/Vec3f.java) 

even more at [senjin](https://github.com/kravchik/senjin/blob/master/src/main/java/yk/senjin/shaders/gshader/ShaderParent.java)


##ANIO
**yk.jcommon.net.anio**

java.nio wrapper with node.js style API (because java.nio's API is not for human beings). Transfer data with just a few lines of code.

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
* node.js style
* replaceable serialization engine
* no threads (threading control is on you) 


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
Npc {
  name='Grumble Fingur'
  type=Goblin
  model={type=AngryBastard colorScheme=red}
  items= items.Hat{name='Hat of sun'}, random{type=ring}
  }
}
```
Or just simple config file:
```
serverType = node
port = 8080
//port = 80
description = "Awesome
super server"
```

###syntax
* no commas or semicolons needed, so noise level is very low
* strings and keys without quotes (that is also reduces noise)
* but can use "" or '' (for strings with spaces, for example)
* ' ' for strings - so you can include YADS in java strings without escaping
* spaces and tabs don't have special meaning (opposite to yaml or python), so you can arrange data-text as you wish, even in one line (important for various input types: xls cells, input fields, etc)
* multiline strings (with both "" and '' quotes)
* numbers, booleans
* utf8, no restriction on keys or strings
* comments (one line // and multiline /**/)
* carefully controlled comma, to avoid one level parentheses like in {pos=10, 10 size=100, 200}

###serialization
* serialize any data to human readable string, and then back to the same data without any additional effort
* maps, lists, arrays, objects with class preservation
* if type is unknown - array, map, or special class is constructed

[more on wiki](https://github.com/kravchik/jcommon/wiki/YADS-instead-of-.properties-syntax-example)

##mvn artifact
```xml
<repository>
   <id>yk.jcommon</id>
   <url>https://github.com/kravchik/mvn-repo/raw/master</url>
</repository>

<dependency>
    <groupId>yk</groupId>
    <artifactId>jcommon</artifactId>
    <version>0.119</version>
</dependency>
```
(current dev version is 0.120-SNAPSHOT)

