package yk.jcommon.match;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static yk.jcommon.utils.Util.car;
import static yk.jcommon.utils.Util.set;
import static yk.jcommon.utils.Util.sub;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/25/14
 * Time: 1:37 PM
 */
public class JMSet implements JM.Specific {
    public Set pattern;

    @Override
    public Set<Map> match(Object od, Map current) {
        if (!(od instanceof Set)) return Collections.emptySet();
        if (pattern.isEmpty()) return set(current);
        Set<Map> result = set();
        Set data = (Set) od;
        Object p = car(pattern);
        for (Object d : data) for (Map mm : JM.match(d, p, current)) result.addAll(JM.matchSet(sub(data, d), sub(pattern, p), mm));
        return result;
    }
}
