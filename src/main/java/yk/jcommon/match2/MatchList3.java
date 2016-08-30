package yk.jcommon.match2;

import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;
import yk.jcommon.utils.BadException;

import java.lang.reflect.Array;
import java.util.List;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 16:43
 */
public class MatchList3 implements MatchCustom {
    public Object before;
    public Object x;
    public Object after;
    public Object index;

    public MatchList3(Object before, Object x, Object after) {
        this.before = before;
        this.x = x;
        this.after = after;
    }

    public MatchList3(Object before, Object x, Object after, Object index) {
        this.before = before;
        this.x = x;
        this.after = after;
        this.index = index;
    }

    @Override
    public YSet<YMap<String, Object>> match(Matcher matcher, Object data, YMap<String, Object> cur) {
        if (data instanceof List) {
            List l = (List) data;
            Object index = matcher.resolve(this.index, cur);

            if (this.index instanceof Number) {
                return forI(matcher, cur, l, ((Number) index).intValue());
            }
            YSet<YMap<String, Object>> result = hs();
            for (int i = 0; i < l.size(); i++) result.addAll(forI(matcher, cur, l, i));
            return result;
        }
        if (data.getClass().isArray()) {
            Object index = matcher.resolve(this.index, cur);
            if (index instanceof Number) return matcher.match(Array.get(data, ((Number) index).intValue()), this.x, cur);
            YSet<YMap<String, Object>> result = hs();
            if (!(index instanceof MatchVar) && !(index instanceof MatchAny)) BadException.shouldNeverReachHere("" + index);

            for (int i = 0; i < Array.getLength(data); i++) {
                for (YMap<String, Object> m : matcher.match(Array.get(data, i), this.x, index instanceof MatchVar ? cur.with(((MatchVar) index).name, i) : cur)) {
//                    if (pattern.index == null) result.add(m);
//                    else result.addAll(match(i, index, m));
                    result.add(m);
                }
            }
            return result;
        }
        return hs();
    }

    private YSet<YMap<String, Object>> forI(Matcher matcher, YMap<String, Object> cur, List l, int i) {
        if (i < 0 || i >= l.size()) return hs();
        YSet<YMap<String, Object>> res = matcher.match(i <= 0 ? al() : l.subList(0, i), before, cur);

        YSet<YMap<String, Object>> res2 = hs();
        for (YMap<String, Object> m : res) res2.addAll(matcher.match(l.get(i), this.x, m));

        YSet<YMap<String, Object>> res3 = hs();
        for (YMap<String, Object> m : res2) res3.addAll(matcher.match(i >= l.size() - 1 ? al() : l.subList(i + 1, l.size()), after, m));

        YSet<YMap<String, Object>> result = hs();

        for (YMap<String, Object> m : res3) {
            if (index == null) result.add(m);
            //TODO don't match if we know index already?
            else result.addAll(matcher.match(i, index, m));
        }

        return result;
    }
}
