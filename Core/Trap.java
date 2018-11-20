package byog.Core;

import byog.TileEngine.TETile;
import java.util.LinkedList;

/**
 * This class deals with the traps of the world. Traps hurt the player in both the
 * virtual and real worlds. However, they can only be seen in the real.
 */
public class Trap implements java.io.Serializable {
    private final TETile trap;
    private LinkedList<Point> allTraps;

    Trap(TETile trapTile) {
        this.trap = trapTile;
        allTraps = new LinkedList<>();
    }

    /**
     * @return : The tile representing a trap.
     */
    public TETile getTile() {
        return trap;
    }

    /**
     * Add a trap to the list of traps.
     *
     * @param position : The position of the new traps.
     */
    public void addTrap(Point position) {
        allTraps.add(position);
    }

    /**
     * @return : The list of all the current traps.
     */
    public LinkedList<Point> getAllTraps() {
        return allTraps;
    }

    /**
     * Remove all of the traps from the trap list.
     */
    public void removeAllTraps() {
        while (!allTraps.isEmpty()) {
            allTraps.removeFirst();
        }
    }
}
