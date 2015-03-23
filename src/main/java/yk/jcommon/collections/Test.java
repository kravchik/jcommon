package yk.jcommon.collections;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 9:47 PM
 */
public class Test {

    public static void main(String[] args) {
        String names = YArrayList.al(new File("/home/yuri/").listFiles())
                .filter(File::isDirectory)                  //only dirs
                .map(File::getName)                         //get name
                .filter(n -> n.startsWith("."))             //only invisible
                .sorted()                                   //sorted
                .fold("", (r, n) -> r + ", " + n);      //to print fine
        System.out.println(names);

        //File("/home/yuri")listFiles()f(_.isDirectory)m(_.getName)f(n.startsWith(".")sfl("" _1+", "+_2)
    }

}
