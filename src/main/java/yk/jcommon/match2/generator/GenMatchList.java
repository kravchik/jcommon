package yk.jcommon.match2.generator;

import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;

import java.util.List;

/**
 * Created by Yuri Kravchik on 19/11/16.
 */
public class GenMatchList implements GenMatchCustom {
    public YList pattern;

    @Override
    public YMap<String, Object> next(GenMatcher matcher, Object data, YMap<String, Object> current) {
        if (!(data instanceof List)) return null;
        List dl = (List) data;


        return null;
    }
}
