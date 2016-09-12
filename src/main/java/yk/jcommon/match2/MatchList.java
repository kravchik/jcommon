package yk.jcommon.match2;

import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;

import java.util.List;

import static yk.jcommon.collections.YArrayList.toYList;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/08/16
 * Time: 11:14
 */
public class MatchList implements MatchCustom {
    public YList pattern;

    public MatchList(YList pattern) {
        this.pattern = pattern;
    }

    @Override
    public YSet<YMap<String, Object>> match(Matcher matcher, Object data, YMap<String, Object> cur) {
        if (!(data instanceof List)) return hs();
        return matchRest(matcher, data instanceof YList ? (YList) data : toYList((List) data), pattern, cur);
    }

    public static YSet<YMap<String, Object>> matchRest(Matcher matcher, YList data, YList pattern, YMap<String, Object> cur) {
        if (pattern.isEmpty() && data.isEmpty()) return hs(cur);
        if (pattern.isEmpty()) return hs();
        Object p = pattern.car();

        if (p instanceof Filler) {
            Filler f = (Filler) p;
            YSet<YMap<String, Object>> result = hs();
            int max = data.size();
            if (f.maxLength != null && f.maxLength < max) max = f.maxLength;
            int min = f.minLength;

            //is this optimization necessary?
            if (pattern.size() == 1) {//if filler is last, it must fill all the rest
                if (data.size() < min) return hs();//already not enough data
                min = data.size();
            }

            for (int i = min; i < max + 1; i++) {
                YList forFiller = data.subList(0, i);
                YSet<YMap<String, Object>> fillerMappings = f.x == null ? hs(cur) : matcher.match(forFiller, f.x, cur);
                for (YMap<String, Object> map : fillerMappings) result.addAll(matchRest(matcher, data.subList(i, data.size()), pattern.cdr(), map));
            }
            return result;
        } else {
            if (data.isEmpty()) return hs();
            YSet<YMap<String, Object>> result = hs();
            YList dCdr = data.cdr();
            YList pCdr = pattern.cdr();
            for (YMap<String, Object> map : matcher.match(data.car(), p, cur)) {
                result.addAll(matchRest(matcher, dCdr, pCdr, map));
            }
            return result;
        }
    }

    public static class Filler {
        public MatchVar x;
        public int minLength = 0;
        public Integer maxLength;

        public Filler setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Filler setMinLength(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public Filler setVar(MatchVar v) {
            this.x = v;
            return this;
        }
    }
}
