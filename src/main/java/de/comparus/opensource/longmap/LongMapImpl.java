package de.comparus.opensource.longmap;

import java.util.*;

public class LongMapImpl<V> implements LongMap<V> {
    private Node<V>[] hashTable;
    private int size = 0;
    private float threshold;

    public LongMapImpl() {
        hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }

    public V put(long key, V value) {
        if (size + 1 >= threshold) {
            threshold = threshold * 2;
            doubleTheArray();
        }
        Node<V> node = new Node<>(key, value);
        int index = node.hash();
        if (hashTable[index] == null) {
            return internalAdd(index, node);
        }
        List<Node<V>> nodeList = hashTable[index].getNodes();
        for (Node<V> n : nodeList) {
            if (keyOldValueNew(node, n, value) || collisionResolve(node, n, nodeList)) {
                return value;
            }
        }
        return null;
    }


    public V get(long key) {
        Long longKey = key;
        int index = hash(longKey);
        if (index < hashTable.length && hashTable[index] != null) {
            List<Node<V>> nodeList = hashTable[index].getNodes();
            for (Node<V> node : nodeList) {
                if (longKey.equals(node.getKey())) {
                    return node.getValue();
                }
            }
        }
        return null;
    }

    public V remove(long key) {
        Long longKey = key;
        int index = hash(longKey);
        if (hashTable[index].getNodes() == null) {
            return null;
        }
        if (hashTable[index].getNodes().size() == 1) {
            size--;
            return hashTable[index].getNodes().remove(0).getValue();
        }
        List<Node<V>> nodeList = hashTable[index].getNodes();
        for (Node<V> node : nodeList) {
            if (longKey.equals(node.getKey())) {
                nodeList.remove(node);
                size--;
                return node.getValue();
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        if (size() == 0) {
            return false;
        }
        for (Node<V> n : hashTable) {
            if (n != null) {
                for (Node<? extends V> node : n.getNodes()) {
                    if (node.getKey().equals(key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean containsValue(V value) {
        if (size() == 0) {
            return false;
        }
        for (Node<V> n : hashTable) {
            if (n != null) {
                for (Node<? extends V> node : n.getNodes()) {
                    if (node.getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public long[] keys() {
        List<Long> list = new ArrayList<>();
        for (Node<V> n : hashTable) {
            if (n != null) {
                for (Node<V> node : n.getNodes()) {
                    if (node.getKey() != null) {
                        list.add(node.getKey());
                    }
                }
            }
        }
        long[] keys = new long[list.size()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = list.get(i);
        }
        return keys;
    }

    public V[] values() {
        List<V> list = new ArrayList<>();
        for (Node<V> n : hashTable) {
            if (n != null) {
                for (Node<V> node : n.getNodes()) {
                    if (node.getValue() != null) {
                        list.add(node.getValue());
                    }
                }
            }
        }
        return reflectionToArrayMethod(list);
    }

    private <Z> Z[] reflectionToArrayMethod(List<Z> list) {
        Z[] toR = (Z[]) java.lang.reflect.Array.newInstance(list.get(0).getClass(), list.size());
        for (int i = 0; i < list.size(); i++) {
            toR[i] = list.get(i);
        }
        return toR;
    }

    public long size() {
        return size;
    }

    public void clear() {
        if (size() > 0) {
            hashTable = new Node[16];
            size = 0;
            threshold = hashTable.length * 0.75f;
        }
    }


    private boolean collisionResolve(Node<V> node, Node<V> n, List<Node<V>> nodeList) {
        if ((node.hashCode() == n.hashCode()) && !Objects.equals(node.key, n.key) && !Objects.equals(node.value, n.value)) {
            nodeList.add(node);
            size++;
            return true;
        }
        return false;
    }

    private void doubleTheArray() {
        Node<V>[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node<? extends V> n : oldHashTable) {
            if (n != null) {
                for (Node<? extends V> node : n.getNodes()) {
                    put(node.key, node.value);
                }
            }
        }
    }

    private boolean keyOldValueNew(Node<V> node, Node<V> n, V value) {
        if (node.getKey().equals(n.getKey()) && !node.getValue().equals(n.getValue())) {
            n.setValue(value);
            return true;
        }
        return false;
    }


    private V internalAdd(int index, Node<V> node) {
        hashTable[index] = new Node<V>(0, null);
        List<Node<V>> nodeList = hashTable[index].getNodes();
        nodeList.add(node);
        size++;
        return nodeList.stream().findFirst().get().value;
    }

    private int hash(final Long key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();

        return hash % hashTable.length;
    }

    private class Node<V> {
        private List<Node<V>> nodes;
        private int hash;
        private Long key;
        private V value;

        private Node(long key, V value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<>();
        }

        private List<Node<V>> getNodes() {
            return nodes;
        }

        private int hash() {
            if (hashCode() % hashTable.length < 0) {
                return hashCode() % hashTable.length * -1;
            }
            return hashCode() % hashTable.length;
        }

        private Long getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;

            if (hash != node.hash) return false;
            if (!key.equals(node.key)) return false;
            if (nodes != null ? !nodes.equals(node.nodes) : node.nodes != null) return false;
            return value != null ? value.equals(node.value) : node.value == null;
        }

        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + key.hashCode();
            return hash;
        }
    }
}
