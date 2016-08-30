package yk.jcommon.match2;

import yk.jcommon.collections.YMap;

import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 17:06
 */
public class MatchProperty {
    public YMap<String, PropertyDesc> pp;

    public static MatchProperty p1(String name, Boolean isMethod, Object... oo) {
        MatchProperty result= new MatchProperty();
        result.pp = hm();
        result.pp.put(name, oo.length > 0 ? new PropertyDesc(name, isMethod, oo[0]) : null);
        for (int i = 1; i < oo.length; i += 3) {
            String k = (String) oo[i];
            Boolean isM = (Boolean) oo[i + 1];
            Object v = i+2 == oo.length ? null : oo[i+2];
            result.pp.put(k, new PropertyDesc(k, isM, v));
        }
        return result;
    }

    public static MatchProperty p(String name, Object... oo) {
        MatchProperty result= new MatchProperty();
        result.pp = hm();
        result.pp.put(name, new PropertyDesc(name, null, oo.length > 0 ? oo[0] : null));
        for (int i = 1; i < oo.length; i += 2) {
            String k = (String) oo[i];
            Boolean isM = null;
            Object v = i+1 == oo.length ? null : oo[i+1];
            result.pp.put(k, new PropertyDesc(k, isM, v));
        }
        return result;
    }

    public static class PropertyDesc {
        public String name;
        public Boolean isMethod;
        public Object value;

        public PropertyDesc(String name, Boolean isMethod, Object value) {
            this.name = name;
            this.isMethod = isMethod;
            this.value = value;
        }
    }
}
