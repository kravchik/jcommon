package yk.jcommon.match2;

import yk.jcommon.collections.YList;

import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 17:43
 */
public class MatchAnd {
    public YList elements;

    public MatchAnd(Object... elements) {
        this.elements = al(elements);
    }
}
