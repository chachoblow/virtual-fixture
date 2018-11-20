package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class deals with the animation between worlds in the game.
 */
public class Animation implements java.io.Serializable {

    private Environment environment;
    private World world;
    private TERenderer tileRender;
    private TETile[][] currentWorld;
    private TETile[][] otherWorld;
    private int[][] stepArray;
    private int maxStep;
    private boolean digital;

    Animation(Environment env, TERenderer ter) {
        this.environment = env;
        this.world = environment.getWorld();
        this.tileRender = ter;
        this.stepArray = new int[world.getWidth()][world.getHeight()];
        this.currentWorld = environment.getCurrentWorld();
        this.otherWorld = environment.getAlternateWorld();
        this.maxStep = 0;
        this.digital = false;
    }

    /**
     * The main method for performing the animation.
     */
    public void transitionWorld() {
        // For each tile, calculate the number of steps to the nearest wall.
        calcSteps();

        LinkedList<Point> currentStep;

        for (int i = 0; i <= maxStep; i += 1) {
            // Get all tiles with the current step count.
            currentStep = getStepTiles(i);

            // If the world is currently digital.
            if (digital) {

                // For each tile in the current step count, change it to a random analog
                // transition tile.
                for (Point tile : currentStep) {
                    currentWorld[tile.getX()][tile.getY()] = getRandomAnalog();
                }

                // Show the random analog transition tiles.
                tileRender.renderFrame(currentWorld);
                StdDraw.show();
                StdDraw.pause(i * i / 3);

            } else {
                // The world is currently analog. For each tile in the current step count, change
                // it to the digital transition tile.
                for (Point tile : currentStep) {
                    currentWorld[tile.getX()][tile.getY()] = Tileset.TODIGITAL;
                }

                // Show the digital transition tiles.
                tileRender.renderFrame(currentWorld);
                StdDraw.show();
                StdDraw.pause(i * i / 3);

            }

            // The transition tiles have been shown, now change the current step count tiles
            // to their appropriate other world tiles.
            for (Point tile : currentStep) {
                currentWorld[tile.getX()][tile.getY()] = otherWorld[tile.getX()][tile.getY()];
            }

            // Show the new world tiles for this step count.
            tileRender.renderFrame(currentWorld);
            StdDraw.show();
            StdDraw.pause(i * i / 3);
        }

        // The transition has been made. Switch current/other and digital/analog.
        TETile[][] temp = currentWorld;
        currentWorld = otherWorld;
        otherWorld = temp;
        digital = !digital;
    }

    /**
     * This class the number of steps from each wall of the world. This is used later one
     * to perform the animation. It gives an ordering of tiles based on their distance
     * from a wall, i.e., then number of steps from a tile to the nearest wall.
     */
    private void calcSteps() {
        // Initialize all steps to 1000.
        for (int x = 0; x < world.getWidth(); x += 1) {
            for (int y = 0; y < world.getHeight(); y += 1) {
                stepArray[x][y] = 10000;
            }
        }

        LinkedList<Point> nonEmptyTiles = environment.getWorld().getWallTiles();

        // Set all of the wall tiles to 0.
        for (Point tile : nonEmptyTiles) {
            stepArray[tile.getX()][tile.getY()] = 0;
        }

        boolean changeMade = true;
        int minValue;

        // Go through the every point in the stepArray. Find the cardinal neighbor with
        // the smallest integer value. If this value is 2 or more greater than the original
        // value, set the original point to the small value plus one. Continue this pattern
        // until the stepArray is gone through and no changes are made.
        while (changeMade) {
            Point[] checkPoints;
            changeMade = false;

            for (int x = 1; x < world.getWidth() - 1; x += 1) {
                for (int y = 1; y < world.getHeight() - 1; y += 1) {
                    minValue = 1000;

                    // Cardinal neighbors.
                    checkPoints = new Point[]{
                        new Point(x + 1, y),
                        new Point(x - 1, y),
                        new Point(x, y + 1),
                        new Point(x, y - 1)};

                    // Find the cardinal neighbor with least value.
                    for (Point checkPoint : checkPoints) {
                        if (stepArray[checkPoint.getX()][checkPoint.getY()] < minValue) {
                            minValue = stepArray[checkPoint.getX()][checkPoint.getY()];
                        }
                    }

                    // Set the current place to the least value plus one.
                    if (stepArray[x][y] - minValue > 1) {
                        stepArray[x][y] = minValue + 1;
                        maxStep = minValue + 1;
                        changeMade = true;
                    }
                }
            }
        }
    }

    /**
     * Retrieve all tiles with the given step count.
     *
     * @param step : The step count to retrieve.
     * @return : All tiles with the given step count.
     */
    private LinkedList<Point> getStepTiles(int step) {
        LinkedList<Point> steps = new LinkedList<>();

        for (int x = 0; x < world.getWidth(); x += 1) {
            for (int y = 0; y < world.getHeight(); y += 1) {
                if (stepArray[x][y] == step) {
                    steps.add(new Point(x, y));
                }
            }
        }
        return steps;
    }

    /**
     * @return : A random transition tile for switching to analog.
     */
    private TETile getRandomAnalog() {
        Random random = world.getRandom();
        TETile[] tiles = new TETile[]{
            Tileset.TOANALOG1,
            Tileset.TOANALOG2,
            Tileset.TOANALOG3,
            Tileset.TOANALOG4,
            Tileset.TOANALOG5,
            Tileset.TOANALOG6};

        return tiles[random.nextInt(6)];
    }
}
