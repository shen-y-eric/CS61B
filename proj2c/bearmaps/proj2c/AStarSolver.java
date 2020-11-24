package bearmaps.proj2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private List<Vertex> _solution;
    private double _solutionWeight;
    private SolverOutcome _outcome;
    private int _numStatesExplored;
    private double timeSpent;

    private ArrayHeapMinPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        _solution = new ArrayList<>();
        pq = new ArrayHeapMinPQ();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        _solutionWeight = 0;

        distTo.put(start, 0.0);
        pq.add(start, input.estimatedDistanceToGoal(start, end));

        while (pq.size() > 0) {
            if (pq.getSmallest().equals(end)) {
                _outcome = SolverOutcome.SOLVED;
                break;
            }
            if (sw.elapsedTime() > timeout) {
                _outcome = SolverOutcome.TIMEOUT;
                break;
            }

            Vertex curr = pq.removeSmallest();
            _numStatesExplored++;

            List<WeightedEdge<Vertex>> n = input.neighbors(curr);
            for (WeightedEdge<Vertex> e : n) {
                if (e.from().equals(curr)) {
                    relax(input, e, end);
                } else {
                    distTo.put(curr, e.weight());
                }
            }
        }

        if (_outcome == SolverOutcome.SOLVED) {
            _solutionWeight = distTo.get(end);
            while (!end.equals(start)) {
                _solution.add(end);
                end = edgeTo.get(end);
            }
            _solution.add(start);
        } else {
            _outcome = SolverOutcome.UNSOLVABLE;
        }
        timeSpent = sw.elapsedTime();
    }

    private void relax(AStarGraph<Vertex> input, WeightedEdge<Vertex> e, Vertex goal) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (distTo.get(p) + w < distTo.getOrDefault(q, Double.POSITIVE_INFINITY)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            } else {
                pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            }
        }

    }
    @Override
    public SolverOutcome outcome() {
        return _outcome;
    }

    @Override
    public List<Vertex> solution() {
        Collections.reverse(_solution);
        return _solution;
    }

    @Override
    public double solutionWeight() {
        return _solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return _numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
