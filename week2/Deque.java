import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private  class Node<T> {
        private final T node;
        private Node<T> next;
        private Node<T> previous;

        public Node(T node) {
            this.node = node;
        }
    }

    private Node<Item> first, last;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node<Item> newNode = new Node<>(item);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            first.previous = newNode;
            newNode.next = first;
            first = newNode;
        }
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node<Item> newNode = new Node<>(item);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size +=1;
    }

    // remove and return the item from the front
    public Item removeFirst() throws NoSuchElementException {
        if (this.first == null) {
            throw new NoSuchElementException("There is no first element");
        }
        Item toRet = first.node;
        first = first.next;
        if (first != null ) {
            first.previous = null;
        } else {
            last = null;
        }
        size -= 1;
        return toRet;
    }

    // remove and return the item from the back
    public Item removeLast() throws NoSuchElementException {
        if (this.last == null) {
            throw new NoSuchElementException("There is no last element");
        }
        Item toRet = last.node;
        last = last.previous;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        size -= 1;
        return toRet;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null; 
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }
                Item i = current.node;
                current = current.next;
                return i;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
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

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> que = new Deque<>();
        que.print();
        que.addFirst(1);
        que.removeFirst();
        que.print();

        que.addLast(2);
        que.print();
        que.removeLast();
        que.print();

        que.addFirst(3);
        que.print();
        que.removeLast();
        que.print();

        que.addLast(4);
        que.print();
        que.removeFirst();
        que.print();


        StdOut.println("size: " + que.size());
        StdOut.println(que.isEmpty());
        que.addFirst(2);
        que.addFirst(3);
        que.addFirst(4);
        que.addFirst(5);
        que.addLast(6);
        que.addLast(7);
        que.addLast(8);
        que.addLast(9);
        que.addLast(10);
        que.addFirst(0);
        que.print();
        StdOutt.println("size: " + que.size());

        StdOut.println(que.removeFirst());
        StdOut.println(que.removeFirst());
        StdOut.println(que.removeFirst());
        que.print();
        StdOut.println("size: " + que.size());

        StdOut.println(que.removeLast());
        StdOut.println(que.removeLast());
        StdOut.println(que.removeLast());
        que.print();
        StdOut.println("size: " + que.size());
    }

}