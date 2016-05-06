package yk.jcommon.search;

import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;

import java.util.*;
import java.util.function.Consumer;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 1/3/14
 * Time: 11:58 PM
 */
//TODO dynamic child counting and parents weight
abstract public class SSearch<STATE> implements Comparator<SSearch.Node<STATE>>, Iterable<SSearch.Node<STATE>>, Iterator<SSearch.Node<STATE>> {
    public YMap<STATE, Node<STATE>> seen = hm();
    //TODO sortedSeen - to find closest not solution
    public YList<Node<STATE>> edge = al();

    abstract public List<STATE> generate(Node<STATE> node);

    protected SSearch(STATE first) {
        Node<STATE> f = new Node<>(null, first);
        f.value = evaluate(f);
        edge.add(f);
        seen.put(first, f);
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

    public boolean isValid(Node<STATE> node) {
        return true;
    }

//    @Override
//    public Node<STATE> next() {
//
//        for (Node<STATE> node : edge) {
//            if (!seen.containsKey(node.state) && isValid(node)) {
//                seen.put(node.state, node);
//                List<STATE> nexts = generate(node);
//                for (STATE next : nexts) {
//                    Node newNode = new Node(node, next);
//                    newNode.value = evaluate(newNode);
//                    edge.add(newNode);
//                }
//                Collections.sort(edge, this);
//                return node;
//            }
//        }
//        return null;
//    }

    @Override
    public Node<STATE> next() {
        Node<STATE> node = edge.remove(0);
        List<STATE> nexts = generate(node);
        for (STATE next : nexts) {
            Node newNode = new Node(node, next);
            newNode.value = evaluate(newNode);
            if (!seen.containsKey(next) || seen.get(next).value < newNode.value) {
                seen.put(next, newNode);
                edge.add(newNode);
            }
        }
        Collections.sort(edge, this);
        return node;
    }

//    @Override
//    public Node<STATE> next() {
//        Node<STATE> node = edge.remove(0);
//        List<STATE> nexts = generate(node);
//        for (STATE next : nexts) if (!seen.containsKey(next)) {
//            Node newNode = new Node(node, next);
//            newNode.value = evaluate(newNode);
//            seen.put(next, newNode);
//            edge.add(newNode);
//        }
//        Collections.sort(edge, this);
//        return node;
//    }
//
    /**
     * bigger - better
     * @param node
     * @return
     */
    public float evaluate(Node<STATE> node) {
        return -node.deepness;
    }

    public Node<STATE> nextSolution(int steps) {
        for (int i = 0; i < steps && hasNext(); i++) {
            Node<STATE> cur = next();
            if (isSolution(cur)) return cur;
        }
        return null;
    }

    public Node<STATE> nextBest(int tries) {//can return same thing again
        Node<STATE> node = nextSolution(tries);
        return node != null ? node : seen.maxValue(st -> st.value);
    }


    @Override
    public int compare(Node<STATE> a, Node<STATE> b) {
        return Float.compare(b.value, a.value);
    }

    public boolean isSolution(Node<STATE> node) {
        return false;
    }

    public static class Node<STATE> {
        public STATE state;
        public Node<STATE> prevNode;
        public int deepness;
        public float value;

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
