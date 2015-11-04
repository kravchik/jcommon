package yk.jcommon.match2;

import yk.jcommon.collections.YMap;

import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 17:06
 */
public class Property {
    public YMap<String, Object> pp;

    public Property(String name, Object... oo) {
        pp = hm();
        pp.put(name, oo.length > 0 ? oo[0] : null);
        for (int i = 1; i < oo.length; i += 2) {
            pp.put((String) oo[i], i + 1 == oo.length ? null : oo[i + 1]);
        }
    }
}
