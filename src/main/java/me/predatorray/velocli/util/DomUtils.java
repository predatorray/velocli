package me.predatorray.velocli.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;

public class DomUtils {

    public Iterator<Node> iter(final NodeList nodeList) {
        return new Iterator<Node>() {

            private int index = 0;
            private final int size = nodeList.getLength();

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Node next() {
                return nodeList.item(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
