import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uF;
    private final boolean[][] grid;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private final int gridSize;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("N must be greater than 0.");

        this.uF = new WeightedQuickUnionUF(n * n + 2);
        this.virtualTopSite = 0;
        this.virtualBottomSite = n * n + 1;
        this.openSites = 0;
        this.gridSize = n;

        this.grid = new boolean[n][n];
        // for (int i = 1; i <= n; i++) {
        //     for (int j = 1; j <= n; j++) {
        //         this.grid[i][j] = new Site(i, j);
        //     }
        // }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.grid[row][col] = true;;
        this.openSites += 1;

        if(row == 1) {
            uF.union(this.virtualTopSite, col * row);
        } else if(row == this.gridSize) {
            uF.union(this.virtualBottomSite, col * row);
        } else {
            if (isOpen(row + 1, col)) uF.union(row * col, (row + 1) * col);
            if (isOpen(row - 1, col)) uF.union(row * col, (row - 1) * col);
            if (col + 1 <= gridSize) {
                if (isOpen(row, col + 1)) uF.union(row * col, row * (col + 1));
            }
            if (col - 1 > 0) {
                if (isOpen(row, col - 1)) uF.union(row * col, row * (col - 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return uF.find(0) == uF.find(row * col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uF.find(0) == uF.find(this.gridSize * this.gridSize + 1);
    }
}