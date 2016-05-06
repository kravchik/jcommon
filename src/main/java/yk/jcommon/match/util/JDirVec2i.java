package yk.jcommon.match.util;

import yk.jcommon.collections.YSet;
import yk.jcommon.fastgeom.Vec2i;
import yk.jcommon.match.JM;
import yk.jcommon.match.JVar;

import java.util.Map;
import java.util.Set;

import static yk.jcommon.collections.YHashSet.hs;
import static yk.jcommon.utils.Util.copy;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 04/05/16
 * Time: 10:13
 */
public class JDirVec2i implements JM.Specific2 {
    public Object dir, a, b;

    public JDirVec2i(Object dir, Object a, Object b) {
        this.dir = dir;
        this.a = a;
        this.b = b;
    }

    @Override
    public Set<Map> match(Map cur) {
        Object rop = dir;
        Object ra = a;
        Object rb = b;
        Object rDir = resolve(cur, rop);
        if (rDir != null && !(rDir instanceof Dir)) return hs();
        Dir dir = (Dir) rDir;
        Vec2i a = (Vec2i) resolve(cur, ra);
        Vec2i b = (Vec2i) resolve(cur, rb);

        if (dir == null) {
            if (a != null && b != null) {
                Vec2i dif = b.sub(a);
                for (int i = 0; i < Dir.values().length; i++) {
                    if (dif.x == Dir.values()[i].dx && dif.y == Dir.values()[i].dy) return hs(copy(cur, ((JVar)rop).name, Dir.values()[i]));
                }
                throw new Error("unknown dir: " + dif);
            } else if (a != null) {
                YSet<Map> result = hs();
                for (int i = 0; i < Dir.values().length; i++) {
                    Dir d = Dir.values()[i];
                    result.add(copy(cur, ((JVar)rop).name, d, rb, a.add(d.dx, d.dy)));
                }
                return result;
            } else if (b != null) {
                YSet<Map> result = hs();
                for (int i = 0; i < Dir.values().length; i++) {
                    Dir d = Dir.values()[i];
                    result.add(copy(cur, ((JVar)rop).name, d, ra, b.sub(d.dx, d.dy)));
                }
                return result;
            }
        } else {
            if (a != null && b != null) return a.add(dir.dx, dir.dy).equals(b) ? hs(cur) : hs();
            else if (a != null) return hs(copy(cur, ((JVar)rb).name, a.add(dir.dx, dir.dy)));
            else if (b != null) return hs(copy(cur, ((JVar)ra).name, b.add(-dir.dx, -dir.dy)));
        }
        throw new Error("" + this);
    }

    @Override
    public String toString() {
        return "JDirVec2i{" +
               "dir=" + dir +
               ", a=" + a +
               ", b=" + b +
               '}';
    }

    private static Object resolve(Map map, Object value) {
        if (JM.isVar(value)) return map.get(((JVar)value).name);
        return value;
    }
}
