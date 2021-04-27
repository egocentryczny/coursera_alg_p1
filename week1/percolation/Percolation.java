import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private class Site {
        private int row;
        private int col;
        private boolean isOpen;
        private boolean isFull;

        Site(int row, int col) {
            this.row = row;
            this.col = col;
            this.isOpen = false;
            this.isFull = false;
        }

        public int getRow() {
            return this.row;
        }

        public int getCol() {
            return this.col;
        }

        public void open() {
            this.isOpen = true;
        }

        public boolean isOpen() {
            return this.isOpen;
        }

        public void full() {
            this.isFull = true;
        }

        public boolean isFull() {
            return this.isFull;
        }
    }
    private final WeightedQuickUnionUF uF;
    private final Site[][] grid;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private final int openSites;
    private final int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("N must be greater than 0.");

        this.UF = new WeightedQuickUnionUF(n * n + 2);
        this.virtualTopSite = 0;
        this.virtualBottomSite = n * n + 1;
        this.openSites = 0;
        this.gridSize = n;

        this.grid = new Site[n][n];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                this.grid[i][j] = new Site(i, j);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.grid[row][col].open();
        this.openSites += 1;

        if(row == 1) {
            UF.union(this.virtualTopSite, col * row);
        } else if(row == this.gridSize) {
            UF.union(this.virtualBottomSite, col * row);
        } else {
            if (this.grid[row + 1][col].isOpen()) UF.union(row * col, (row + 1) * col);
            if (this.grid[row - 1][col].isOpen()) UF.union(row * col, (row - 1) * col);
            if (col + 1 <= gridSize) {
                if (this.grid[row][col + 1].isOpen()) UF.union(row * col, row * (col + 1));
            }
            if (col - 1 > 0) {
                if (this.grid[row][col - 1].isOpen()) UF.union(row * col, row * (col - 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.grid[row][col].isOpen();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return UF.find(0) == UF.find(row * col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.find(0) == UF.find(this.gridSize * this.gridSize + 1);
    }
}