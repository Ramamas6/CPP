package src.solvings;

import java.util.List;

import src.Point;

public interface Solving {
    /*
     * Function called at the start of a resolution
     * to initiate the algorithm with the starting point
     */
    public void start(Point startingPoint);

    /*
     * Function called at each step of the resolution
     * to pass the neighboring cells of the last cell visited
     * and return the next cell (or null if the resolution is finished)
     */
    public Point next(List<Point> newDiscoveredPoints);

    /*
     * Used to make the selection of the solver
     */
    public String getName();
}
