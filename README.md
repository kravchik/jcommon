# MIGRATED to [ycollections](https://github.com/kravchik/ycollections)

# jcommon

_Let's say we want to filter a Map, and we have two options..._

Option 1:
```
        sMap = sMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith("a"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
```

Option 2:
```
        yMap = yMap.filter((k, v) -> k.startsWith("a"));
```

If you'd prefer Option 2, you are welcome to continue reading.


streams are ok if you have many steps, but bad for simple steps (and simple steps are more often)
instantiation (vs standard, vs Collections, vs Guava) 
everything extends usual collections (so accepted everywhere)
everything is modifiable except of added methods
examples of creating, filtering, mapping, sorting
maps and sets are sorted (pros)
ycollectiona are copying every time but...


Common utils, abstractions, and tools that I use in my projects. They make Java pleasant.

_There was more code here but it has been moved to [yincubator](https://github.com/kravchik/yincubator)_

_Now, without apache commons dependency!_

**How to MAKE JAVA GREAT AGAIN?**
1. don't use streams or guava
2. use YCollections
3. if (IF) your profiler says you to - then refactor an algorithm with YCollections into ugly but efficient empiric code (fors, side-effects, etc)

They are just extensions of the standard collections - you can use YList everywhere where you use List. Same goes for YArrayList, YSet, YMap, etc.

YCollections will suffice in most cases:
1. 99.9% of an enterprise project
1. 80% of a game-dev project
1. 100% of scripts
1. 100% of tests

It is better to stick with simple and elegant YCollections and optimize small percentage of places, than to suffer (even if you don't know it yet) with streams, and guava (I even not talking about standard collections initialization).

_Why streams are bad?_
1. `stream()` and `collect` parts. In YCollections you don't need to collect. Though you too have to convert usual collection or array into YCollection (`toYList(someList)`, `al(someArray)`). _You can't imagine how nicer your scripts will look!_
2. verbosity
3. you can't ask them to add something (me, you can ask)
4. premature optimization, complex insides, etc.

_Why Guava is bad?_
1. `ImmutableList.of` - you can't static import that. In YCollections you just use `al()` or `hm()` (static import from `YArrayList.al()` and `YHashMap.hm()`) Which leads to elegant and concise code. _You can't imagine how nicer your tests will look!_


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
            .toString(", ");                            //to print fine
    System.out.println(names);
```
Example 2
```java
    YList<String> all = al("shift", "ctrl", "alt", "super");
    System.out.println(all.eachToEach(all)     //take pares of each to each
            .map(p -> p                        //rework each pare
                    .toSet()                   //  convert to set to remove "alt alt" and similar
                    .sorted()                  //  sort (yes, it is a LinkedHashSet inside)
                    .toString(", ")            //  make a string
            )
            .toSet()                           //convert to set to remove duplicates ("alt shift", "shift alt")
            .sorted()                          //sort
            .toString("\n"));                  //make a result string
```
Gives result:
```
alt
alt, ctrl
alt, shift
alt, super
ctrl
ctrl, shift
ctrl, super
shift
shift, super
super
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
   <id>yk</id>
   <url>https://github.com/kravchik/mvn-repo/raw/master</url>
</repository>

<dependency>
    <groupId>yk</groupId>
    <artifactId>jcommon</artifactId>
    <version>0.122</version>
</dependency>
```
(current dev version is 0.123-SNAPSHOT)

