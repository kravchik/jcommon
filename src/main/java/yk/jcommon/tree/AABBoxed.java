package yk.jcommon.tree;

import yk.jcommon.fastgeom.IntBox;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 22.01.2010
 * Time: 14:15:28
 */
abstract public class AABBoxed extends BaseIdentifiable {//TODO rename Squared
    public boolean stone = false;//TODO fix! Not tree specific
    public transient IntBox intBox = new IntBox();
    public float z = 1;//TODO fix! Not tree specific
    public int owningSize;//TODO remove property (calculate it)
    public boolean localWorked;//TODO fix! Not tree specific

    public IntBox getIntBox() {
        return intBox;
    }

    public void tick(float dt) {}

    abstract public void refreshIntBox();
}
