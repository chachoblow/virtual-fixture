package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game {
    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;
    private static final int HUDWIDTH = 14;
    private static final int HUDHEIGHT = 0;
    private final String filename = "world.txt";

    /**DOM.nextInt(grid[row][col].x2 - grid[row][col].x1) + grid[row][col].x1;
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        char userOption;
        long userSeed;

        boolean validOption = false;

        ter.initialize(WIDTH + HUDWIDTH, HEIGHT + HUDHEIGHT, HUDWIDTH, HUDHEIGHT);

        // Show start menu and get the user option to start, load, or quit.
        StartMenu startMenu = new StartMenu(WIDTH + HUDWIDTH, HEIGHT + HUDHEIGHT);
        startMenu.introScreen();
        userOption = startMenu.getUserOption();

        // While a valid input has not been given, check for valid inputs.
        while (!validOption) {
            // The user wishes to start a new game.
            if (userOption == 'N') {
                validOption = true;

                // Get the seed for the new game from the user.
                userSeed = startMenu.getUserSeed();

                // Initialize a new game environment.
                Environment environment = new Environment(new World(WIDTH, HEIGHT, userSeed));

                // Run the game and serialize after user has quit.
                runGame(environment);
                serializeWorld(environment);

            // The user wished to load the last saved game.
            } else if (userOption == 'L') {
                validOption = true;

                // Load the last saved game.
                Environment environmentLoad = deserializeWorld();

                // If there was a saved game.
                if (environmentLoad != null) {

                    //Run the game and save if the user has quit.
                    runGame(environmentLoad);
                    serializeWorld(environmentLoad);
                }

            // The player wishes to quit.
            } else if (userOption == 'Q') {
                validOption = true;
            }
        }
        System.exit(0);
    }

    /**
     * While the game should be run (the user hasn't quit), continue to retrieve user input
     * and take appropriate actions. As to what these actions are, see the method
     * getInputAndMove.
     *
     * @param environment : The world in which the game is run.
     */
    private void runGame(Environment environment) {
        HUD hud = new HUD(environment, HUDWIDTH, HUDHEIGHT);
        GUI userInterface = new GUI(hud, environment, ter);
        boolean gameRunning = true;

        while (gameRunning) {
            gameRunning = getInputAndMove(environment.getWorld(), userInterface);
        }
    }

    /**
     * Get the user input and take the corresponding action in the world. For now, the means
     * either move the player (up, left, down, or right) or save and quit.
     *
     * @param world : The world in which the player acts.
     * @param userInterface : The object for the GUI which displays everything for user.
     * @return : Whether the game should continue to run or not.
     */
    private boolean getInputAndMove(World world, GUI userInterface) {
        char userInput;
        Point newPosition;

        final char moveUp = 'W';
        final char moveLeft = 'A';
        final char moveDown = 'S';
        final char moveRight = 'D';
        final char[] quitSequence = {':', 'Q'};
        boolean validOption = false;
        boolean gameRunning = true;
        boolean quitSeqStarted = false;

        // Continue to wait for valid user input.
        while (!validOption) {

            userInterface.displayGUI();

            if (StdDraw.hasNextKeyTyped()) {
                userInput = Character.toUpperCase(StdDraw.nextKeyTyped());

                if (userInput == moveUp) {
                    newPosition = new Point(world.getPlayer().getPosition().getX(),
                            world.getPlayer().getPosition().getY() + 1);
                    if (world.getPlayer().canMoveUp()) {
                        world.movePlayer(newPosition);
                    }
                    validOption = true;

                } else if (userInput == moveLeft) {
                    newPosition = new Point(world.getPlayer().getPosition().getX() - 1,
                            world.getPlayer().getPosition().getY());
                    if (world.getPlayer().canMoveLeft()) {
                        world.movePlayer(newPosition);
                    }
                    validOption = true;

                } else if (userInput == moveDown) {
                    newPosition = new Point(world.getPlayer().getPosition().getX(),
                            world.getPlayer().getPosition().getY() - 1);
                    if (world.getPlayer().canMoveDown()) {
                        world.movePlayer(newPosition);
                    }
                    validOption = true;

                } else if (userInput == moveRight) {
                    newPosition = new Point(world.getPlayer().getPosition().getX() + 1,
                            world.getPlayer().getPosition().getY());
                    if (world.getPlayer().canMoveRight()) {
                        world.movePlayer(newPosition);
                    }
                    validOption = true;

                } else if (userInput == ' ') {
                    userInterface.switchWorlds();

                } else if (userInput == quitSequence[0]) {
                    quitSeqStarted = true;

                } else if (userInput == quitSequence[1] && quitSeqStarted) {
                    gameRunning = false;
                    validOption = true;
                }
            }
        }
        return gameRunning;
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        char[] inputChars = input.toCharArray();
        char menuChoice = Character.toUpperCase(inputChars[0]);
        long userSeed = 0;
        TETile[][]finalWorldFrame = null;

        boolean saveGame;

        int index = 1;

        // The input string dictates to start a new game.
        if (menuChoice == 'N') {

            // Retrieve the seed from the input string.
            while (Character.isDigit(inputChars[index])) {
                userSeed = userSeed * 10 + inputChars[index];
                index += 1;
            }

            index += 1;

            // Initialize the new environment.
            Environment environmentNew = new Environment(new World(WIDTH, HEIGHT, userSeed));

            // Perform the actions of the input string in the game.
            saveGame = parseInputAndMove(environmentNew.getWorld(), inputChars, index);

            if (saveGame) {
                serializeWorld(environmentNew);
            }

            finalWorldFrame = environmentNew.getWorld().getTileArray();

        // The input string dictates to load the last saved game.
        } else if (menuChoice == 'L') {
            Environment environmentLoad = deserializeWorld();

            // If there was a last saved game.
            if (environmentLoad != null) {
                saveGame = parseInputAndMove(environmentLoad.getWorld(), inputChars, index);

                if (saveGame) {
                    serializeWorld(environmentLoad);
                }

                finalWorldFrame = environmentLoad.getWorld().getTileArray();
            }
        }
        return finalWorldFrame;
    }

    /**
     * This methods performs the actions dictated by the input string. This string relates
     * to actions allowed by the player in the world.
     *
     * @param world : The world of the game.
     * @param input : The input string.
     * @param startIndex : The index of the input string to start at.
     * @return : Whether the user requested to save.
     */
    private boolean parseInputAndMove(World world, char[] input, int startIndex) {
        char currentInput;
        Point newPosition;

        final char moveUp    = 'W';
        final char moveLeft  = 'A';
        final char moveDown  = 'S';
        final char moveRight = 'D';
        final char[] quitSequence = {':', 'Q'};
        boolean quitSeqStarted = false;
        boolean toSave = false;

        for (int index = startIndex; index < input.length; index += 1) {
            currentInput = Character.toUpperCase(input[index]);

            if (currentInput == moveUp) {
                newPosition = new Point(world.getPlayer().getPosition().getX(),
                        world.getPlayer().getPosition().getY() + 1);
                if (world.getPlayer().canMoveUp()) {
                    world.movePlayer(newPosition);
                }
                quitSeqStarted = false;

            } else if (currentInput == moveLeft) {
                newPosition = new Point(world.getPlayer().getPosition().getX() - 1,
                        world.getPlayer().getPosition().getY());
                if (world.getPlayer().canMoveLeft()) {
                    world.movePlayer(newPosition);
                }
                quitSeqStarted = false;

            } else if (currentInput == moveDown) {
                newPosition = new Point(world.getPlayer().getPosition().getX(),
                        world.getPlayer().getPosition().getY() - 1);
                if (world.getPlayer().canMoveDown()) {
                    world.movePlayer(newPosition);
                }
                quitSeqStarted = false;

            } else if (currentInput == moveRight) {
                newPosition = new Point(world.getPlayer().getPosition().getX() + 1,
                        world.getPlayer().getPosition().getY());
                if (world.getPlayer().canMoveRight()) {
                    world.movePlayer(newPosition);
                }
                quitSeqStarted = false;

            } else if (currentInput == quitSequence[0]) {
                quitSeqStarted = true;

            } else if (currentInput == quitSequence[1] && quitSeqStarted) {
                toSave = true;
                break;
            }
        }
        return toSave;
    }

    /**
     * Serialize, i.e., save the world.
     */
    private void serializeWorld(Environment environment) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(environment);

            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught in serialize.");
            ex.printStackTrace();
        }
    }

    /**
     * Deserialize, i.e., load the world. If not save file, game terminates with no error.
     *
     * @return : Then environment to load.
     */
    private Environment deserializeWorld() {
        Environment environmentLoad = null;

        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            environmentLoad = (Environment) in.readObject();

            in.close();
            file.close();
        } catch (IOException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught.");
            ex.printStackTrace();
        }
        return environmentLoad;
    }
}
