import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.y == that.y) {
            return 0.0;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return (that.y - this.y) / (double) (that.x - this.x);
        }
    }

    public int compareTo(Point that) {
        if (this.y == that.y) {
            return this.x - that.x;
        } else {
            return this.y - that.y;
        }
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point point0, Point point1) {
                double slope0 = slopeTo(point0);
                double slope1 = slopeTo(point1);

                return Double.compare(slope0, slope1);
            }
        };
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}