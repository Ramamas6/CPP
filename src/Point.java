package src;

import java.util.LinkedList;
import java.util.List;

public class Point {

    public static boolean diagonalMovementAllowed = false;

    public Integer x;
    public Integer y;
    public boolean free;

    public Point(int x, int y) {
        this(x, y, true);
    }
    public Point(int x, int y, boolean free) {
        this.x = x;
        this.y = y;
        this.free = free;
    }

    @Override
    public int hashCode() {
        return x + y * 100;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    public int get(int index) {
        if (index == 0) return x;
        if (index == 1) return y;
        throw new IndexOutOfBoundsException("Index must be 0 or 1");
    }

    public void set(int index, int newValue) {
        if (index == 0) x = newValue;
        else if (index == 1) y = newValue;
        else throw new IndexOutOfBoundsException("Index must be 0 or 1");
    }

    public void add(int index, int valueToAdd) {
        if (index == 0) x += valueToAdd;
        else if (index == 1) y += valueToAdd;
        else throw new IndexOutOfBoundsException("Index must be 0 or 1");
    }

    public List<Point> getSurroundingPoints() {
        List<Point> listOfCases = new LinkedList<>();
        for (int i = -1; i <= 1; i ++) {
            for (int j = -1; j <= 1; j ++) {
                if ((i != 0 || j != 0) && (diagonalMovementAllowed || Math.abs(i) + Math.abs(j) <= 1)) {
                    listOfCases.add(new Point(x+i, y+j));
                }
            }
        }
        return listOfCases;
    }
}
