package yk.jcommon.tree;

import yk.jcommon.fastgeom.IntCube;
import yk.jcommon.utils.Poolable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 22.01.2010
 * Time: 14:14:00
 */
public class CubeNode<T> extends Poolable<CubeNode> {//factually it is a Map<IntBox, T>

    public CubeNode parent;
    public IntCube cube = new IntCube();
    public CubeNode[] children;
    public List<T> included = new ArrayList<T>();

    public CubeNode[] neigh = new CubeNode[6];


    public CubeNode() {
    }

    public void init(CubeNode parent, int l, int b, int n, int r, int t, int f) {
        this.parent = parent;
        cube.init(l, b, n, r, t, f);
    }

    private CubeNode createChild(int x, int y, int z) {
        int newL = cube.l + cube.size / 2 * x;
        int newB = cube.b + cube.size / 2 * y;
        int newN = cube.n + cube.size / 2 * z;
        //Node result = new Node();
        CubeNode result = pool.borrow();
        //result.init(this, newL, newB, newN, newL + cube.size / 2, newB + cube.size / 2, newN + cube.size / 2);
        result.init(this, newL, newB, newN, newL + cube.size / 2-1, newB + cube.size / 2-1, newN + cube.size / 2-1);
        //System.out.println(result.cube.toString());
        return result;
    }

    private static IntCube getChild(IntCube cube, int x, int y, int z) {
        int newL = cube.l + cube.size / 2 * x;
        int newB = cube.b + cube.size / 2 * y;
        int newN = cube.n + cube.size / 2 * z;

        return new IntCube().init(newL, newB, newN, newL+cube.size / 2 - 1, newB + cube.size / 2 -1, newN + cube.size / 2 - 1);
    }

    public static void main(String[] args) {
        //TODO test
        System.out.println(getChild(new IntCube().init(0, 0, 0, 7, 7, 7), 0, 0, 0));
        System.out.println(getChild(new IntCube().init(0, 0, 0, 7, 7, 7), 1, 0, 0));
        System.out.println(getChild(new IntCube().init(0, 0, 0, 7, 7, 7), 1, 1, 1));
        System.out.println(getChild(new IntCube().init(4, 4, 4, 7, 7, 7), 1, 1, 1));
        System.out.println(getChild(new IntCube().init(4, 4, 4, 7, 7, 7), 0, 0, 0));
        System.out.println(getChild(new IntCube().init(6, 6, 6, 7, 7, 7), 1, 1, 1));
        System.out.println(getChild(new IntCube().init(6, 6, 6, 7, 7, 7), 0, 0, 0));
        System.out.println();
        System.out.println(getChild(new IntCube().init(-3, -3, -3, 4, 4, 4), 0, 0, 0));
        System.out.println(getChild(new IntCube().init(-3, -3, -3, 0, 0, 0), 1, 0, 0));
        System.out.println(getChild(new IntCube().init(-1, -3, -3, 0, -2, -2), 1, 0, 0));
    }

    public static int[][][] PLACES = new int[][][] {
            {{0, 4}, {2, 6}},
            {{1, 5}, {3, 7}},
    };

    public CubeNode c(int [] pos) {
        if (children == null) return null;
        return children[PLACES[pos[0]][pos[1]][pos[2]]];
    }

    protected void createChildren() {
        if (children == null) {
            children = new CubeNode[8];
            children[0] = createChild(0, 0, 0);
            children[1] = createChild(1, 0, 0);
            children[2] = createChild(0, 1, 0);
            children[3] = createChild(1, 1, 0);

            children[4] = createChild(0, 0, 1);
            children[5] = createChild(1, 0, 1);
            children[6] = createChild(0, 1, 1);
            children[7] = createChild(1, 1, 1);
        }
    }

    protected void removeChildren() {
        if (children != null) for(int i = 0; i < children.length; i++) {
            children[i].removeChildren();
            pool.returnItem(children[i]);
        }
        children = null;
    }

    public void insert(IntCube ibox, T bboxed) {
        if (cube.isCrossed(ibox)) {
            if (ibox.size * 2 < cube.size && cube.size > 5 ) {//too small
                createChildren();
                for(int i = 0; i < children.length; i++) {
                    children[i].insert(ibox, bboxed);
                }
            } else {
                included.add(bboxed);
                //bboxed.owningSize = box.size;
            }
        }
    }

    private boolean isBranchEmpty() {
        if (children != null) for(int i = 0; i < children.length; i++) {
            if (children[i].included.size() > 0) return false;
            if(!children[i].isBranchEmpty())return false;
        }
        return true;
    }

    public boolean remove(IntCube ibox, T bboxed) {
        if(!cube.isCrossed(ibox))return false;
        boolean result = false;
        int i;
        //remove item from array
        //todo if size
        //try dictionary with performance tests
        //for(i = 0; i < included.size(); i++){
        //    if(included.get(i) == bboxed){
        //        result = true;
        //        int last = included.size() - 1;
        //        if(i < last){
        //            included.set(i, included.get(last));
        //        }
        //        included.remove(included.size() - 1);
        //        i--;//TODO fix
        //    }
        //}
        included.remove(bboxed);
        //remove item from children
        if (children != null)for(i = 0; i < children.length; i++) {
            result = children[i].remove(ibox, bboxed) || result;
        }
        if (isBranchEmpty()) removeChildren();
        return result;
    }

    public void collectIntersection(IntCube ibox, List<? super T> result) {
        if(!cube.isCrossed(ibox))return;
        int i, m;
        //Physics.COUNTER_INTR_D.start();
        for(i = 0, m = included.size(); i < m; i++){
            T o = included.get(i);
            if (!result.contains(o)) result.add(o);
        }
        //Physics.COUNTER_INTR_D.stop();
        //Physics.COUNTER_INTR_E.start();
        if (children != null)for(i = 0; i < children.length; i++) {
            children[i].collectIntersection(ibox, result);
        }
        //Physics.COUNTER_INTR_E.stop();
    }


}
