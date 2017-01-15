package yk.jcommon.match2;

import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 19/08/16
 * Time: 11:02
 */
public interface MatchCustom {
    YSet<YMap<String, Object>> match(Matcher matcher, Object data, Object pattern, YMap<String, Object> cur);
}
