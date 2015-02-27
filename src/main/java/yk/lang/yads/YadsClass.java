package yk.lang.yads;

import yk.jcommon.collections.YList;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/02/15
 * Time: 08:56
 */
public class YadsClass {
    public String name;
    public YList body;

    public YadsClass(String s, YList l) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YadsClass yadClass = (YadsClass) o;

        if (!body.equals(yadClass.body)) return false;
        if (name != null ? !name.equals(yadClass.name) : yadClass.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + body.hashCode();
        return result;
    }
}
