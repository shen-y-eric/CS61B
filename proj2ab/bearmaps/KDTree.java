package bearmaps;

import java.util.List;

/** @source Josh Hug: watched all his walkthrough videos extensively and used his ideas **/

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = true;

    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node left;
        private Node right;

        Node(Point p, boolean o) {
            this.p = p;
            orientation = o;
            left = null;
            right = null;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = insert(p, root, HORIZONTAL);
        }
    }

    private Node insert(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePoints(p, n.p, orientation);
        if (cmp < 0) {
            n.left = insert(p, n.left, !orientation);
        } else if (cmp >= 0) {
            n.right = insert(p, n.right, !orientation);
        } else {
            n.p = p;
        }
        return n;
    }

    private int comparePoints(Point p1, Point p2, boolean orientation) {
        if (orientation) {
            return Double.compare(p1.getX(), p2.getX());
        } else {
            return Double.compare(p1.getY(), p1.getY());
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node n = nearestHelper(root, goal, root);
        return n.p;
    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        Node goodSide;
        Node badSide;
        Point hypo;

        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        int cmp = comparePoints(goal, n.p, n.orientation);
        if (cmp < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }

        best = nearestHelper(goodSide, goal, best);

        if (n.orientation == HORIZONTAL) {
            hypo = new Point(n.p.getX(), goal.getY());
        } else {
            hypo = new Point(goal.getY(), n.p.getX());
        }

        if (Point.distance(goal, hypo) < Point.distance(goal, best.p)) {
            best = nearestHelper(badSide, goal, best);
        }
        return best;
    }
}
