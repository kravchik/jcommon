package yk.jcommon.match2;

import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;
import yk.jcommon.utils.BadException;

import java.lang.reflect.Array;
import java.util.List;

import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 16:43
 */
public class MatchByIndex implements MatchCustom {
    public Object index;
    public Object rest;

    public MatchByIndex(Object rest) {
        index = new MatchAny();
        this.rest = rest;
    }

    public MatchByIndex() {
        index = new MatchAny();
    }

    //TODO assert index either Int or Var
    public MatchByIndex(Object index, Object rest) {
        this.index = index;
        this.rest = rest;
    }

    @Override
    public YSet<YMap<String, Object>> match(Matcher matcher, Object data, YMap<String, Object> cur) {
        if (data instanceof List) {
            List l = (List) data;
            Object index = matcher.resolve(this.index, cur);

            if (this.index instanceof Number) return matcher.match(l.get(((Number) index).intValue()), this.rest, cur);
            YSet<YMap<String, Object>> result = hs();
            for (int i = 0; i < l.size(); i++) {
                for (YMap<String, Object> m : matcher.match(l.get(i), this.rest, cur)) {
                    if (this.index == null) result.add(m);
                    else result.addAll(matcher.match(i, index, m));
                }
            }
            return result;
        }
        if (data.getClass().isArray()) {
            Object index = matcher.resolve(this.index, cur);
            if (index instanceof Number) return matcher.match(Array.get(data, ((Number) index).intValue()), this.rest, cur);
            YSet<YMap<String, Object>> result = hs();
            if (!(index instanceof MatchVar) && !(index instanceof MatchAny)) BadException.shouldNeverReachHere("" + index);

            for (int i = 0; i < Array.getLength(data); i++) {
                for (YMap<String, Object> m : matcher.match(Array.get(data, i), this.rest, index instanceof MatchVar ? cur.with(((MatchVar) index).name, i) : cur)) {
//                    if (pattern.index == null) result.add(m);
//                    else result.addAll(match(i, index, m));
                    result.add(m);
                }
            }
            return result;
        }
        return hs();
    }
}
