import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 */

/**
 * @author robhn
 * @param <Item>
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] a;         // array of items
    private int n;            // number of elements on stack
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
	return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
	return n;
	
    }
    
    // add the item
    public void enqueue(Item item) {
	if (item == null)
	    throw new IllegalArgumentException("Argument can not be null");

        if (n == a.length)
            resize(2 * a.length);    // double size of array if necessary
        a[n] = item;	// add item
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
	if (isEmpty())
	    throw new NoSuchElementException("Can not remove when deque is empty");
	int i = StdRandom.uniform(n);
	Item item = a[i];
	while (i < n) {
	    a[i] = a[i + 1];
	    i++;
	}
	n--;
	return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
	if (isEmpty())
	    throw new NoSuchElementException("Can not sample when deque is empty");
	return a[StdRandom.uniform(n)];	
    }

    
    @Override
    public Iterator<Item> iterator() {
	return new RandomQueueIterator();
    }
    
    private class RandomQueueIterator implements Iterator<Item> {
        private int i;

	public RandomQueueIterator() {
	    i = n - 1;
	}

	@Override
	public boolean hasNext() {
            return i >= 0;
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException();
	}

	@Override
	public Item next() {
	    if (!hasNext())
		throw new NoSuchElementException("Queue is empty");
	    return a[i--];
	}

    }
    
    public static void main(String[] args) {
	int n = 5;
	RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
	for (int i = 0; i < n; i++) {
	    q.enqueue(i);
	}
	System.out.println("Size: " + q.size());
	System.out.println("isEmpty: " + q.isEmpty());
	System.out.println("hasNext: " + q.iterator().hasNext());
	System.out.println("Sample:");
	for (int i = 0; i < n; i++) {
	    System.out.println(q.sample());
	}
	System.out.println("Dequeue:");
	for (int i = 0; i < n; i++) {
	    System.out.println(q.dequeue());
	}
	System.out.println("isEmpty: " + q.isEmpty());
	System.out.println("hasNext: " + q.iterator().hasNext());
    }
    
    // resize the underlying array holding the elements
    private void resize(int capacity) {
        if (capacity < n)
            return;

	Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;

       // alternative implementation
       // a = java.util.Arrays.copyOf(a, capacity);
    }

}
