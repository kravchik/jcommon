package yk.jcommon.match;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 4/20/13
 * Time: 6:48 PM
 */
public class JVar {
    public String name;
    public Object child;

    public JVar(String name) {
        this.name = name;
    }

    public JVar(String name, Object child) {
        this.name = name;
        this.child = child;
    }

    @Override
    public String toString() {
        return "!" + name + (child != null ? ":" + child : "");
    }



}
