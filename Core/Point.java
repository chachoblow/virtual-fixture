package byog.Core;

/**
 * Class representing a point.
 */
public class Point implements java.io.Serializable {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        x = 0;
        y = 0;
    }

    /**
     * Return the distance from this point and the given point.
     *
     * @param a : The point to calculate distance from.
     * @return : The distance from Point a.
     */
    public double distance(Point a) {
        return Math.sqrt((a.x - x) * (a.x - x) + (a.y - y) * (a.y - y));
    }

    /**
     * @return : The x-coordinate of this point.
     */
    public int getX() {
        return x;
    }

    /**
     * @return : The y-coordinate of this point.
     */
    public int getY() {
        return y;
    }

    /**
     * Set the x-coordinate of this point.
     *
     * @param x : The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y-coordinate of this point.
     *
     * @param y : The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }
}
