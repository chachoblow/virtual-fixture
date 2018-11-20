package byog.Core;

import byog.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

/**
 * This class manages all aspects of graphical user interface. It retrieves the
 * appropriate information from appropriate classes, and then organizes and displays
 * the relevant data.
 */
public class GUI implements java.io.Serializable {
    private HUD hud;
    private TERenderer tileRender;
    private Environment environment;
    private int hudWidth;
    private int hudHeight;
    private int worldHeight;
    private Font optionFont;
    private Font titleFont;
    private Color boxColor;
    private Color textColor;
    private Color darkTextColor;
    private Color livesColor;
    private Color chargesCurrentColor;
    private Color chargesMaxColor;
    private double leftTextMargin;

    private Animation animation;

    GUI(HUD worldHud, Environment env, TERenderer ter) {
        this.hud = worldHud;
        this.environment = env;
        this.tileRender = ter;
        this.worldHeight = environment.getWorld().getHeight();
        this.hudWidth = hud.getHudWidth();
        this.hudHeight = worldHeight;

        // Set all fonts, colors, and margins.
        this.optionFont = new Font("Monospaced", Font.PLAIN, 15);
        this.titleFont = new Font("Monospaced", Font.BOLD, 16);
        this.boxColor = new Color(18, 48, 59);
        this.textColor = new Color(189, 194, 160);
        this.darkTextColor = new Color(74, 78, 50);
        this.livesColor = new Color(165, 115, 115);
        this.chargesCurrentColor = new Color(255, 255, 128);
        this.chargesMaxColor = new Color(89, 89, 89);
        this.leftTextMargin = 0.3;

        animation = new Animation(environment, tileRender);
    }

    /**
     * Display the GUI and all of its elements, to the screen.
     */
    public void displayGUI() {
        displayHudBox();
        displayLives();
        displayCharges();
        displayActions();
        displayMouseHover();
        displayHowTo();
        displayWorldTiles();
        StdDraw.show();
    }

    public void switchWorlds() {
        environment.calcEnvironments();
        animation.transitionWorld();
        environment.switchWorld();
    }

    /**
     * Display the background box for the HUD.
     */
    private void displayHudBox() {
        double boxCenterX = hudWidth / 2;
        double boxCenterY = worldHeight / 2;
        double halfBoxWidth = hudWidth / 2 + 1;
        double halfBoxHeight = worldHeight / 2;

        StdDraw.setPenColor(boxColor);
        StdDraw.filledRectangle(boxCenterX, boxCenterY, halfBoxWidth, halfBoxHeight);
    }

    /**
     * Display the lives of the player in the HUD.
     */
    private void displayLives() {
        double titleHeight = hudHeight - 1.1;
        double livesHeight = hudHeight - 2.2;

        StdDraw.setPenColor(textColor);
        StdDraw.setFont(titleFont);
        StdDraw.textLeft(leftTextMargin, titleHeight, hud.displayLives()[0]);
        StdDraw.setPenColor(livesColor);
        StdDraw.setFont(optionFont);
        StdDraw.textLeft(leftTextMargin, livesHeight, hud.displayLives()[1]);
    }

    /**
     * Display the current charges of the buddy in the world.
     */
    private void displayCharges() {
        double titleHeight = hudHeight - 5.2;
        double chargesHeight = hudHeight - 6.4;

        StdDraw.setPenColor(textColor);
        StdDraw.setFont(titleFont);
        StdDraw.textLeft(leftTextMargin, titleHeight, hud.displayCharges()[0]);
        StdDraw.setPenColor(chargesMaxColor);
        StdDraw.setFont(optionFont);
        StdDraw.textLeft(leftTextMargin, chargesHeight, hud.displayCharges()[2]);
        StdDraw.setPenColor(chargesCurrentColor);
        StdDraw.setFont(optionFont);
        StdDraw.textLeft(leftTextMargin, chargesHeight, hud.displayCharges()[1]);
    }

    /**
     * Display the allowable actions for the player to perform in the HUD.
     */
    private void displayActions() {
        double titleHeight = hudHeight - 9.4;
        double actionHeight = hudHeight - 10.6;
        String[] actions = hud.displayActions();

        StdDraw.setPenColor(textColor);
        StdDraw.setFont(titleFont);
        StdDraw.textLeft(leftTextMargin, titleHeight, actions[0]);
        StdDraw.setFont(optionFont);

        // If an action can be performed, make its text light. Otherwise, make it dark.
        for (int i = 1; i < actions.length - 2; i += 1) {
            if ((i & 1) == 0) {
                continue;
            } else if (actions[i].equals("Y")) {
                StdDraw.setPenColor(textColor);
            } else {
                StdDraw.setPenColor(darkTextColor);
            }
            StdDraw.textLeft(leftTextMargin, actionHeight, actions[i + 1]);
            actionHeight -= 1;
        }

        StdDraw.setPenColor(textColor);
        StdDraw.textLeft(leftTextMargin, actionHeight, actions[actions.length - 2]);
        StdDraw.textLeft(leftTextMargin, actionHeight - 1, actions[actions.length - 1]);
    }

    /**
     * Display what the mouse is hovering over to the HUD.
     */
    private void displayMouseHover() {
        double titleHeight = hudHeight - 18.6;
        double messageHeight = hudHeight - 19.7;

        StdDraw.setPenColor(textColor);
        StdDraw.setFont(titleFont);
        StdDraw.textLeft(leftTextMargin, titleHeight, hud.displayMouseHover()[0]);
        StdDraw.setFont(optionFont);
        StdDraw.textLeft(leftTextMargin, messageHeight, hud.displayMouseHover()[1]);
        StdDraw.textLeft(leftTextMargin, messageHeight - 1, hud.displayMouseHover()[2]);
    }

    /**
     * Display the visible and remembered world tiles to the GUI.
     */
    private void displayWorldTiles() {
        environment.calcEnvironments();
        tileRender.renderFrame(environment.getCurrentWorld());
    }

    /**
     * Display the instructions for the game.
     */
    private void displayHowTo() {
        double titleHeight = hudHeight - 23.7;
        double messageHeight = hudHeight - 24.8;

        StdDraw.setPenColor(textColor);
        StdDraw.setFont(titleFont);
        StdDraw.textLeft(leftTextMargin, titleHeight, hud.displayInstructions()[0]);
        StdDraw.setFont(optionFont);

        for (int i = 1; i < hud.displayInstructions().length; i += 1) {
            StdDraw.textLeft(leftTextMargin, messageHeight - i + 1, hud.displayInstructions()[i]);
        }
    }
}
