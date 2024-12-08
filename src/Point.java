package src;

public class Point {
    public int x;
    public int y;
    public boolean free;

    public Point(int x, int y) {
        this(x, y, true);
    }
    public Point(int x, int y, boolean free) {
        this.x = x;
        this.y = y;
        this.free = free;
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
}
