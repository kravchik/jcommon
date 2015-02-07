package yk.jcommon.tree;

import yk.jcommon.fastgeom.IntBox;
import yk.jcommon.fastgeom.Vec2f;
import yk.jcommon.utils.Poolable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 22.01.2010
 * Time: 14:14:00
 */
public class Node<T extends AABBoxed> extends Poolable<Node> {//factually it is a Map<IntBox, T>

    public Node parent;
    public IntBox box = new IntBox();
    public Node[] children = new Node[4];
    public List<T> included = new ArrayList<T>();

    public Node() {
    }

    public void init(Node parent, int l, int b, int r, int t) {
        this.parent = parent;
        box.init(l, b, r, t);
    }

    private Node createChild(int x, int y) {
        int newL = box.l + box.w / 2 * x;
        int newB = box.b + box.h / 2 * y;
        //Node result = new Node();
        Node result = pool.borrow();
        result.init(this, newL, newB, newL + box.w / 2, newB + box.h / 2);
        return result;
    }

    private void createChildren() {
        if (children[0] == null) {
            children[0] = createChild(0, 0);
            children[1] = createChild(1, 0);
            children[2] = createChild(0, 1);
            children[3] = createChild(1, 1);
        }
    }

    private void removeChildren() {
        if (children[0] != null) for(int i = 0; i < children.length; i++) {
            children[i].removeChildren();
            pool.returnItem(children[i]);
            children[i] = null;
        }
    }

    public void insert(IntBox ibox, T bboxed) {
        if (box.isCrossed(ibox)) {
            if (ibox.w * 2 < box.w && box.w > 5) {//too small
                createChildren();
                for(int i = 0; i < children.length; i++) {
                    children[i].insert(ibox, bboxed);
                }
            } else {
                included.add(bboxed);
                bboxed.owningSize = box.w;
            }
        }
    }

    private boolean isBranchEmpty() {
        if (children[0] != null) for(int i = 0; i < children.length; i++) {
            if (children[i].included.size() > 0) return false;
            if(!children[i].isBranchEmpty())return false;
        }
        return true;
    }

    public boolean remove(IntBox ibox, T bboxed) {
        if(!box.isCrossed(ibox))return false;
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
        if (children[0] != null)for(i = 0; i < children.length; i++) {
            result = children[i].remove(ibox, bboxed) || result;
        }
        if (isBranchEmpty()) removeChildren();
        return result;
    }

    public void collectIntersection(IntBox ibox, List<? super T> result) {
        if(!box.isCrossed(ibox))return;
        int i, m;
        //Physics.COUNTER_INTR_D.start();
        for(i = 0, m = included.size(); i < m; i++){
            T o = included.get(i);
            o.localWorked = false;
            if (!result.contains(o)) result.add(o);
        }
        //Physics.COUNTER_INTR_D.stop();
        //Physics.COUNTER_INTR_E.start();
        if (children[0] != null)for(i = 0; i < children.length; i++) {
            children[i].collectIntersection(ibox, result);
        }
        //Physics.COUNTER_INTR_E.stop();
    }

    public void collectIntersection(Vec2f from, Vec2f to, Set<? super T> result) {
        if (intersects1(from, to) && intersects2(from, to)) {
            result.addAll(included);
            if (children[0] != null)for(int i = 0; i < children.length; i++) {
                children[i].collectIntersection(from, to, result);
            }
        }
    }

    public boolean intersects2(Vec2f from, Vec2f to) {
        if (from.x > box.r && to.x > box.r) return false;
        if (from.x < box.l && to.x < box.l) return false;
        if (from.y > box.t && to.y > box.t) return false;
        if (from.y < box.b && to.y < box.b) return false;
        return true;
    }

    public boolean intersects1(Vec2f from, Vec2f to) {
        boolean b = F(from.x, from.y, to.x, to.y, box.l, box.b);
        if (b != F(from.x, from.y, to.x, to.y, box.l, box.t)) return true;
        if (b != F(from.x, from.y, to.x, to.y, box.r, box.t)) return true;
        if (b != F(from.x, from.y, to.x, to.y, box.r, box.b)) return true;
        return false;
    }

    public static boolean F(float x1, float y1, float x2, float y2, float x, float y) {
        return (y2-y1) * x + (x1-x2) * y + (x2*y1-x1*y2) > 0;
    }

}
