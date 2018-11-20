package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * This class implements the player's buddy. The buddy functions as a companion to the player.
 * He performs no action and does not affect the players. The buddy's only function is to
 * follow the player as they move.
 */
public class Buddy implements java.io.Serializable {
    private World world;
    private TETile buddyTile;
    private TETile buddyAbove;
    private Point position;
    private int chargesCurrent;
    private int chargesNeeded;

    public Buddy(Point position, World world) {
        this.position = position;
        this.world = world;
        this.buddyAbove = world.getTile(position);
        this.buddyTile = Tileset.BUDDY;
        this.chargesCurrent = 0;
        this.chargesNeeded = 5;
    }

    /**
     * @return : Current position of the buddy.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @return : Tile representation of buddy in the world.
     */
    public TETile getTile() {
        return buddyTile;
    }

    /**
     * @return : Tile which the buddy is currently on top of.
     */
    public TETile getAbove() {
        return buddyAbove;
    }

    /**
     * Set the tile above which the buddy is.
     *
     * @param above : The tile buddy is above.
     */
    public void setAbove(TETile above) {
        buddyAbove = above;
    }

    /**
     * @return : The current number of charges.
     */
    public int getCharges() {
        return chargesCurrent;
    }

    /**
     * Add a charge to the current number of charges. If the charge count is greater
     * than or equal to the maximum number of charges, and the player still has lives,
     * demolish all of the traps in the world.
     */
    public void addCharge() {
        chargesCurrent += 1;

        if (chargesCurrent >= chargesNeeded && world.getPlayer().getLives() > 0) {
            world.demolishTraps();
        }
    }

    /**
     * @return : The charges needed for trap demolition.
     */
    public int getChargesNeeded() {
        return chargesNeeded;
    }

    /**
     * Have the buddy move so that it follows the player. The buddy should always be a distance
     * of one from the player. Thus, this method checks to see if the player is more than one tile
     * away in the x and y directions, moving that direction is so.
     */
    public void follow() {
        Point playerPos = world.getPlayer().getPosition();
        int buddyX = getPosition().getX();
        int buddyY = getPosition().getY();
        int deltaX = playerPos.getX() - buddyX;
        int deltaY = playerPos.getY() - buddyY;

        // Check if player is more than one tile away in the x direction and then move if true.
        if (Math.abs(deltaX) > 1) {
            if (Math.abs(deltaY) > 0) {
                buddyY += deltaY;
            }
            buddyX += deltaX / 2;
        }

        // Check if player is more than one tile away in the x direction and then move if true.
        if (Math.abs(deltaY) > 1) {
            if (Math.abs(deltaX) > 0) {
                buddyX += deltaX;
            }
            buddyY += deltaY / 2;
        }
        this.position = new Point(buddyX, buddyY);
    }
}
