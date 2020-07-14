import edu.princeton.cs.algs4.StdIn;

/**
 * 
 */

/**
 * @author robhn
 *
 */
public class Permutation {

    public static void main(String[] args) {
	int n = Integer.parseInt(args[0]);
	RandomizedQueue<String> q = new RandomizedQueue<String>();
	while (!StdIn.isEmpty()) {
	    q.enqueue(StdIn.readString());
	}
	for (int i = 0; i < n; i++) {
	    System.out.println(q.dequeue());
	}
	
    }

}
