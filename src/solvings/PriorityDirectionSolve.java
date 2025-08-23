package src.solvings;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import src.Point;

public class PriorityDirectionSolve implements Solving {

    private static final String NAME = "Priority direction";
    private static final int[][] FULL_ORDER = {{1, 1}, {1, 0}, {0, 1}, {1, -1}, {-1, 1}, {0, -1}, {-1, 0}, {-1, -1}};
    private static final int[][] SIMPLE_ORDER = {{1, 0}, {0, 1}, {0, -1}, {-1, 0}};

    private final LinkedList<Point> path;
    private final Set<Point> toVisit;
    private final Set<Point> visitedPoints;

    private Point currentPoint;

    public PriorityDirectionSolve() {
        path = new LinkedList<>();
        toVisit = new HashSet<>();
        visitedPoints = new HashSet<>();
    }

    @Override
    public void start(Point startingPoint) {
        path.clear();
        toVisit.clear();
        visitedPoints.clear();
        currentPoint = startingPoint;
        path.add(currentPoint);
    }

    @Override
    public Point next(List<Point> newDiscoveredPoints) {
        if (newDiscoveredPoints.size() == 0) {
            System.err.println("ERROR: STOPING ALGORITHM");   
            return null;
        }
        // Add the new discovered points
        for (Point point : newDiscoveredPoints) {
            if (point.free && !visitedPoints.contains(point))
                toVisit.add(point);
        }
        // Go to next case
        if (toVisit.size() == 0) return null;
        Point newPoint = new Point(currentPoint.x, currentPoint.y);
        // For each adjacent point, in order, if the point has to be visited, go to it
        for (int[] tuple : SIMPLE_ORDER) {
            newPoint.set(0, currentPoint.x + tuple[0]);
            newPoint.set(1, currentPoint.y + tuple[1]);
            if (toVisit.contains(newPoint)) {
                path.add(newPoint);
                visitedPoints.add(newPoint);
                toVisit.remove(newPoint);
                currentPoint = newPoint;
                return currentPoint;
            }
        }
        // If no adjacent point has to be visited, return to the previous square of the path travelled
        path.pollLast();
        currentPoint = path.peekLast();
        return currentPoint;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
