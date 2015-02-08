package yk.jcommon.tree;

import yk.jcommon.fastgeom.IntBox;
import yk.jcommon.fastgeom.IntCube;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 22.01.2010
 * Time: 14:15:28
 */
abstract public class AABCubed extends BaseIdentifiable {
    public boolean stone = false;
    public transient IntCube intBox = new IntCube();
    public float z = 1;
    public int owningSize;//TODO remove property (calculate it)
    public boolean localWorked;

    public void tick(float dt) {}

    abstract public void refreshIntBox();
}
