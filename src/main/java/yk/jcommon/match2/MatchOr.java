package yk.jcommon.match2;

import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/09/16
 * Time: 09:48
 */
public class MatchOr implements MatchCustom {
    public YList<Object> variants;

    public MatchOr(Object... variants) {
        this.variants = al(variants);
    }

    @Override
    public YSet<YMap<String, Object>> match(Matcher matcher, Object data, Object pattern, YMap<String, Object> cur) {
        for (Object variant : variants) {
            YSet<YMap<String, Object>> result = matcher.match(data, variant, cur);
            if (result.notEmpty()) return result;
        }
        return hs();
    }
}
