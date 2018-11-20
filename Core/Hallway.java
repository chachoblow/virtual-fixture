package byog.Core;

import java.util.LinkedList;
import java.util.Random;

/**
 * Class for constructing a linked list representing an individual hallway.
 */
public class Hallway implements java.io.Serializable {
    private LinkedList<Point> hallway;
    private Point centerA;
    private Point centerB;
    private Random random;

    Hallway(Point centerA, Point centerB) {
        this.hallway = new LinkedList<>();
        this.centerA = centerA;
        this.centerB = centerB;
        this.random = new Random(1086);

        makeHallway();
    }

    /**
     * Make a hallway connecting centerA to centerB. Randomly, this is done by either
     * first connecting a vertical hallway to a horizontal, or by first connecting
     * a horizontal hallway to a vertical.
     */
    private void makeHallway() {
        if (random.nextInt(2) == 1) {
            makeVertical(centerA.getX());
            makeHorizontal(centerB.getY());
        } else {
            makeHorizontal(centerA.getY());
            makeVertical(centerB.getX());
        }
    }

    /**
     * Constructs a vertical hallway going from the minimum vertical height to the
     * maximum vertical height of the two center points.
     *
     * @param x : The x-coordinate along which the vertical hallway is built.
     */
    private void makeVertical(int x) {
        int minY = Math.min(centerA.getY(), centerB.getY());
        int maxY = Math.max(centerA.getY(), centerB.getY());
        for (int y = minY; y <= maxY; y += 1) {
            hallway.add(new Point(x, y));
        }
    }

    /**
     * Constructs a horizontal hallway going from the minimum horizontal length to the
     * maximum horizontal length of the two center points.
     *
     * @param y : The y-coordinate along which the horizontal hallway is built.
     */
    private void makeHorizontal(int y) {
        int minX = Math.min(centerA.getX(), centerB.getX());
        int maxX = Math.max(centerA.getX(), centerB.getX());
        for (int x = minX; x <= maxX; x += 1) {
            hallway.add(new Point(x, y));
        }
    }

    /**
     * @return : The linked list containing the hallway tiles.
     */
    public LinkedList<Point> getHallway() {
        return hallway;
    }
}
