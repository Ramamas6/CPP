package src.creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import src.Point;

public class Challenge {

    private final Generation generation;
    private final Room room;

    private Point initialPoint;
    private int casesNumber;

    private Point currentPoint;
    private int errors;
    private int walls;
    private int cleanedCases;
    private int roamedCases;

    public Challenge() {
        this.generation = new Generation();
        this.room = new Room();
        currentPoint = new Point(0,0);
    }

    public void newChallenge(int N) {
        generation.generateMap(room, N);
        initialPoint = generation.generationPosition(room);
    }

    public void refreshBestResolution() {
        casesNumber = calculatesBestResolution(initialPoint);
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
        if (!currentPoint.equals(newLocation) && !currentPoint.getSurroundingPoints().contains(newLocation)) {
            errors += 1;
            System.err.println("Error: new location unreachable");
            return nextPoints;
        }
        // Check if the case is available
        if (!room.contains(newLocation)) {
            errors += 1;
            System.err.println("Error: new location not in the room");
            return nextPoints;
        }
        if (!room.isFree(newLocation)) {
            walls += 1;
            System.err.println("Error: new location not free");
            return nextPoints;
        }
        // Add the case to the roamed cases
        if (room.setRoamed(newLocation)) cleanedCases += 1;
        roamedCases += 1;
        Point oldPoint = new Point(currentPoint.x, currentPoint.y);
        currentPoint = newLocation;
        // return the cases next to it
        for (Point p : newLocation.getSurroundingPoints()) {
            p.free = room.isFree(p);
            nextPoints.add(p);
        }
        room.repaint(oldPoint, currentPoint);
        return nextPoints;
    }

    public void verif() {
        System.out.println("Cases cleaned : " + String.valueOf(cleanedCases) + " / " + String.valueOf(casesNumber));
        System.out.println("Roamed cases : " + String.valueOf(roamedCases));
        System.out.println("Errors : " + String.valueOf(errors));
        System.out.println("Walls : " + String.valueOf(walls));
    }

    public int getCleanedCases() {
        return cleanedCases;
    }
    public int getRoamedCases() {
        return roamedCases;
    }
    public int getCasesNumber() {
        return casesNumber;
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
            for (Point p : newPoint.getSurroundingPoints())
                if (room.isFree(p) && !visited.contains(p)) toVisit.add(p);
        }
        return visited.size();
    }

    public JPanel getRoomPanel() { return room.getJPanel(); }
    public Generation getGeneration() { return generation; }
}