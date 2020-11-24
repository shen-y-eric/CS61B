package bearmaps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> minHeap;
    private HashMap<T, Integer> index;
    private int size;

    private class Node implements Comparable<Node> {
        private double p;
        private T item;

        Node(T i, double priority) {
            p = priority;
            item = i;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return p;
        }

        void setPriority(double priority) {
            p = priority;
        }

        @Override
        public int compareTo(Node other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((Node) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    public ArrayHeapMinPQ() {
        minHeap = new ArrayList<>();
        index = new HashMap<>();
        size = 0;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        } else {
            minHeap.add(new Node(item, priority));
            index.put(item, size);
            bubbleUp(size);
            size++;
        }

    }

    @Override
    public boolean contains(T item) {
        if (index.containsKey(item)) {
            return true;
        }
        return false;
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {
            return minHeap.get(0).item;
        }
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {
            T prev = getSmallest();
            swap(minHeap.get(0), minHeap.get(size - 1));
            minHeap.remove(size - 1);
            size--;
            index.remove(prev);
            bubbleDown(0);
            return prev;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        } else {
            int i = index.get(item);
            Node n = minHeap.get(index.get(item));
            n.setPriority(priority);
            bubbleDown(i);
            bubbleUp(i);
        }
    }

    private void bubbleUp(int i) {
        if (i > 0) {
            int pIndex = getParentOf(i);
            Node parent = minHeap.get(pIndex);
            Node current = minHeap.get(i);

            int cmp = current.compareTo(parent);
            if (cmp < 0) {
                swap(current, parent);
                bubbleUp(pIndex);
            }
        }
    }

    private void bubbleDown(int i) {
        int min = bubbleDownHelper(i);
        if (min != i) {
            swap(minHeap.get(i), minHeap.get(min));
            bubbleDown(min);
        }
    }

    private int bubbleDownHelper(int i) {
        int leftChild = getLeftOf(i);
        int rightChild = getRightOf(i);

        if (leftChild < size() && rightChild < size()) {
            int cmp = minHeap.get(leftChild).compareTo(minHeap.get(rightChild));
            if (cmp < 0) {
                i = leftChild;
            } else {
                i = rightChild;
            }
        }
        return i;
    }
    private void swap(Node i, Node j) {
        int iIndex = index.get(i.getItem());
        int jIndex = index.get(j.getItem());

        Node tmp = i;
        minHeap.set(iIndex, j);
        minHeap.set(jIndex, tmp);

        index.replace(i.getItem(), jIndex);
        index.replace(j.getItem(), iIndex);
    }

    private int getParentOf(int i) {
        return i / 2;
    }

    private int getLeftOf(int i) {
        return i * 2;
    }

    private int getRightOf(int i) {
        return i * 2 + 1;
    }

}
