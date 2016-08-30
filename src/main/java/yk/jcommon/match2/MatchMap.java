package yk.jcommon.match2;

import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;

import java.util.Map;

import static yk.jcommon.collections.YHashMap.toYMap;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/08/16
 * Time: 10:44
 */
//TODO match keys
public class MatchMap implements MatchCustom {
    public YMap pattern;
    public MatchVar other;

    public MatchMap(MatchVar other, YMap pattern) {
        this.other = other;
        this.pattern = pattern;
    }

    @Override
    public YSet<YMap<String, Object>> match(Matcher matcher, Object dObj, YMap<String, Object> cur) {
        if (!(dObj instanceof Map)) return hs();
        Map data = (Map) dObj;
        if (!data.keySet().containsAll(pattern.keySet())) return hs();

        YSet<YMap<String, Object>> last = hs(cur);
        for (Object pk : pattern.keySet()) {
            Object pv = pattern.get(pk);
            Object dv = data.get(pk);

            YSet<YMap<String, Object>> newResult = hs();
            for (YMap<String, Object> map : last) newResult.addAll(matcher.match(dv, pv, map));
            last = newResult;
        }
        YSet<YMap<String, Object>> result = hs();
        if (other != null) {
            YMap otherData = toYMap(data).without(pattern.keySet());
            for (YMap<String, Object> map : last) {
                result.addAll(matcher.match(otherData, other, map));
            }
        }
        return result;
    }
}
