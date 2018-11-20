package byog.Core;

import byog.TileEngine.TETile;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class for rendering structures as tiles in the world.
 */
public class Builder implements java.io.Serializable {
    private World world;
    private TETile[][] worldTiles;
    private TETile floor;
    private TETile empty;
    private TETile[] decorativeWallTiles;
    private LinkedList<Point> wallTiles;
    private Random random;

    Builder(World world) {
        this.world = world;
        this.worldTiles = world.getTileArray();
        this.floor = world.getFloor();
        this.empty = world.getEmpty();
        this.decorativeWallTiles = world.getDecorativeTiles();
        this.wallTiles = new LinkedList<>();
        this.random = world.getRandom();
    }

    /**
     * @return : All the wall tiles built so far.
     */
    public LinkedList<Point> getWallTiles() {
        return wallTiles;
    }

    /**
     * Make the structure corresponding the linked list of tiles input. This structure  may
     * be any structure within the world.
     *
     * @param tiles : The tiles making up the structure.
     * @param material : The material of which the tiles are.
     */
    private void makeStructure(LinkedList<Point> tiles, TETile material) {
        for (Point tile : tiles) {
            world.changeTile(tile, material);
        }
    }

    /**
     * Build each room in the room collection.
     *
     * @param rooms : A linked list of rooms of the world.
     */
    public void buildRooms(RoomCollection rooms) {
        for (int room = 0; room < rooms.size(); room += 1) {
            makeStructure(rooms.get(room).getTiles(), floor);
        }
    }

    /**
     * Build each hallway in the hallway collection;
     *
     * @param hallways : A linked list of hallways of the world.
     */
    public void buildHallways(HallwayCollection hallways) {
        LinkedList<Point> currentHall;

        for (int hallway = 0; hallway < hallways.size(); hallway += 1) {
            currentHall = hallways.get(hallway).getHallway();
            makeStructure(currentHall, floor);
        }
    }

    /**
     * Build the walls of the world. This checks each tile in the world to see whether
     * it is an empty tile or a floor tile. If it is a floor tile, a random type of wall
     * tile is assigned, and then all adjacent tiles are checked. If an adjacent tile is
     * an empty tile, it is changed to a wall.
     */
    public void buildWalls() {
        for (int row = 1; row < worldTiles.length - 1; row += 1) {
            for (int col = 1; col < worldTiles[0].length - 1; col += 1) {
                String tileDesrip = world.getTile(new Point(row, col)).description();
                if (!tileDesrip.equals(empty.description())) {

                    Point[] checkPoints = new Point[]{new Point(row + 1, col),
                        new Point(row - 1, col),
                        new Point(row, col + 1),
                        new Point(row, col - 1),
                        new Point(row + 1, col + 1),
                        new Point(row - 1, col + 1),
                        new Point(row + 1, col - 1),
                        new Point(row - 1, col - 1)};

                    // All adjacent tiles are checked to see if empty and then assigned.
                    for (Point checkPoint : checkPoints) {
                        if (world.getTile(checkPoint).equals(empty)) {
                            int randTile = random.nextInt(decorativeWallTiles.length);

                            world.changeTile(checkPoint, decorativeWallTiles[randTile]);
                            wallTiles.add(checkPoint);
                        }
                    }
                }
            }
        }
    }

    /**
     * Place traps within the world's rooms in a random manner.
     *
     * @param frequency : The frequency of trap placement. A frequency of one is every tile,
     *                    while a frequency of zero is no tiles.
     */
    public void placeTraps(double frequency) {
        for (Point tile : world.getRoomCollection().getFloorTiles()) {
            if (world.getRandom().nextDouble() < frequency) {
                world.addTrap(tile);
            }
        }
    }

    /**
     * Place energys in the world in a random manner. Their frequency corresponds with the
     * frequency number given. If the random number is lower than frequency (between 0 and 1),
     * then palce an energy tile.
     *
     * @param frequency : The wanted frequency of energy tiles.
     */
    public void placeEnergy(double frequency) {
        for (Point tile : world.getRoomCollection().getFloorTiles()) {
            if (world.getRandom().nextDouble() < frequency) {
                world.addEnergy(tile);
            }
        }
    }

    /**
     * Demolish all of the traps in the world. Replace them with floor tiles.
     *
     * @param traps : The list of traps to be demolished.
     */
    public void demolishAllTraps(LinkedList<Point> traps) {
        for (Point trapTile : traps) {
            world.changeTile(trapTile, floor);
        }
    }

    /**
     * Demolish all of the energys in the world. Replace them with floor tiles.
     *
     * @param energys : The list of energys to be demolished.
     */
    public void demolishAllEnergys(LinkedList<Point> energys) {
        for (Point energyTile : energys) {
            world.changeTile(energyTile, floor);
        }
    }
}
