import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation_old {
    
    private final WeightedQuickUnionUF quickUnionStructure;
    private final WeightedQuickUnionUF quickUnionStructureForIsFull;

    private int gridSize;
    private final boolean[][] grid;

    private int virtualTopSite;
    private int virtualBottomSite;
    private int openSites;


    public Percolation(int n) { // create N-by-N grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("N must be at least 1");
        }
        gridSize = n;
        grid = new boolean[n][n];

        quickUnionStructure = new WeightedQuickUnionUF(n * n + 2);
        quickUnionStructureForIsFull = new WeightedQuickUnionUF(n * n + 1);

        virtualTopSite = 0;
        virtualBottomSite = n * n + 1;
        openSites = 0;
    }

    public void open(int i, int j) {    // open site (row i, column j) if it is not open already
        if (!isOpen(i, j)) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(i, j);

            if (i == 1) {
                quickUnionStructure.union(virtualTopSite, fieldIndex);
                quickUnionStructureForIsFull.union(virtualTopSite, fieldIndex);
            }
            if (i == gridSize) {
                quickUnionStructure.union(virtualBottomSite, fieldIndex);
            }

            connectIfOpen(fieldIndex, i + 1, j);
            connectIfOpen(fieldIndex, i - 1, j);
            connectIfOpen(fieldIndex, i, j - 1);
            connectIfOpen(fieldIndex, i, j + 1);

            grid[i - 1][j - 1] = true;
            openSites += 1;
        }
    }

    public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
        return grid[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {   // is site (row i, column j) connected to top?
        if (isOpen(i, j)) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(i, j);
            return isConnected(virtualTopSite, fieldIndex);
        }
        return false;
    }

    public boolean percolates() {   // does the system percolate?
        return isConnected(virtualTopSite, virtualBottomSite);
    }

    private void connectIfOpen(int fieldIndex, int i, int j) {
        try {
            if (isOpen(i, j)) {
                int neighbourFieldIndex = getFieldIndexInQuickUnionStructure(i, j);
                quickUnionStructure.union(neighbourFieldIndex, fieldIndex);
                quickUnionStructureForIsFull.union(neighbourFieldIndex, fieldIndex);
            }
        } catch (IndexOutOfBoundsException e) {
            // don't connect field with field outside grid
        }
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    private int getFieldIndexInQuickUnionStructure(int i, int j) {
        return (i - 1) * gridSize + j;
    }

    private boolean isConnected(int p, int q) {
        return quickUnionStructure.find(p) == quickUnionStructure.find(q);
    }
}
