package yk.jcommon.match2.generator;

import yk.jcommon.collections.YMap;

import java.util.List;

/**
 * Created by Yuri Kravchik on 19/11/16.
 */
public class GenMatchListElement implements GenMatchCustom {
    public Object pattern;
    public Object indexPattern;
    private int currentIndex = 0;
    //TODO bind index


    public GenMatchListElement(Object pattern) {
        this.pattern = pattern;
    }

    @Override
    public YMap<String, Object> next(GenMatcher matcher, Object data, YMap<String, Object> current) {
        if (!(data instanceof List)) return null;
        List dl = (List) data;
        while (currentIndex < dl.size()) {
            YMap<String, Object> result = matcher.next(dl.get(currentIndex), pattern, current);
            if (result != null) {
                if (indexPattern != null) result = matcher.next(currentIndex, indexPattern, result);
                if (result != null) return result;
            }
            currentIndex++;
        }
        return null;
    }
}
