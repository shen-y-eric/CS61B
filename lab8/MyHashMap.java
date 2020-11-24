import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class MyHashMap<K, V> implements Map61B<K, V>{
    private class Node {
        K key;
        V value;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int size;
    private double loadFactor;
    private HashSet<K> keySet;
    private ArrayList<Node>[] buckets;

    private static final int DEFAULT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    public MyHashMap(int initialSize, double loadFactor) {
        size = 0;
        loadFactor = loadFactor;
        keySet = new HashSet<>();
        buckets = (ArrayList<Node>[]) new ArrayList[initialSize];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_LOAD_FACTOR);
    }
    @Override
    public void clear() {
        size = 0;
        keySet.clear();
        buckets = (ArrayList<Node>[]) new ArrayList[DEFAULT_SIZE];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
    }

    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    private Node getNode(K key) {
        ArrayList<Node> a = buckets[Math.floorMod(key.hashCode(), buckets.length)];
        for (Node n : a) {
            if (n.key.equals(key)){
                return n;
            }
        }
        return null;
    }

    @Override
    public V get(K key) {
        Node n = getNode(key);
        if (n != null) {
            return n.value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node n = getNode(key);
        if (n != null) {
            n.value = value;
            return;
        }

        if ((double) size/buckets.length == loadFactor) {
            resize(buckets.length * 2);
        }

        ArrayList<Node> a = buckets[Math.floorMod(key.hashCode(), buckets.length)];
        a.add(new Node(key, value));
        size++;
        keySet.add(key);
    }

    private void resize(int newSize) {
        size = 0;
        ArrayList<Node>[] oldBuckets = buckets;
        buckets = (ArrayList<Node>[]) new ArrayList[newSize];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (ArrayList<Node> a: oldBuckets) {
            for (Node n : a) {
                put(n.key, n.value);
            }
        }
    }
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }
}
