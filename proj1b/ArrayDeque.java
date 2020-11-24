public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static double USAGE_FACTOR = 0.25;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        if (size() == items.length - 1) {
            resizeUp();
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /** Adds an of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        if (size() == items.length - 1) {
            resizeUp();
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. Once all the items have been printed,
     * print out a new line.
     */
    @Override
    public void printDeque() {
        int n = plusOne(nextFirst);
        while (n != nextLast) {
            System.out.print(items[n] + " ");
            n = plusOne(n);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            if (size() < USAGE_FACTOR * items.length && items.length > 8) {
                resizeDown();
            }
            T i = items[plusOne(nextFirst)];
            nextFirst = plusOne(nextFirst);
            items[nextFirst] = null;
            size--;
            return i;
        }
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            if (size() < USAGE_FACTOR * items.length && items.length > 8) {
                resizeDown();
            }
            T i = items[minusOne(nextLast)];
            nextLast = minusOne(nextLast);
            items[nextLast] = null;
            size--;
            return i;
        }
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     */
    @Override
    public T get(int index) {
        if (size() == 0) {
            return null;
        }
        return items[(index + plusOne(nextFirst)) % items.length];
    }

    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }

    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        } else {
            return index - 1;
        }
    }

    private void resizeUp() {
        T[] newItems = (T[]) new Object[items.length * 2];
        System.arraycopy(items, plusOne(nextFirst), newItems, 1, items.length - plusOne(nextFirst));
        System.arraycopy(items, 0, newItems, items.length - plusOne(nextFirst) + 1, nextLast);
        items = newItems;
        nextFirst = 0;
        nextLast = size + 1;
    }

    private void resizeDown() {
        T[] newItems = (T[]) new Object[(int) (items.length * 0.5)];
        if (nextFirst < nextLast) {
            System.arraycopy(items, nextFirst + 1, newItems, 1, size());
        } else {
            System.arraycopy(items, plusOne(nextFirst), newItems, 1, items.length - 1 - nextFirst);
            System.arraycopy(items, 0, newItems, items.length - nextFirst, nextLast);
        }
        items = newItems;
        nextFirst = 0;
        nextLast = size + 1;
    }
}
