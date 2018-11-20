package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

/**
 * This class deals with all things regarding the start menu. This includes: displaying the menu
 * and retrieving user input for the menu. Within this menu, a player can choose to start a new
 * game, load a previous game, or quit. If a new game is selected, a player is then prompted to
 * enter a seed. With this seed entered, a new game begins. If a player chooses to load a previous
 * game, if one exists, it is loaded. Otherwise, the game quits.
 */
public class StartMenu implements java.io.Serializable {
    private int width;
    private int height;
    private Font titleFont;
    private Font optionFont;

    StartMenu(int width, int height) {
        this.width = width;
        this.height = height;
        titleFont = new Font("Monospaced", Font.BOLD, 30);
        optionFont = new Font("Monospaced", Font.PLAIN, 20);

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Display the intro screen to the game. This gives the player 3 options: start game,
     * load game, and quit game.
     */
    public void introScreen() {
        String newGame = "New Game (N)";
        String loadGame = "Load Game (L)";
        String saveGame = "Quit (Q)";
        String picFileName = "StartMenuBackground.png";
        Color backgroundColor = new Color(18, 48, 59);
        Color titleColor = new Color(189, 194, 160);
        Color optionColor = new Color(165, 115, 115);

        // Draw the picture for the background of the menu.
        StdDraw.clear(backgroundColor);
        StdDraw.picture(width / 2, height / 2, picFileName, width, height);

        // Draw a box for the border of the options submenu.
        double boxCenterX = 15;
        double boxCenterY = height / 2;
        double halfBoxWidth = 11;
        double halfBoxHeight = 11;
        StdDraw.setPenColor(new Color(171, 186, 179));
        StdDraw.filledRectangle(boxCenterX, boxCenterY, halfBoxWidth, halfBoxHeight);

        // Draw the main box for the options submenu.
        halfBoxWidth = 10;
        halfBoxHeight = 10;
        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledRectangle(boxCenterX, boxCenterY, halfBoxWidth, halfBoxHeight);

        // Draw the title and submenu options.
        StdDraw.setPenColor(titleColor);
        StdDraw.setFont(titleFont);
        StdDraw.text(15, 31, "VIRTUAL");
        StdDraw.text(15, 29, "FIXTURE");
        StdDraw.setPenColor(optionColor);
        StdDraw.setFont(optionFont);
        StdDraw.text(15, 24, newGame);
        StdDraw.text(15, 22, loadGame);
        StdDraw.text(15, 20, saveGame);
        StdDraw.show();
    }

    /**
     * This methods waits for the user to press a key on the keyboard. If the key pressed
     * corresponds to one of the 3 options (start, load, or quit), then the choice is
     * logged and returned. Otherwise, the methods continues to wait.
     *
     * @return : The user's choice.
     */
    public char getUserOption() {
        char userOption = 'X';
        boolean validOption = false;
        char newGameKey  = 'N';
        char loadGameKey = 'L';
        char saveGameKey = 'Q';

        while (!validOption) {
            if (StdDraw.hasNextKeyTyped()) {
                userOption = Character.toUpperCase(StdDraw.nextKeyTyped());

                if (userOption == newGameKey
                        || userOption == saveGameKey
                        || userOption == loadGameKey) {
                    validOption = true;
                }
            }
        }
        return userOption;
    }

    /**
     * This method prompts the user for a seed to start the game. In order for the seed to be
     * logged, the user must enter 'S'. Only after this is done will the user seed be stored
     * and returned.
     *
     * @return : The new game seed determined by the user.
     */
    public long getUserSeed() {
        char userChoice;

        long userSeed = 0;
        boolean userDone = false;
        Color backgroundColor = new Color(18, 48, 59);
        Color titleColor = new Color(189, 194, 160);
        Color optionColor = new Color(165, 115, 115);

        StdDraw.clear(backgroundColor);
        StdDraw.setPenColor(titleColor);
        StdDraw.text(width * 0.5, height * 0.6, "Enter a random seed (Type 'S' when done):");
        StdDraw.show();

        while (!userDone) {
            if (StdDraw.hasNextKeyTyped()) {
                userChoice = StdDraw.nextKeyTyped();

                if (userChoice == 'S' || userChoice == 's') {
                    userDone = true;
                } else if (Character.isDigit(userChoice)) {
                    userSeed = userSeed * 10 + Character.getNumericValue(userChoice);

                    StdDraw.clear(backgroundColor);
                    StdDraw.setPenColor(titleColor);
                    StdDraw.text(width * 0.5, height * 0.6,
                            "Enter a random seed (Type 'S' when done):");
                    StdDraw.setPenColor(optionColor);
                    StdDraw.text(width * 0.5, height * 0.5, Long.toString(userSeed));
                    StdDraw.show();
                }
            }
        }
        return userSeed;
    }
}
