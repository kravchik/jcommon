package yk.jcommon.search;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 1/3/14
 * Time: 11:58 PM
 */
abstract public class SSearch<STATE> implements Comparator<SSearch.Node<STATE>>, Iterable<SSearch.Node<STATE>>, Iterator<SSearch.Node<STATE>> {
    public Map<STATE, Node<STATE>> seen = new HashMap<STATE, Node<STATE>>();
    //TODO sortedSeen - to find closest not solution
    protected List<Node<STATE>> edge = new ArrayList<Node<STATE>>();

    abstract public List<STATE> generate(Node<STATE> node);

    protected SSearch(STATE first) {
        edge.add(new Node<STATE>(null, first));
    }

    @Override
    public Iterator<Node<STATE>> iterator() {
        return this;
    }

    @Override
    public void forEach(Consumer<? super Node<STATE>> action) {
        throw new RuntimeException();
    }

    @Override
    public Spliterator<Node<STATE>> spliterator() {
        throw new RuntimeException();
    }

    @Override
    public boolean hasNext() {
        return !edge.isEmpty();
    }

    @Override
    public Node<STATE> next() {
        Node<STATE> node = edge.remove(0);
        List<STATE> nexts = generate(node);
        for (STATE next : nexts) if (!seen.containsKey(next)) {
            Node newNode = new Node(node, next);
            seen.put(next, newNode);
            edge.add(newNode);
        }
        Collections.sort(edge, this);
        return node;
    }

    public Node<STATE> nextSolution(int steps) {
        for (int i = 0; i < steps; i++) {
            Node<STATE> cur = next();
            if (isSolution(cur)) return cur;
        }
        return null;
    }

    @Override
    public int compare(Node<STATE> a, Node<STATE> b) {
        return a.deepness - b.deepness;
    }

    public boolean isSolution(Node<STATE> node) {
        return false;
    }

    public static class Node<STATE> {
        public STATE state;
        public Node<STATE> prevNode;
        public int deepness;

        public Node(Node<STATE> prevNode, STATE state) {
            this.prevNode = prevNode;
            if (prevNode != null) this.deepness = prevNode.deepness + 1;
            this.state = state;
        }

        @Override
        public String toString() {
            return "Node{prevNode.state=" + (prevNode == null ? "null" : prevNode.state) + ", w=" + deepness + ", state=" + state + "}\n";
        }
    }

}
