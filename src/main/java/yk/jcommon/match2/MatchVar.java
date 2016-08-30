package yk.jcommon.match2;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 16:47
 */
public class MatchVar {
    public String name;
    public Object rest;

    public MatchVar(String name) {
        this.name = name;
    }

    public MatchVar(String name, Object rest) {
        this.name = name;
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Var{" +
               "name='" + name + '\'' +
               ", rest=" + rest +
               '}';
    }
}
