package yk.jcommon.match2;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 16:43
 */
public class ByIndex {
    public Object index;
    public Object rest;

    public ByIndex(Object rest) {
        index = new Any();
        this.rest = rest;
    }

    public ByIndex() {
        index = new Any();
    }

    //TODO assert index either Int or Var
    public ByIndex(Object index, Object rest) {
        this.index = index;
        this.rest = rest;
    }
}
