package src.solvings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import src.Point;

public class ShortestPathSolve implements Solving {

    private final Map<Point, Set<Point>> knownPoints;
    private final Set<Point> visitedPoints;
    private final Set<Point> toVisit;
    private final LinkedList<Point> goalPath;

    private Point currentPoint;

    public ShortestPathSolve() {
        toVisit = new HashSet<>();
        visitedPoints = new HashSet<>();
        knownPoints = new HashMap<>();
        goalPath = new LinkedList<>();
    }

    @Override
    public String getName() {
        return "Shortest Path";
    }

    @Override
    public void start(Point startingPoint) {
        toVisit.clear();
        visitedPoints.clear();
        knownPoints.clear();
        goalPath.clear();
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
        // Go to next case
        currentPoint = getNextPoint();
        return currentPoint;
    }

    private void addNewDiscoveredPoint(Point point) {
        knownPoints.computeIfAbsent(point, _ -> new HashSet<>()).add(currentPoint);
        toVisit.add(point);
    }

    private Point getNextPoint() {
        if (!actualiseGoalPath()) return null;
        if (goalPath.size() > 1) return goalPath.removeLast();
        Point goalPoint = goalPath.removeLast();
        visitedPoints.add(goalPoint);
        toVisit.remove(goalPoint);
        return goalPoint;
    }

    private boolean actualiseGoalPath() {
        if (goalPath.size() == 0) {
            if (toVisit.size() == 0) return false;
            goalPath.addAll(findShortestPath(currentPoint));
            if (goalPath.size() == 0) return false;
        }
        return true;
    }

    /*
     * Until it finds an unvisited cell, the function roam all known point,
     * in ascending order of distance, saving the previous cell for each one.
     */
    private LinkedList<Point> findShortestPath(Point initialPoint) {
        ArrayList<Point> liste = new ArrayList<>();
        liste.add(initialPoint);
        Map<Point, Point> originsOfPoints = new HashMap<>();
        originsOfPoints.put(initialPoint, null);
        while (liste.size() > 0) {
            // Get the first unroamed cell and roam all the cells around, if they are known and unroamed
            Point newPoint = liste.removeFirst();
            for (Point nextPoint: newPoint.getSurroundingPoints()) {
                if (knownPoints.containsKey(nextPoint) && !originsOfPoints.containsKey(nextPoint)) {
                    originsOfPoints.put(nextPoint, newPoint);
                    // If they are not visited, find the total path and end of the function
                    if (!visitedPoints.contains(nextPoint)) {
                        LinkedList<Point> path = new LinkedList<>();
                        findPath(originsOfPoints, nextPoint, path);
                        return path;
                    }
                    // Else, add them in the roamed ones
                    else liste.add(nextPoint);
                }
            }
        }
        return null;
    }

    /**
     * Recursive function : loops backwards to find the shortest path
     * @param originsOfPoints map of all the points with the previous cell in the shortest path
     * @param destination current destination point in the loop
     * @param path list of cells to be roamed to reach the nearest new cell
     */
    private void findPath(Map<Point, Point> originsOfPoints, Point destination, LinkedList<Point> path) {
        Point originPoint = originsOfPoints.get(destination);
        if (originPoint == null) return;
        path.add(destination);
        findPath(originsOfPoints, originPoint, path);
    }
}
