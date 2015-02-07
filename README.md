jcommon
=======
Common utils, abstractions and tools that I use in my projects. They make Java pleasant.

##YCollections
Collections as they should be.
'''java
    String names = al(new File("/home/user/").listFiles())
            .filter(File::isDirectory)                  //only dirs
            .map(File::getName)                         //get name
            .filter(n -> n.startsWith("."))             //only invisible
            .sorted()                                   //sorted
            .foldLeft("", (r, n) -> r + ", " + n);      //to print fine
    System.out.println(names);
'''

##ANIO
A normal IO for Java

##YADS
yet another data syntax (the perfect one actually)


