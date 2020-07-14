import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author robhn
 * @param <Item>
 * @apiNote A double-ended queue or deque that supports adding and removing
 *          items from either the front or the back of the data structure.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first, last; // pointers to first and last node
    private int n; // size of deque

    // helper linked list class
    private class Node {
	private Item item;
	private Node next;
	private Node prev;
    }

    // construct an empty deque
    public Deque() {
	first = null;
	last = null;
	n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
	return n == 0;
	// alternateive Approach: return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
	return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
	if (item == null)
	    throw new IllegalArgumentException("Argument can not be null");
	Node oldFirst = first;
	first = new Node();
	first.item = item;
	first.next = oldFirst;
	first.prev = null;
	n++;
	if (last == null)
	    last = first;
	else
	    oldFirst.prev = first;
    }

    // add the item to the back
    public void addLast(Item item) {
	if (item == null)
	    throw new IllegalArgumentException("Argument can not be null");
	Node oldLast = last;
	last = new Node();
	last.item = item;
	last.prev = oldLast;
	last.next = null;
	n++;
	if (first == null)
	    first = last;
	else
	    oldLast.next = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
	if (isEmpty())
	    throw new NoSuchElementException("Can not remove when deque is empty");
	Item item = first.item;
	if (first.next == null) {
	    // only item left
	    first = null;
	    last = null;
	} else {
	    first = first.next;
	    first.prev = null;	    
	}
	n--;
	return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
	if (isEmpty())
	    throw new NoSuchElementException("Can not remove when deque is empty");
	Item item = last.item;
	if (last.prev == null) {
	    // only item left
	    last = null;
	    first = null;
	} else {
	    last = last.prev;
	    last.next = null;
	}
	n--;
	return item;
    }

    @Override
    public Iterator<Item> iterator() {
	return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

	private Node current = first;

	@Override
	public boolean hasNext() {
	    return current != null;
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException();
	}

	@Override
	public Item next() {
	    if (!hasNext())
		throw new NoSuchElementException("Queue is empty");
	    Item item = current.item;
	    current = current.next;
	    return item;
	}

    }

    // unit testing (required)
    public static void main(String[] args) {
	int n = 6;

	// test initialization
	Deque<Integer> q = new Deque<Integer>();

	// test addFirst and addLast
	int j = 0;
	while (j < n / 2) {
	    q.addFirst(n / 2 + j);
	    j++;
	    q.addLast(n / 2 - j);
	}

	System.out.println("Size: " + q.size());
	System.out.println("isEmpty: " + q.isEmpty());
	System.out.println("hasNext: " + q.iterator().hasNext());

	// test removeFirst and removeLast
	for (int i = 0; i < n / 2; i++) {
	    System.out.println(q.removeFirst());
	    System.out.println(q.removeLast());
	}

	// test size, isEmpty, and hasNext
	System.out.println("Size: " + q.size());
	System.out.println("isEmpty: " + q.isEmpty());
	System.out.println("hasNext: " + q.iterator().hasNext());
    }

}
