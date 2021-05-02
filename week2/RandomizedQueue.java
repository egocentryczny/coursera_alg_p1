import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n = 1;
    private Item[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue(){
        items = (Item[]) new Object[n];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (size == n) {
            resize(n * 2);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        int randomIdx = StdRandom.uniform(size);
        Item randomItem = items[randomIdx];
        items[randomIdx] = items[--size];
        items[size] = null;
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        return items[StdRandom.uniform(size)];
    }

    private void resize(int newSize) {
        n = newSize;
        Item[] newArray = (Item[]) new Object[n];
        for (int i = 0; i < items.length; i++) {
            newArray[i] = items[i];
        }
        items = newArray;
    }

    public void print() {
        StdOut.println("======================");
        StdOut.print("[ ");
        for (Item i : this) {
            StdOut.print(i + " ");
        }
        StdOut.print("]\n");
        StdOut.println("Size: " + size());
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueIterator();

    }

    private class QueIterator implements Iterator<Item> {
        private int currentIndex = 0;
        private final Item[] iteratorItems;

        public QueIterator() {
            iteratorItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iteratorItems[i] = items[i];
            }
            StdRandom.shuffle(iteratorItems);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < iteratorItems.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more elements");
            }
            return iteratorItems[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> que = new RandomizedQueue<>();
        StdOut.println("size: " + que.size());
        StdOut.println("empty: " + que.isEmpty());
        que.print();

        que.enqueue(1);
        StdOut.println("size: " + que.size());
        StdOut.println("empty: " + que.isEmpty());
        que.print();

        que.enqueue(2);
        que.enqueue(3);
        que.enqueue(4);
        que.enqueue(5);
        que.enqueue(6);
        que.print();
        que.print();
        que.print();
        que.print();
        StdOut.println(que.dequeue());
        StdOut.println(que.dequeue());
        StdOut.println(que.dequeue());
        StdOut.println(que.dequeue());
        StdOut.println("size: " + que.size());
        StdOut.println("empty: " + que.isEmpty());
        que.print();
        StdOut.println(que.sample());
        StdOut.println(que.sample());
        StdOut.println(que.sample());
        StdOut.println(que.sample());
        StdOut.println(que.dequeue());
        StdOut.println(que.dequeue());
        StdOut.println("size: " + que.size());
        StdOut.println("empty: " + que.isEmpty());
    }

}