package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.LinkedList;

/**
 * This class represents the game environment. The World class represents the underlying
 * structure of the game world. However, it is more of a generator and blueprint. This class
 * is meant to interact with the user interface classes, allowing for the modification
 * of the world's aesthetic, as well as retrieval and storage of information of individual
 * tiles, such as, brightness.
 */
public class Environment implements java.io.Serializable {
    private static final TETile FLOORDARK = Tileset.FLOORD;
    private static final TETile WALL1DARK = Tileset.WALL1D;
    private static final TETile WALL2DARK = Tileset.WALL2D;
    private static final TETile WALL3DARK = Tileset.WALL3D;
    private static final TETile WALL4DARK = Tileset.WALL4D;
    private static final TETile WALL5DARK = Tileset.WALL5D;
    private static final TETile[] DECORATIVEWALLSDARK = new TETile[]{WALL1DARK,
        WALL2DARK,
        WALL3DARK,
        WALL4DARK,
        WALL5DARK};
    private static final TETile FLOORDIGITAL = Tileset.FLOORV;
    private static final TETile WALLDIGITAL = Tileset.WALLV;
    private static final TETile PLAYERDIGITAL = Tileset.PLAYERV;
    private static final TETile BUDDYDIGITAL = Tileset.BUDDYV;
    private static final TETile STATICFLOOR1 = Tileset.STATIC1;
    private static final TETile STATICFLOOR2 = Tileset.STATIC2;
    private static final TETile STATICFLOOR3 = Tileset.STATIC3;
    private static final TETile STATICFLOOR4 = Tileset.STATIC4;
    private static final TETile STATICFLOOR5 = Tileset.STATIC5;

    private World world;
    private TETile[][] displayWorld;
    private TETile[][] virtualWorld;
    private TETile[][] currentWorld;
    private TETile[][] alternateWorld;
    private int[][] seenBrightness;
    private int worldWidth;
    private int worldHeight;

    Environment(World newWorld) {
        this.world  = newWorld;
        this.worldWidth = world.getWidth();
        this.worldHeight = world.getHeight();

        this.displayWorld = new TETile[worldWidth][worldHeight];
        this.virtualWorld = new TETile[worldWidth][worldHeight];
        this.seenBrightness = new int[worldWidth][worldHeight];
        this.currentWorld = displayWorld;
        this.alternateWorld = virtualWorld;

        // Initialize the display world, virtual world, and brightness array.
        for (int x = 0; x < worldWidth; x += 1) {
            for (int y = 0; y < worldHeight; y += 1) {
                displayWorld[x][y] = world.getEmpty();
                virtualWorld[x][y] = world.getEmpty();
                seenBrightness[x][y] = 0;
            }
        }
    }

    /**
     * @return : The width of the environment.
     */
    public int getWidth() {
        return worldWidth;
    }

    /**
     * @return : The height of the environment.
     */
    public int getHeight() {
        return worldHeight;
    }

    /**
     * @return : The world object that the environment is based on.
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return : The player object in the world.
     */
    public Player getPlayer() {
        return world.getPlayer();
    }

    /**
     * @return : The world that the player is currently in.
     */
    public TETile[][] getCurrentWorld() {
        return currentWorld;
    }

    /**
     * @return : The world that the player is not currently in.
     */
    public TETile[][] getAlternateWorld() {
        return alternateWorld;
    }

    /**
     * Switch the world that the player is in.
     */
    public void switchWorld() {
        world.getPlayer().switchWorlds();

        if (currentWorld.equals(virtualWorld)) {
            currentWorld = displayWorld;
            alternateWorld = virtualWorld;
        } else {
            currentWorld = virtualWorld;
            alternateWorld = currentWorld;
        }
    }

    /**
     * Calculate the two environments.
     */
    public void calcEnvironments() {
        calcVirtualWorld();
        calcSeenWorld();
    }

