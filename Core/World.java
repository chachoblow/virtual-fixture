
package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class generates and builds a pseudo-random world. This pseudo-random world is based upon
 * a seed entered by the user at game start. Within this method, rooms are generated, then
 * hallways, and then finally a player.
 *
 * The rooms are randomly placed within the world space for a certain number of random attempts.
 * If a room intersects an already placed room, it is discarded. These rooms are randomly sized,
 * with smaller rooms preferred over larger ones.
 *
 * Once the rooms are placed, hallways are generated. The rooms are ordered based on their distance
 * from the first room placed. Then, hallways are generated, connecting this first room to the next
 * closest, and then that room to its next closest, and so on. This assures that all rooms are
 * connected with minimal crossover of hallways and rooms.
 *
 * With rooms and hallways generated and built, walls are built. Walls are simply places where a
 * floor tile (placed during the preceding operations) meets and empty tile.
 *
 * Finally, the user is placed at the center of the first room generated, with its buddy next to
 * them.
 *
 * Sources:
 *   @Source : https://gamedevelopment.tutsplus.com/tutorials/create-a-procedurally
 *              -generated-dungeon-cave-system--gamedev-10099
 *   @Source : http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/
 *   @Source : http://www.roguebasin.com/index.php?title=Roguelike_Dev_FAQ
 *
 *   The three above sources were used to create this class. I first made attempts to generate the
 *   world on my own by sectioning the grid into a larger grid (such as 3x8) and then placing a
 *   randomly sized room in each of these larger grids. I then connected each room horizontally,
 *   with randomly placed vertical connections.
 *
 *   This worked, but looked too uniform. The grid was clearly seen within the randomness. After
 *   trying a few different things, I finally utilized the resources above.
 */
public class World implements java.io.Serializable {
    private static final TETile FLOOR = Tileset.FLOOR;
    private static final TETile WALL  = Tileset.WALL;
    private static final TETile EMPTY = Tileset.NOTHING;
    private static final TETile TRAP = Tileset.TRAP;
    private static final TETile ENERGY = Tileset.ENERGY;
    private static final TETile WALL1 = Tileset.WALL1;
    private static final TETile WALL2 = Tileset.WALL2;
    private static final TETile WALL3 = Tileset.WALL3;
    private static final TETile WALL4 = Tileset.WALL4;
    private static final TETile WALL5 = Tileset.WALL5;
    private static final TETile[] DECORATIVETILES = new TETile[]{WALL1,
        WALL2, WALL3, WALL4, WALL5};

    private TETile[][] world;
    private RoomCollection rooms;
    private HallwayCollection hallways;
    private Builder build;
    private Player player;
    private Trap trap;
    private Energy energy;
    private int width;
    private int height;
    private Random random;

