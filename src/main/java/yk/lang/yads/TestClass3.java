package yk.lang.yads;

import yk.jcommon.fastgeom.Vec2f;

public class TestClass3 {
    public Vec2f pos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestClass3 that = (TestClass3) o;

        if (pos != null ? !pos.equals(that.pos) : that.pos != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pos != null ? pos.hashCode() : 0;
    }
}
