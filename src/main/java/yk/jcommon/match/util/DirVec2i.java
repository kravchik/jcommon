package yk.jcommon.match.util;

import yk.jcommon.fastgeom.Vec2i;
import yk.jcommon.match.Match;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.utils.Util.copy;
import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 04/05/16
 * Time: 10:13
 */
public class DirVec2i implements Match.Specific {

    public Object dir, a, b;

    public DirVec2i(Object dir, Object a, Object b) {
        this.dir = dir;
        this.a = a;
        this.b = b;
    }

    @Override
    public List<Map> match(Map cur) {
        Object rop = dir;
        Object ra = a;
        Object rb = b;
        Object rDir = resolve(cur, rop);
        if (rDir != null && !(rDir instanceof Dir)) return emptyList();
        Dir dir = (Dir) rDir;
        Vec2i a = (Vec2i) resolve(cur, ra);
        Vec2i b = (Vec2i) resolve(cur, rb);

        if (dir == null) {
            if (a != null && b != null) {
                Vec2i dif = b.sub(a);
                for (int i = 0; i < Dir.values().length; i++) {
                    if (dif.x == Dir.values()[i].dx && dif.y == Dir.values()[i].dy) return list(copy(cur, rop, Dir.values()[i]));
                }
                throw new Error("unknown dir: " + dif);
            } else if (a != null) {
                List<Map> result = al();
                for (int i = 0; i < Dir.values().length; i++) {
                    Dir d = Dir.values()[i];
                    result.add(copy(cur, rop, d, rb, a.add(d.dx, d.dy)));
                }
                return result;
            } else if (b != null) {
                List<Map> result = al();
                for (int i = 0; i < Dir.values().length; i++) {
                    Dir d = Dir.values()[i];
                    result.add(copy(cur, rop, d, ra, b.sub(d.dx, d.dy)));
                }
                return result;
            }
        } else {
            if (a != null && b != null) return a.add(dir.dx, dir.dy).equals(b) ? list(cur) : al();
            else if (a != null) return list(copy(cur, rb, a.add(dir.dx, dir.dy)));
            else if (b != null) return list(copy(cur, ra, b.add(-dir.dx, -dir.dy)));
        }
        throw new Error("" + this);
    }

    @Override
    public String toString() {
        return "DirVec2i{" +
               "dir=" + dir +
               ", a=" + a +
               ", b=" + b +
               '}';
    }

    @Override
    public Object resolve(Object p, Map current) {
        return null;
    }

    private static Object resolve(Map map, Object value) {
        if (Match.isVar(value)) return map.get(value);
        return value;
    }
}
