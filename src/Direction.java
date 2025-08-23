package src;

public class Direction {
    public int directionX;
    public int directionY;

    public Direction(int directionX, int directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public Direction(Point p1, Point p2) {
        this(p2.x - p1.x, p2.y - p1.y);
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(directionX) + ", " + String.valueOf(directionY) + ")";
    }

    public boolean equals(Direction direction) {
        if( direction.directionX == 0 && direction.directionY == 0 ) return true;
        return directionX == direction.directionX && directionY == direction.directionY;
    }

    /*
     * This function (and the following 'getLinearRotation') returns the number of quarter turns needed to make the rotation
     */
    public int getRotation(Direction olDirection) {
        if(equals(olDirection)) return 0;
        return getLinearRotation(directionX, olDirection.directionX) + getLinearRotation(directionY, olDirection.directionY);
    }

    private int getLinearRotation(int linearDirection1, int linearDirection2) {
        if( linearDirection1 == 0 && linearDirection2 == 0) return 2;
        return Math.abs(linearDirection1 - linearDirection2);
    }
}
