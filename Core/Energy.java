package byog.Core;

import byog.TileEngine.TETile;
import java.util.LinkedList;

/**
 * This class deals with the energys places in the world. These energys allow the buddy
 * to eventually get rid of all the traps. This occurs if the maximum number of energys
 * is reached and the player still has lives.
 */
public class Energy implements java.io.Serializable {
    private final TETile energy;
    private LinkedList<Point> allEnergys;

    Energy(TETile energyTile) {
        this.energy = energyTile;
        allEnergys = new LinkedList<>();
    }

    /**
     * @return : The tile representing an energy.
     */
    public TETile getTile() {
        return energy;
    }

    /**
     * Add an energy to the list of current energys.
     *
     * @param position : The position of the new energy.
     */
    public void addEnergy(Point position) {
        allEnergys.add(position);
    }

    /**
     * @return : A list of all the current energys.
     */
    public LinkedList<Point> getAllEnergys() {
        return allEnergys;
    }

    /**
     * Remove all of the energys from the energy list.
     */
    public void removeAllEnergys() {
        while (!allEnergys.isEmpty()) {
            allEnergys.removeFirst();
        }
    }
}
