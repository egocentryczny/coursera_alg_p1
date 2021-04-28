import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final double meanValue;
    private final double stddevValue;
    private final double confidenceLoValue;
    private final double confidenceHiValue;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double [] results = new double[trials];
        int testSiteY;
        int testSiteX;

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                do {
                    testSiteY = (int) (StdRandom.uniform()*n) + 1;
                    testSiteX = (int) (StdRandom.uniform()*n) + 1;
                } while (p.isOpen(testSiteY, testSiteX));

                p.open(testSiteY, testSiteX);
            }
            results[i] = (double) (p.numberOfOpenSites()) / (n*n);
        }

        meanValue = StdStats.mean(results);
        stddevValue = StdStats.stddev(results);

        confidenceLoValue = meanValue - (1.96*stddevValue)/Math.sqrt(trials);
        confidenceHiValue = meanValue + (1.96*stddevValue)/Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanValue;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddevValue;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLoValue;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHiValue;
    }

    // test client (see below)
    public static void main(String[] args) {
        int a, b;
        a = StdIn.readInt();
        b = StdIn.readInt();

        try {
            PercolationStats pp = new PercolationStats(a, b);
            StdOut.println("mean                    = " + pp.mean());
            StdOut.println("stddev                  = " + pp.stddev());
            StdOut.println("95% confidence interval = [" + pp.confidenceLo() + ", " + pp.confidenceHi() + "]");
        } catch (IllegalArgumentException e) {
            StdOut.println(e.getMessage());
        }
    }
}
