package src.creation;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import src.Point;

public class Generation {

    private Random random;
    private int cuttingProba = 3;

    private Function<Integer, Integer> minRowSize;
    private Function<Integer, Integer> maxRowSize;
    private Function<Integer, Integer> minColumnSize;
    private Function<Integer, Integer> maxColumnSize;

    private Function<Integer, Integer> minObstacles;
    private Function<Integer, Integer> maxObstacles;
    private BiFunction<Integer, Integer, Integer> obstaclesSize;

    Generation() {
        setDefaultFunctions();
    }

    Point generationPosition(Room room) {
        Point currentPoint = new Point(random.nextInt(room.getRowSize()), (random.nextInt(room.getColumnSize())));
        boolean flag = true;
        while (!room.isFree(currentPoint)) {
            if (flag) currentPoint.x = random.nextInt(room.getRowSize());
            else currentPoint.y = random.nextInt(room.getColumnSize());
            flag = !flag;
        }
        return currentPoint;
    }

    void generateMap(Room room, int N) {
        random = new Random();
        // Generate the map
        int rowSize = random.nextInt(minRowSize.apply(N), maxRowSize.apply(N));
        int columnSize = random.nextInt(minColumnSize.apply(N), maxColumnSize.apply(N));
        room.newRoom(rowSize, columnSize);
        // Cutting : cut some parts so the room is not a square
        cutting(room, 0, rowSize, columnSize);
        cutting(room, 1, rowSize, columnSize);
        // OBSTACLES : add several obsacles
        int nbrObstacles = random.nextInt(minObstacles.apply(N), maxObstacles.apply(N));
        for (int n = 0; n < nbrObstacles; n ++)
            addObstacle(room, obstaclesSize.apply(N, n), rowSize, columnSize);
        room.buildPanel();
    }

    private void cutting(Room room, Integer mainDirection, int rowSize, int columnSize) {
        // Initialisation
        Point currentPoint;
        int direction = random.nextInt(2)*2- 1; // -1 or +1
        int starting = random.nextInt(2); // start or end
        int cuttingDirection = new int[]{-1, 1}[starting];
        if (mainDirection == 0) currentPoint = new Point(random.nextInt(rowSize), starting*(columnSize-1));
        else currentPoint = new Point(starting*(rowSize-1), random.nextInt(columnSize));
        // Main part
        while (room.contains(currentPoint)) {
            if (random.nextInt(cuttingProba+1) == 0) { // Cut more deeply
                Point point = new Point(currentPoint.x, currentPoint.y);
                while (room.contains(point)) {
                    room.setWall(point);
                    point.add(mainDirection, direction);
                }
                currentPoint.add(1-mainDirection, -cuttingDirection);
            }
            else { // Decrease
                room.setWall(currentPoint);
                currentPoint.add(mainDirection, direction);
            }
        }
    }

    private void addObstacle(Room room, Integer size, int rowSize, int columnSize) {
        Point currentPoint = new Point(random.nextInt(rowSize), random.nextInt(columnSize));
        room.setObstacle(currentPoint);
        while (random.nextInt(size) != 0) {
            currentPoint.add(random.nextInt(2), random.nextInt(2)*2- 1); // Add -1 or +1
            room.setObstacle(currentPoint);
        }
    }

    public void setMinRowSize (Function<Integer, Integer> minRowSize) { this.minRowSize = minRowSize; }
    public void setMaxRowSize (Function<Integer, Integer> maxRowSize) { this.maxRowSize = maxRowSize; }
    public void setMinColumnSize (Function<Integer, Integer> minColumnSize) { this.minColumnSize = minColumnSize; }
    public void setMaxColumnSize (Function<Integer, Integer> maxColumnSize) { this.maxColumnSize = maxColumnSize; }
    public void setMinObstacles (Function<Integer, Integer> minObstacles) { this.minObstacles = minObstacles; }
    public void setMaxObstacles (Function<Integer, Integer> maxObstacles) { this.maxObstacles = minRowSize; }
    public void setObstaclesSize (BiFunction<Integer, Integer, Integer> obstaclesSize) { this.obstaclesSize = obstaclesSize; }

    private void setDefaultFunctions() {
        setMinRowSize(n -> 10*n);
        setMaxRowSize(n -> 20*n);
        setMinColumnSize(n -> 10*n);
        setMaxColumnSize(n -> 20*n);
        setMinObstacles(_ -> 4);
        setMaxObstacles(n -> 5*n);
        setObstaclesSize((n, N) -> 2*N+3*n);
    }
}
