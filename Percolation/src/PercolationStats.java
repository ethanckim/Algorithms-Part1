import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Ethan Calvin Kim
 *
 */
public class PercolationStats {

    private static final double CONFIDENCE_PERCENTAGE = 1.96;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
	if (n <= 0 || trials <= 0) {
	    throw new IllegalArgumentException();
	}

	double[] pThreshold = new double[trials];

	// runs the experiment trials times
	for (int i = 0; i < trials; i++) {
	    Percolation grid = new Percolation(n);
	    while (!grid.percolates()) {
		int row = StdRandom.uniform(n) + 1;
		int col = StdRandom.uniform(n) + 1;
		if (!grid.isOpen(row, col))
		    grid.open(row, col);
	    }
	    pThreshold[i] = (double) grid.numberOfOpenSites() / n / n;
	}

	mean = StdStats.mean(pThreshold);
	if (trials == 1)
	    stddev = Double.NaN;
	else
	    stddev = StdStats.stddev(pThreshold);
	confidenceLo = mean - (CONFIDENCE_PERCENTAGE * stddev) / Math.sqrt(trials);
	confidenceHi = mean + (CONFIDENCE_PERCENTAGE * stddev) / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
	return mean;

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
	return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
	return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
	return confidenceHi;

    }

    // test client (see below)
    public static void main(String[] args) {
	int n = Integer.parseInt(args[0]);
	int t = Integer.parseInt(args[1]);
	PercolationStats test = new PercolationStats(n, t);
	System.out.println("mean                    = " + test.mean());
	System.out.println("stddev                  = " + test.stddev());
	System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }

}
