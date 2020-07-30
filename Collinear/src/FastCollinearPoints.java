import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author Ethan Kim
 *
 */
public class FastCollinearPoints {

    private final LineSegment[] colLineSeg;

    public FastCollinearPoints(Point[] points) {

	// check corner cases
	validateInput(points);

	// copy points array to avoid mutating original's values,
	// especially in the upcomming for loop.
	Point[] pointsCopy = Arrays.copyOf(points, points.length);

	// check for duplicate points
	Arrays.sort(pointsCopy);
	for (int i = 0; i < pointsCopy.length - 1; i++) {
	    if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
		throw new IllegalArgumentException("No two points in the array can be the same");
	}

	// ArrayList to store collinear linesegments. Dynamic capacity & can find size.
	ArrayList<LineSegment> colLineSegList = new ArrayList<LineSegment>();
	// to avoid duplicate linesegments, store already found
	// min points of line segments & slopes
	ArrayList<Point> minPointsFound = new ArrayList<Point>();
	ArrayList<Point> maxPointsFound = new ArrayList<Point>();

	for (Point p : points) {

	    // sort in terms of slope with starting point p
	    Arrays.sort(pointsCopy, p.slopeOrder());

	    // store points that have the same slope on ArrayList sameSlopes.
	    ArrayList<Point> sameSlopes = new ArrayList<Point>();
	    double currentSlope = Double.NEGATIVE_INFINITY;
	    double nextSlope;
	    for (int i = 1; i < pointsCopy.length; i++) {
		nextSlope = p.slopeTo(pointsCopy[i]);
		if (currentSlope != nextSlope) {
		    // check if 4+ points have the same slope, then add line seg to arraylist.
		    addCollinearLineSegment(sameSlopes, p, minPointsFound, maxPointsFound,
			    colLineSegList);
		    sameSlopes.clear();
		}
		sameSlopes.add(pointsCopy[i]);
		currentSlope = nextSlope;
	    }
	    addCollinearLineSegment(sameSlopes, p, minPointsFound, maxPointsFound, colLineSegList);
	}

	colLineSeg = colLineSegList.toArray(new LineSegment[colLineSegList.size()]);

    }

    public int numberOfSegments() {
	return colLineSeg.length;
    }

    public LineSegment[] segments() {
	return Arrays.copyOf(colLineSeg, colLineSeg.length);
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

	FastCollinearPoints collinear = new FastCollinearPoints(points);
	System.out.println("Number of Segments: " + collinear.numberOfSegments());
	for (LineSegment s : collinear.segments()) {
	    s.draw();
	    System.out.println(s.toString());
	}
	StdDraw.show();

	long endTime = System.nanoTime();
	double totalTime = (double) (endTime - startTime);
	System.out.println("Runtime: " + totalTime / 1000000000 + " s");
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
    }

    /**
     * @param sameSlopes     an array of points on a same colinear line based off
     *                       the slope each point makes with p
     * @param p              the point in reference. All slopes are based off of
     *                       point p in a given looping interval
     * @param minPointsFound the arraylist which stores the minimum points of each
     *                       collinear line already found (based on natural order).
     *                       Use with maxPointsFound to avoid duplicate collinear
     *                       lines
     * @param maxPointsFound the arraylist which stores the maximum points of each
     *                       collinear line already found (based on natural order).
     *                       Use with minPointsFound to avoid duplicate collinear
     *                       lines
     * @param colLineSegList the arraylist which stores the found collinear lines
     * 
     */
    private void addCollinearLineSegment(ArrayList<Point> sameSlopes, Point p, ArrayList<Point> minPointsFound,
	    ArrayList<Point> maxPointsFound, ArrayList<LineSegment> colLineSegList) {
	// reject less then 3 points
	if (sameSlopes.size() < 3)
	    return;

	sameSlopes.add(p);
	Collections.sort(sameSlopes);
	Point minPoint = sameSlopes.get(0);
	Point maxPoint = sameSlopes.get(sameSlopes.size() - 1);

	// check if this line segment is already recorded
	for (int i = 0; i < minPointsFound.size(); i++) {
	    if (minPointsFound.get(i).compareTo(minPoint) == 0 && maxPointsFound.get(i).compareTo(maxPoint) == 0)
		return;
	}
	minPointsFound.add(minPoint);
	maxPointsFound.add(maxPoint);
	colLineSegList.add(new LineSegment(minPoint, maxPoint));

    }

}