    /**
     * Calculate the world visible from the player's tile. Any new tiles that can be seen
     * will have their brightness adjusted to level 2. Also, any previously visible tiles
     * will have their brightness adjusted to level 1.
     */
    private void calcSeenWorld() {
        TETile currentTile;

        for (int x = 0; x < worldWidth; x += 1) {
            for (int y = 0; y < worldHeight; y += 1) {
                currentTile = world.getTile(new Point(x, y));

                // Set all empty tile to empty.
                if (currentTile.equals(world.getEmpty())) {
                    displayWorld[x][y] = world.getEmpty();

                // Find all visible tiles and set them to their corresponding world tile.
                } else if (isVisible(new Point(x, y))) {
                    if (currentTile.equals(world.getEnergy())) {
                        displayWorld[x][y] = world.getFloor();
                    } else {
                        displayWorld[x][y] = currentTile;
                    }
                    seenBrightness[x][y] = 2;

                // The rest of the tiles are not visible. Find all that have been seen and set
                // them. The rest are empty.
                } else {
                    // The tile was seen, but now is not.
                    if (seenBrightness[x][y] == 2) {
                        seenBrightness[x][y] = 1;
                    }

                    // These tiles have been seen, but are not visible.
                    if (seenBrightness[x][y] == 1) {
                        String descrip = currentTile.description();
                        if (descrip.equals(world.getFloor().description())) {
                            displayWorld[x][y] = FLOORDARK;
                        } else if (descrip.equals(world.getTrap().description())) {
                            displayWorld[x][y] = FLOORDARK;
                        } else if (descrip.equals(world.getEnergy().description())) {
                            displayWorld[x][y] = FLOORDARK;

                        } else {
                            for (int i = 0; i < world.getDecorativeTiles().length; i += 1) {
                                if (descrip.equals(world.getDecorativeTiles()[i].description())) {
                                    displayWorld[x][y] = DECORATIVEWALLSDARK[i];
                                }
                            }
                        }
                    // These tiles have yet to be seen.
                    } else {
                        displayWorld[x][y] = world.getEmpty();
                    }
                }
            }
        }
    }

    /**
     * Calculate the world visible from the player's tile. Any new tiles that can be seen
     * will have their brightness adjusted to level 2. Also, any previously visible tiles
     * will have their brightness adjusted to level 1.
     */
    private void calcVirtualWorld() {
        TETile currentTile;

        for (int x = 0; x < worldWidth; x += 1) {
            for (int y = 0; y < worldHeight; y += 1) {
                currentTile = world.getTile(new Point(x, y));

                // Set all empty tiles to static.
                if (currentTile.equals(world.getEmpty())) {
                    //virtualWorld[x][y] = randomStatic();
                    virtualWorld[x][y] = world.getEmpty();

                // Find all visible tiles and set them to their corresponding digital tiles.
                } else if (isVisible(new Point(x, y))) {
                    String currDescrip = currentTile.description();
                    String playerDescrip = world.getPlayer().getTile().description();
                    String buddyDescrip = world.getPlayer().getBuddy().getTile().description();
                    if (currDescrip.equals(world.getFloor().description())) {
                        virtualWorld[x][y] = randomStatic();
                    } else if (currDescrip.equals(playerDescrip)) {
                        virtualWorld[x][y] = PLAYERDIGITAL;
                    } else if (currDescrip.equals(buddyDescrip)) {
                        virtualWorld[x][y] = BUDDYDIGITAL;
                    } else if (currDescrip.equals(world.getTrap().description())) {
                        virtualWorld[x][y] = FLOORDIGITAL;
                    } else if (currDescrip.equals(world.getEnergy().description())) {
                        virtualWorld[x][y] = world.getEnergy();
                    } else {
                        virtualWorld[x][y] = WALLDIGITAL;
                    }

                // The rest of the tiles are not seen. Set them to static.
                } else {
                    //virtualWorld[x][y] = randomStatic();
                    virtualWorld[x][y] = world.getEmpty();
                }
            }
        }
    }

    /**
     * @return : A random static floor tile for the digital world.
     */
    private TETile randomStatic() {
        int randomTile = world.getRandom().nextInt(5);
        TETile[] randomStaticFloors = new TETile[]{STATICFLOOR1,
            STATICFLOOR2, STATICFLOOR3, STATICFLOOR4, STATICFLOOR5};

        return randomStaticFloors[randomTile];
    }

    /**
     * Checks to see if a given tile is visible by player. This is done by constructing a
     * Bresenham line from the given tile to the player tile. Working along this line, it is
     * checked whether interior tiles are all floor.
     *
     * @param tile : The tile to check whether visible by player.
     * @return : Whether the tile is visible.
     */
    private boolean isVisible(Point tile) {
        int x;
        int y;

        Point playerPos = world.getPlayer().getPosition();
        LinkedList<Point> line = new BresenhamLine(playerPos, tile).plotLine();
        boolean tileVisible = true;

        for (int i = 1; i < line.size() - 1; i += 1) {
            x = line.get(i).getX();
            y = line.get(i).getY();

            if (world.getTileArray()[x][y].equals(world.getWall())) {
                tileVisible = false;
                break;
            }
        }
        return tileVisible;
    }
}
