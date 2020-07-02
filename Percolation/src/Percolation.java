import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Ethan Calvin Kim
 *
 */
public class Percolation {

    // grid which is connected to virtual top and bottom sites
    private final WeightedQuickUnionUF grid1;
    // grid which is NOT connected to virtual bottom site to avoid backwash
    private final WeightedQuickUnionUF grid2;
    // n by n grid. Represents side length.
    private final int n;
    // Top virutal node site. Also the total number of sites within the grid
    // (count starts from 1)
    private final int topRow;
    // Top virutal node site
    private final int bottomRow;
    // Records if site is opened (true) or closed (false)
    private boolean[] siteAccess;
    // # of sites opened
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
	if (n <= 0)
	    throw new IllegalArgumentException();
	this.n = n;
	this.topRow = n * n;
	this.bottomRow = topRow + 1;
	grid1 = new WeightedQuickUnionUF(topRow + 2);
	grid2 = new WeightedQuickUnionUF(bottomRow);
	// when initialized array, all values are false.
	siteAccess = new boolean[topRow];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
	int site = rowcolToSite(row, col);
	if (siteAccess[site])
	    return;
	siteAccess[site] = true;
	openSites++;
	// UNION the site that has just been opened to adjacent opened sites.
	// Ensure no IndexOutOfBoundsException happens!
	// For sites of the very left or right side, don't open the site on the next
	// row.
	// Most right site # in that row
	int rowmaxright = row * n - 1;
	// Most left site # in that row
	int rowmaxleft = (row - 1) * n;
	// right
	if (site + 1 < topRow && site + 1 <= rowmaxright) {
	    if (siteAccess[site + 1]) {
		grid1.union(site, site + 1);
		grid2.union(site, site + 1);
	    }
	}
	// left
	if (site - 1 >= 0 && site - 1 >= rowmaxleft) {
	    if (siteAccess[site - 1]) {
		grid1.union(site, site - 1);
		grid2.union(site, site - 1);		
	    }
	}
	// down
	if (site + n < topRow) {
	    if (siteAccess[site + n]) {
		grid1.union(site, site + n);
		grid2.union(site, site + n);		
	    }
	}
	// up
	if (site - n >= 0) {
	    if (siteAccess[site - n]) {
		grid1.union(site, site - n);
		grid2.union(site, site - n);		
	    }
	}
	// if site is a top or bottom site, UNION with virtual top and bottom sites.
	// top
	if (site < n) {
	    // UNION grid[topRow] to top row
	    grid1.union(topRow, site);
	    grid2.union(topRow, site);
	}
	// bottom
	if (site >= (topRow - n)) {
	    // UNION grid[bottomRow] to bottom row
	    grid1.union(bottomRow, site);
	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
	int site = rowcolToSite(row, col);
	return siteAccess[site];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
	// FIND if grid[site] is in union with grid[gridsize - 2] (virtual top site)
	int site = rowcolToSite(row, col);
	return grid2.find(site) == grid2.find(topRow);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
	return openSites;

    }

    // does the system percolate?
    public boolean percolates() {
	// FIND if grid[grid.length - 1] (virtual bottom site) is in union with
	// grid[grid.length - 2] (virtual top site)
	return grid1.find(topRow) == grid1.find(bottomRow);
    }

    private int rowcolToSite(int row, int col) {
	if (row <= 0 || row > n)
	    throw new IllegalArgumentException("row index " + row + " is not between 1 and " + n);
	if (col <= 0 || col > n)
	    throw new IllegalArgumentException("column index " + col + " is not between 1 and " + n);
	// Change indexes to start at 1, not 0
	int site = (row - 1) * n + (col - 1);
	return site;
    }

}
