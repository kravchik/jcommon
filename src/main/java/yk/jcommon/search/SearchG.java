package yk.jcommon.search;
import java.util.*;

import static yk.jcommon.utils.Util.list;

/**
 * Kravchik Yuri
 * Date: 18.01.13
 */
//Search Generator
// 1. each state is GENERATOR of other states
// 2. value of each state can change over time
abstract public class SearchG {
    public List<Node> seen = list();
    public List<Node> edge = list();


    public void next() {
        Node cur = edge.get(0);
        edge.add(cur.next());
        if (!cur.hasNext()) {
            edge.remove(0);
            seen.add(cur);
        }
        Collections.sort(edge);

    }

    abstract public static class Node<T extends Node> implements Comparable<Node<T>> {
        T parent;
        float weight;

        protected Node(T parent) {
            this.parent = parent;
        }

        @Override
        public int compareTo(Node<T> o) { return Float.compare(weight, o.weight); }

        abstract public boolean hasNext();
        abstract public T next();

    }

}
