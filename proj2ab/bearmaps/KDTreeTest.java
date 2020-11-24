package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private static final double LOWER_BOUND = -1000;
    private static final double UPPER_BOUND = 1000;

    private static Random r = new Random();

    private Point randomPoint() {
        double x = LOWER_BOUND + (UPPER_BOUND - LOWER_BOUND) * r.nextDouble();
        double y = LOWER_BOUND + (UPPER_BOUND - LOWER_BOUND) * r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> pointList(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }
    private static KDTree buildTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;
    }

    @Test
    public void testNearest() {
        KDTree kd = buildTree();
        Point actual = kd.nearest(0,7);
        Point expected = new Point(1,5);
        assertEquals(expected, actual);
    }

    @Test
    public void testNPoints() {
        List<Point> points = pointList(1000);
        NaivePointSet n = new NaivePointSet(points);
        KDTree k = new KDTree(points);

        List<Point> q = pointList(200);
        for (Point p : q) {
            Point expected = n.nearest(p.getX(), p.getY());
            Point actual = k.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testTiming() {
        List<Point> points = pointList(100000);

        List<Point> q = pointList(10000);

        Stopwatch sw1 = new Stopwatch();
        NaivePointSet n = new NaivePointSet(points);
        double a1Time = sw1.elapsedTime();

        System.out.println("NaivePointSet Build Time: " + a1Time);

        Stopwatch sw2 = new Stopwatch();
        KDTree k = new KDTree(points);
        double a2Time = sw2.elapsedTime();

        System.out.println("KDTree Build Time: " + a2Time);

        Stopwatch sw3 = new Stopwatch();
        for (Point p : q) {
            Point expected = n.nearest(p.getX(), p.getY());
        }
        double a3Time = sw3.elapsedTime();
        System.out.println("NaivePointSet Nearest Runtime: " + a3Time);

        Stopwatch sw4 = new Stopwatch();
        for (Point p : q) {
            Point expected = k.nearest(p.getX(), p.getY());
        }
        double a4Time = sw4.elapsedTime();
        System.out.println("KDTree Nearest Runtime: " + a4Time);
    }
}
