import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Ethan Kim
 *
 */
public class SampleClient {

    public static void main(String[] args) {

	In in = new In(args[0]); // input file
	int n = in.readInt(); // n points
	Point[] points = new Point[n];
	for (int i = 0; i < n; i++) {
	    int x = in.readInt();
	    int y = in.readInt();
	    points[i] = new Point(x, y);
	}

	// draw the points
	StdDraw.enableDoubleBuffering();
	StdDraw.setXscale(0, 32768);
	StdDraw.setYscale(0, 32768);
	for (Point p : points) {
	    p.draw();
	}
	StdDraw.show();

	// FastCollinearPoints collinear = new FastCollinearPoints(points);
	BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	System.out.println("Number of Segments: " + collinear.numberOfSegments());
	for (LineSegment s : collinear.segments()) {
	    s.draw();
	    System.out.println(s.toString());
	}
	StdDraw.show();
    }
    
}
