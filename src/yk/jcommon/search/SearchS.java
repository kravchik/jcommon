package yk.jcommon.search;
import java.util.*;

/**
 * Kravchik Yuri
 * Date: 09.01.12
 * Time: 10:34 PM
 */
//TO DO max edge size. List of actions?
abstract public class SearchS<STATE> implements Comparator<SearchS.Node<STATE>> {
    public boolean breadthFirst = true;
    private Map<STATE, Node<STATE>> seen = new HashMap<STATE, Node<STATE>>();
    public List<Node<STATE>> common = new ArrayList<Node<STATE>>();
    protected List<Node<STATE>> edge = new ArrayList<Node<STATE>>();
    public int iterations;

    abstract public boolean isEnd(STATE state);
    abstract public List<STATE> next(Node<STATE> node);

    /**
     * Override this to add an heuristic (A* would use distance to the target state as heuristics)
     */
    @Override
    public int compare(Node<STATE> a, Node<STATE> b) {
        return breadthFirst ? a.w - b.w : b.w - a.w;
    }

    public SearchS(STATE begin) {
        iterations = 0;
        edge.add(new Node<STATE>(null, begin));
    }

    public List<Node<STATE>> popNext(int limit) {
        next(limit);
        return popResult();
    }

    private int curLimit;
    public void next(int limit) {
        curLimit += limit;
        while(!edge.isEmpty()) {
            Node<STATE> current;
            iterations++;
            Collections.sort(edge, this);
            current = edge.get(0);
            if (isEnd(current.state)){
                //System.out.println("end");
                break;
            }
            if (current.w >= curLimit) {
                //System.out.println("out of limit");
                break;
            }
            edge.remove(0);
            for (STATE n : next(current)) {
                //Node<STATE> node = null;
                Node<STATE> node = seen.get(n);
                if (node == null || node.w > current.w + 1) {
                    Node<STATE> newNode = new Node<STATE>(current, n);
                    seen.put(n, newNode);
                    common.add(newNode);
                    edge.add(newNode);
                    //System.out.println(edge.size());
                //} else {
                    //fail(node);
                    //System.out.println("dropped "  + node.state);
                }

            }
        }
    }
    //abstract public void fail(Node<STATE> node);

    public List<Node<STATE>> popResult() {
        List<Node<STATE>> result = getResult();
        if (!edge.isEmpty()) edge.remove(0);
        return result;
    }

    public List<Node<STATE>> getResult() {
        List<Node<STATE>> result = new ArrayList<Node<STATE>>();
        Node<STATE> current = edge.isEmpty() ? null : edge.get(0);
        //Node<STATE> current = common.isEmpty() ? null : common.get(0);
        if (current == null) return null;
        while(current.prevNode != null) {
            result.add(0, current);
            current = current.prevNode;
        }
        result.add(0, current);
        return result;
    }

    public static class Node<STATE> {
        public STATE state;
        public Node<STATE> prevNode;//node with state from which we getFromList here with 'action'
        public int w;//number of steps taken on the route from the start

        public Node(Node<STATE> prevNode, STATE state) {
            this.prevNode = prevNode;
            if (prevNode != null) this.w = prevNode.w + 1;
            this.state = state;
        }

        @Override
        public String toString() {
            return "Node{prevNode.state=" + (prevNode == null ? "null" : prevNode.state) + ", w=" + w + ", state=" + state + "}\n";
        }
    }

}
