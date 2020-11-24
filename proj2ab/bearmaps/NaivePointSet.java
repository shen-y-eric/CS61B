package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }
    @Override
    public Point nearest(double x, double y) {
        Point best = points.get(0);
        Point goal = new Point(x, y);
        for (Point p : points) {
            double currentDistance = Point.distance(p, goal);
            if (currentDistance < Point.distance(best, goal)) {
                best = p;
            }
        }
        return best;
    }
}
