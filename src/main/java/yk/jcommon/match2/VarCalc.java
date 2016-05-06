package yk.jcommon.match2;

import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 04/05/16
 * Time: 09:45
 */
public interface VarCalc {
    YSet<YMap<String, Object>> calc(YMap<String, Object> cur);
}
