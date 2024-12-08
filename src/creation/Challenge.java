package src.creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import src.Point;

public class Challenge {

    private Generation generation;
    private int N;
    private Room room;
    private Point initialPoint;
    private int goal;

    private Point currentPoint;
    private int errors;
    private int walls;
    private int cleanedCases;
    private int roamedCases;

    public Challenge() {
        this.generation = new Generation();
    }

    public void setN(int N) { this.N = N; }

    public void newChallenge() {
        room = generation.generateMap(N);
        initialPoint = generation.generationPosition(room);
        goal = calculatesBestResolution(initialPoint);
    }
    public void newChallenge(int N) {
        setN(N);
        newChallenge();
    }

    public Point start() {
        room.reset();
        errors = 0;
        walls = 0;
        cleanedCases = 0;
        roamedCases = 0;
        currentPoint.x = initialPoint.x;
        currentPoint.y = initialPoint.y;
        return currentPoint;
    }

    public List<Point> next(Point newLocation) {
        List<Point> nextPoints = new ArrayList<>();
        // Check if newLocation is 1 case (or less) near last case
        if (Math.abs(currentPoint.x - newLocation.x) + Math.abs(currentPoint.y - newLocation.y) > 1) {
            errors += 1;
            return nextPoints;
        }
        // Check if the case is available
        if (!room.contains(newLocation)) {
            errors += 1;
            return nextPoints;
        }
        if (!room.isFree(newLocation)) {
            walls += 1;
            return nextPoints;
        }
        // Add the case to the roamed cases
        if (room.setRoamed(newLocation)) cleanedCases += 1;
        roamedCases += 1;
        currentPoint = newLocation;
        // return the cases next to it
        for (Point p : nextCases(newLocation)) {
            p.free = room.isFree(p);
            nextPoints.add(p);
        }
        return nextPoints;
    }

    public void verif() {
        System.out.println("Cases cleaned : " + String.valueOf(cleanedCases) + " / " + String.valueOf(goal));
        System.out.println("Roamed cases : " + String.valueOf(roamedCases));
        System.out.println("Errors : " + String.valueOf(errors));
        System.out.println("Walls : " + String.valueOf(walls));
    }

    private int calculatesBestResolution(Point startingPoint) {
        Set<Point> visited = new HashSet<>();
        Set<Point> toVisit = new HashSet<>();
        toVisit.add(startingPoint);
        while (toVisit.size() > 0) {
            Iterator<Point> iterator = toVisit.iterator();
            Point newPoint = iterator.next();
            iterator.remove();
            visited.add(newPoint);
            for (Point p : nextCases(newPoint))
                if (room.isFree(p) && !visited.contains(p)) toVisit.add(p);
        }
        return visited.size();
    }

    private List<Point> nextCases(Point point) {
        List<Point> listOfCases = new ArrayList<>();
        for (int i = -1; i <= 1; i ++) {
            for (int j = -1; j <= 1; j ++) {
                if ((i != 0 || j != 0) && (Math.abs(point.x - i) + Math.abs(point.y - j) <= 1)) {
                    listOfCases.add(new Point(i, j));
                }
            }
        }
        return listOfCases;
    }
}