package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * A class representing a player in the world. Players can see whether they can move,
 * move if able, give their position, and keep count of their lives.
 */
public class Player implements java.io.Serializable {
    private World world;
    private TETile[][] worldTiles;
    private TETile playerTile;
    private TETile playerAbove;
    private Point position;
    private Buddy buddy;
    private int lives;
    private boolean digital;

    public Player(Point position, World world) {
        this.world = world;
        this.position = position;
        this.worldTiles = world.getTileArray();
        this.playerTile = Tileset.PLAYER;
        this.playerAbove = worldTiles[position.getX()][position.getY()];
        this.buddy = new Buddy(new Point(position.getX() + 1, position.getY()), world);
        this.lives = 5;
        this.digital = false;
    }

    /**
     * @return : The current position of the player.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @return : The current tile of the player.
     */
    public TETile getTile() {
        return playerTile;
    }

    /**
     * @return : The current tile which the player is above.
     */
    public TETile getAbove() {
        return playerAbove;
    }

    /**
     * Set the tile over which the player is.
     *
     * @param above : The tile which the player is over.
     */
    public void setAbove(TETile above) {
        playerAbove = above;
    }

    /**
     * Move the player to the given position.
     *
     * @param pos : Point to which the player should move.
     */
    public void move(Point pos) {
        this.position = pos;
        setAbove(world.getTile(pos));
    }

    /**
     * @return : Whether the player can move up.
     */
    public boolean canMoveUp() {
        Point upTile = new Point(position.getX(), position.getY() + 1);
        return !world.getTile(upTile).equals(world.getWall());
    }

    /**
     * @return : Whether the player can move left.
     */
    public boolean canMoveLeft() {
        Point leftTile = new Point(position.getX() - 1, position.getY());
        return !world.getTile(leftTile).equals(world.getWall());
    }

    /**
     * @return : Whether the player can move down.
     */
    public boolean canMoveDown() {
        Point downTile = new Point(position.getX(), position.getY() - 1);
        return !world.getTile(downTile).equals(world.getWall());
    }

    /**
     * @return : Whether the player can move right.
     */
    public boolean canMoveRight() {
        Point rightTile = new Point(position.getX() + 1, position.getY());
        return !world.getTile(rightTile).equals(world.getWall());
    }

    /**
     * @return : The current number of lives of the player.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Decrement the number of lives of the player. If the number of lives is zero or less,
     * change the player's tile to the one representing their death.
     */
    public void decrementLives() {
        lives -= 1;

        if (lives < 1) {
            world.demolishEnergys();
        }
    }

    /**
     * @return : The buddy of the player.
     */
    public Buddy getBuddy() {
        return buddy;
    }

    /**
     * Switch the boolean value relating to the current world of the player.
     */
    public void switchWorlds() {
        digital = !digital;
    }

    /**
     * @return : Whether the current world of the player is digital or not.
     */
    public boolean isDigital() {
        return digital;
    }
}
