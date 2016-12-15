package yk.jcommon.match2.generator;

import yk.jcommon.collections.YMap;

/**
 * Created by Yuri Kravchik on 19/11/16.
 */
public interface GenMatchCustom {
    YMap<String, Object> next(GenMatcher matcher, Object data, YMap<String, Object> current);
}
