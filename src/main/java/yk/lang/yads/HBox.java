package yk.lang.yads;

import yk.jcommon.collections.YList;
import yk.jcommon.fastgeom.Vec2i;

/**
* Created with IntelliJ IDEA.
* User: yuri
* Date: 05/02/15
* Time: 09:44
*/
class HBox {
    public Vec2i pos;
    public Vec2i size;
    public YList elements;

    public void init(YList elements) {

    }

    @Override
    public String toString() {
        return "HBox{" +
                "pos=" + pos +
                ", size=" + size +
                ", elements=" + elements +
                '}';
    }
}
