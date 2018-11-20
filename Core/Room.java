
package byog.Core;

import java.util.LinkedList;

/**
 * Class representing a room in the world. Note, that the linked list of room tiles
 * is only the floor of the room. It does not include walls.
 */
public class Room implements java.io.Serializable {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Point center;
    private LinkedList<Point> roomTiles;

    Room(int x, int y, int width, int height) {
        this.x1 = x;
        this.x2 = x + width;
        this.y1 = y;
        this.y2 = y + height;
        this.center = new Point((x1 + x2) / 2, (y1 + y2) / 2);

        this.roomTiles = new LinkedList<>();

        // Add the room tiles based on the given coordinates.
        for (int i = x1; i < x2; i += 1) {
            for (int j = y1; j < y2; j += 1) {
                roomTiles.add(new Point(i, j));
            }
        }
    }

    /**
     * Check to see whether this room intersects with the given room.
     *
     * @param room : The room to check intersection with.
     * @return : Whether the two rooms intersect.
     */
    public boolean intersects(Room room) {
        return ((x1 <= room.x2 && x2 >= room.x1) && (y1 <= room.y2 && y2 >= room.y1));
    }

    /**
     * @return : The center point of the room.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @return : A linked list of room tiles.
     */
    public LinkedList<Point> getTiles() {
        return roomTiles;
    }
}
