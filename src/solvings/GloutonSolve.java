package src.solvings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import src.Point;

public class GloutonSolve implements Solving {

    private final Map<Point, Set<Point>> knownPoints;
    private final Set<Point> visitedPoints;
    private final LinkedList<Point> toVisit;

    private Point currentPoint;
    private Point goalPoint;

    public GloutonSolve() {
        toVisit = new LinkedList<>();
        visitedPoints = new HashSet<>();
        knownPoints = new HashMap<>();
    }

    @Override
    public String getName() {
        return "Glouton";
    }

    @Override
    public void start(Point startingPoint) {
        toVisit.clear();
        visitedPoints.clear();
        knownPoints.clear();
        goalPoint = null;
        currentPoint = startingPoint;
        visitedPoints.add(startingPoint);
    }

    @Override
    public Point next(List<Point> newDiscoveredPoints) {
        if (newDiscoveredPoints.size() == 0) {
            System.err.println("ERROR: STOPING ALGORITHM");   
            return null;
        }
        // Add the new discovered points
        for (Point point : newDiscoveredPoints) {
            if (point.free) {
                knownPoints.computeIfAbsent(currentPoint, _ -> new HashSet<>()).add(point);
                if (!visitedPoints.contains(point)) addNewDiscoveredPoint(point);
            }
        }
        // Find next objective square to go (if needed)
        if (goalPoint == null) {
            if (toVisit.size() == 0) return null;
            goalPoint = toVisit.pollLast();
        }
        // Go to next case
        currentPoint = getNextPoint();
        if (!visitedPoints.contains(currentPoint)) {
            visitedPoints.add(currentPoint);
            toVisit.remove(currentPoint);
        }
        if (goalPoint.equals(currentPoint)) goalPoint = null;
        return currentPoint;
    }

    private void addNewDiscoveredPoint(Point point) {
        knownPoints.computeIfAbsent(point, _ -> new HashSet<>()).add(currentPoint);
        toVisit.remove(point);
        toVisit.add(point);
    }

    private Point getNextPoint() {
        if (currentPoint.getSurroundingPoints().contains(goalPoint))return goalPoint;
        return findNextPath(goalPoint);
    }

    /**
     * Find the next cell to go in order to reach the point destination
     * The function starts from the destination and roams all known cells around it, ordered by distance
     * Known defaults :
     *      - Exponential complexity (and must be entirely rerun at each step)
     *      - doesn't take into acount pytagore theorem if diagonal movement isn't enabled
     * @param destination the point the program currently want to reach
     * @return the next cell to go to reach the destination, or null if no cell found
     */
    private Point findNextPath(Point destination) {
        List<Point> surroundingPoints = currentPoint.getSurroundingPoints();
        ArrayList<Point> liste = new ArrayList<>();
        liste.add(destination);
        int index = 0;
        while (index < liste.size()) {
            Point newPoint = liste.get(index);
            if (surroundingPoints.contains(newPoint)) return newPoint;
            for (Point p: newPoint.getSurroundingPoints())
                if (!liste.contains(p) && knownPoints.containsKey(p)) liste.add(p);
            index += 1;
        }
        return null;
    }
}
