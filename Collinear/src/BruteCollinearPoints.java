import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author Ethan Kim
 *
 */
public class BruteCollinearPoints {

    private final LineSegment[] connects4;

    public BruteCollinearPoints(Point[] points) {

	validateInput(points);
	
	// copy points array to avoid mutating original's values
	Point[] pointsCopy = Arrays.copyOf(points, points.length);

	// sort array by natural order: from low to high y coordinate
	// if tied, sort by x coordinate.
	// this way, lines segments are formed in ascending order.
	// Endpoints are clear during loop.
	Arrays.sort(pointsCopy);
	
	// an ArrayList to store linesegments. Dynamic capacity & can find size
	ArrayList<LineSegment> connects4List = new ArrayList<LineSegment>();

	for (int p = 0; p < pointsCopy.length - 3; p++) {
	    for (int q = p + 1; q < pointsCopy.length - 2; q++) {
		for (int r = q + 1; r < pointsCopy.length - 1; r++) {
		    for (int s = r + 1; s < pointsCopy.length; s++) {
			double slopepq = pointsCopy[p].slopeTo(pointsCopy[q]);
			double slopeqr = pointsCopy[q].slopeTo(pointsCopy[r]);
			double slopers = pointsCopy[r].slopeTo(pointsCopy[s]);
			if (slopepq == slopeqr && slopeqr == slopers) {
			    connects4List.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
			}
		    }
		}
	    }
	}
	connects4 = connects4List.toArray(new LineSegment[connects4List.size()]);
    }

    public int numberOfSegments() {
	return connects4.length;
    }

    public LineSegment[] segments() {
	return Arrays.copyOf(connects4, connects4.length);
    }

    public static void main(String[] args) {
	long startTime = System.nanoTime();

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

	BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	System.out.println("Number of Segments: " + collinear.numberOfSegments());
	for (LineSegment s : collinear.segments()) {
	    s.draw();
	    System.out.println(s.toString());
	}
	StdDraw.show();
	
	long endTime = System.nanoTime();
	double totalTime = (double) (endTime - startTime);
	System.out.println("Runtime: " + totalTime/1000000000 + " s");
    }
    
    /**
     * @param points
     */
    private void validateInput(Point[] points) {
	if (points == null)
	    throw new IllegalArgumentException("Argument cannot be null");
	
	for (Point p : points)
	    if (p == null)
		throw new IllegalArgumentException("Point(s) given in the array argument cannot be null");
	
	for (int i = 0; i < points.length; i++) {
	    for (int j = i + 1; j < points.length; j++) {
		if (points[i].compareTo(points[j]) == 0)
		    throw new IllegalArgumentException("No two points in the array can be the same");
	    }
	}
    }

}
