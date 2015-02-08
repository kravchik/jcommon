package yk.lang.yads;

import yk.jcommon.collections.YList;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/02/15
 * Time: 08:56
 */
public class YADClass {
    public String name;
    public YList body;

    public YADClass(String s, YList l) {
        name = s;
        body = l;
    }

    @Override
    public String toString() {
        return "YADClass{" +
                "name='" + name + '\'' +
                ", body=" + body +
                '}';
    }
}