    World(int width, int height, long seed) {
        this.width  = width;
        this.height = height;
        this.random = new Random(seed);
        this.world = new TETile[width][height];
        this.build = new Builder(this);
        this.trap = new Trap(TRAP);
        this.energy = new Energy(ENERGY);

        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                changeTile(new Point(x, y), EMPTY);
            }
        }

        generateRooms();
        generateHallways();
        generateWalls();
        build.placeTraps(0.25);
        build.placeEnergy(0.01);
        generatePlayer();
    }

    /**
     * @return : The world's tile array.
     */
    public TETile[][] getTileArray() {
        return world;
    }

    /**
     * @return : The linked list of all rooms in the world.
     */
    public RoomCollection getRoomCollection() {
        return rooms;
    }

    public LinkedList<Point> getWallTiles() {
        return build.getWallTiles();
    }

    /**
     * @return : The tile representing floors.
     */
    public TETile getFloor() {
        return FLOOR;
    }

    /**
     * @return : The tile representing walls.
     */
    public TETile getWall() {
        return WALL;
    }

    /**
     * @return : The tile representing an empty space.
     */
    public TETile getEmpty() {
        return EMPTY;
    }

    public TETile getTrap() {
        return TRAP;
    }

    public TETile getEnergy() {
        return ENERGY;
    }

    public TETile[] getDecorativeTiles() {
        return DECORATIVETILES;
    }

    /**
     * @return : The player object in the world.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return : The width of the world.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return : The height of the world.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return : The random object whose seed was created by the user.
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Generate all rooms for the world. For a description of how this is done, see the
     * class informational heading.
     */
    private void generateRooms() {
        int roomWidth, roomHeight, roomPosX, roomPosY, minDimension, maxDimension;
        double rand;

        rooms = new RoomCollection();
        int maxAttempts = 2500;

        // Randomly place rooms in the world. Based on a random number between 0 and 1,
        // each room is made either small, medium, or large. If this new room does not intersect
        // with any previously placed rooms, add the room to the overall collection of rooms, and
        // generate the room.
        for (int i = 0; i < maxAttempts; i += 1) {
            rand = random.nextDouble();

            if (rand < 0.2) {
                minDimension = 4;
                maxDimension = 6;
            } else if (rand < 0.4) {
                minDimension = 6;
                maxDimension = 8;
            } else {
                minDimension = 7;
                maxDimension = 12;
            }

            roomWidth  = minDimension + random.nextInt(maxDimension - minDimension + 1);
            roomHeight = minDimension + random.nextInt(maxDimension - minDimension + 1);
            roomPosX = random.nextInt(width - roomWidth - 1) + 1;
            roomPosY = random.nextInt(height - roomHeight - 1) + 1;

            Room newRoom = new Room(roomPosX, roomPosY, roomWidth, roomHeight);
            boolean intersects = false;

            for (int j = 0; j < rooms.size(); j += 1) {
                if (rooms.get(j).intersects(newRoom)) {
                    intersects = true;
                    break;
                }

                // Ensure rooms are offset.
                if (rooms.get(j).getCenter().getX() == newRoom.getCenter().getX()
                        || rooms.get(j).getCenter().getY() == newRoom.getCenter().getY()) {
                    intersects = true;
                    break;
                }
            }

            if (!intersects) {
                rooms.add(newRoom);
            }

            rooms.sortByDistance();
            build.buildRooms(rooms);
        }
    }

    /**
     * Generate all the hallways for the world.
     */
    private void generateHallways() {
        hallways = new HallwayCollection();

        for (int room = 0; room < rooms.size() - 1; room += 1) {
            Room current = rooms.get(room);
            Room neighbor = rooms.get(room + 1);
            hallways.add(new Hallway(current.getCenter(), neighbor.getCenter()));
        }
        build.buildHallways(hallways);
    }

    private void generateWalls() {
        build.buildWalls();
    }

    /**
     * Generate and place the player in the world.
     */
    private void generatePlayer() {
        Point playerPos = rooms.get(0).getCenter();
        player = new Player(playerPos, this);
        changeTile(playerPos, player.getTile());
        changeTile(player.getBuddy().getPosition(), player.getBuddy().getTile());
    }

    /**
     * Move the player in the world based on the user input.
     *
     * @param newPosition : The new position of the player.
     */
    public void movePlayer(Point newPosition) {
        Buddy playerBuddy = player.getBuddy();
        Point oldPosition = player.getPosition();
        TETile oldAbove = player.getAbove();

        // Check if the new tile is a trap. If so, decrement the player's lives.
        if (getTile(newPosition).equals(trap.getTile())) {
            player.decrementLives();
            changeTile(newPosition, getFloor());
        } else if (getTile(newPosition).equals(energy.getTile()) && player.isDigital()) {
            playerBuddy.addCharge();
            changeTile(newPosition, getFloor());
        }

        // Move the player to their new position.
        player.move(newPosition);
        changeTile(oldPosition, oldAbove);
        changeTile(newPosition, player.getTile());

        // Now we move the buddy.
        oldPosition = playerBuddy.getPosition();
        oldAbove = playerBuddy.getAbove();

        playerBuddy.follow();
        Point buddyPos = playerBuddy.getPosition();
        changeTile(oldPosition, oldAbove);
        playerBuddy.setAbove(getTile(buddyPos));
        changeTile(buddyPos, playerBuddy.getTile());
    }

    /**
     * Add a trap to the given position.
     *
     * @param position : The position to place the trap.
     */
    public void addTrap(Point position) {
        changeTile(position, trap.getTile());
        trap.addTrap(position);
    }

    /**
     * Add an energy to the given position.
     *
     * @param position : The position to add the energy.
     */
    public void addEnergy(Point position) {
        changeTile(position, energy.getTile());
        energy.addEnergy(position);
    }

    /**
     * Demolish all of the traps in the world and replace them with floor tiles.
     */
    public void demolishTraps() {
        build.demolishAllTraps(trap.getAllTraps());
        trap.removeAllTraps();
    }

    /**
     * Demolish all of the energys in the world and replace them with floor tiles.
     */
    public void demolishEnergys() {
        build.demolishAllEnergys(energy.getAllEnergys());
        energy.removeAllEnergys();
    }

    /**
     * Change the tile at the given position to the given tile.
     *
     * @param position : The position to change.
     * @param newTile : The new tile.
     */
    public void changeTile(Point position, TETile newTile) {
        world[position.getX()][position.getY()] = newTile;
    }

    /**
     * Retrieve the tile at the given position.
     *
     * @param position : The position of the wanted tile.
     * @return : The tile at the given position.
     */
    public TETile getTile(Point position) {
        return world[position.getX()][position.getY()];
    }
}
