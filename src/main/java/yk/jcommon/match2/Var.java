package yk.jcommon.match2;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 16:47
 */
public class Var {
    public String name;
    public Object rest;

    public Var(String name) {
        this.name = name;
    }

    public Var(String name, Object rest) {
        this.name = name;
        this.rest = rest;
    }
}
