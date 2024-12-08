package src.creation;

import src.Point;

public class Room {

    private int sizeX;
    private int sizeY;
    private State[][] room;

    Room(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.room = new State[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.room[i][j] = State.FREE;
            }
        }
    }

    void reset() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (this.room[i][j] == State.VISITED) this.room[i][j] = State.FREE;
            }
        }
    }

    int getRowSize() { return sizeX; }
    int getColumnSize() { return sizeY; }

    private boolean contains(int row, int column) {
        return (row >= 0 && column >= 0 && row < sizeX && column < sizeY);
    }
    boolean contains(Point point) { return contains(point.x, point.y); }

    private boolean isFree(int row, int column) {
        if (!contains(row, column)) return false;
        return room[row][column].getAvailable();
    }
    boolean isFree(Point point) { return isFree(point.x, point.y); }

    private void setWall(int row, int column) {
        if (!contains(row, column)) return;
        room[row][column] = State.WALL;
    }
    void setWall(Point point) { setWall(point.x, point.y); }
    
    private void setObstacle(int row, int column) {
        if (isFree(row, column)) room[row][column] = State.OBSTACLE;
    }
    void setObstacle(Point point) { setObstacle(point.x, point.y); }

    private boolean setRoamed(int row, int column) {
        if (!isFree(row, column)) return false;
        if (room[row][column] == State.VISITED) return false;
        room[row][column] = State.VISITED;
        return true;
    }
    boolean setRoamed(Point point) { return setRoamed(point.x, point.y); }
}