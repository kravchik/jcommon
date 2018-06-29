jcommon
=======
Common utils, abstractions, and tools that I use in my projects. They make Java pleasant.

_There was more code here but it has been moved to [yincubator]()_

## YCollections
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
* yes, there are copies everywhere, but hey! Optimize it when your profiler says you so!
* very convenient
* resulting code is looking like Xtend's or Scala's but with pure java
* java8's .stream() I consider not usable

## mvn artifact
```xml
<repository>
   <id>yk.jcommon</id>
   <url>https://github.com/kravchik/mvn-repo/raw/master</url>
</repository>

<dependency>
    <groupId>yk</groupId>
    <artifactId>jcommon</artifactId>
    <version>0.121</version>
</dependency>
```
(current dev version is 0.122-SNAPSHOT)

