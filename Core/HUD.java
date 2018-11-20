package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

/**
 * This class takes care of calculating all relevant information for the game's HUD,
 * which is to be displayed in the GUI class.
 */
public class HUD implements java.io.Serializable {
    private Environment environment;
    private int hudWidth;
    private int hudHeight;

    HUD(Environment env, int hudWidth, int hudHeight) {
        this.environment = env;
        this.hudWidth = hudWidth;
        this.hudHeight = hudHeight;
    }

    /**
     * @return : The width of the HUD.
     */
    public int getHudWidth() {
        return hudWidth;
    }

    /**
     * Actions include things that the player can do with the keyboard. If an action can
     * be performed, it is preceded by a "Y" in the string array. Otherwise, it is preceded
     * by a "N" in the string array.
     *
     * @return : An array of strings relating to the available actions to the player.
     */
    public String[] displayActions() {
        String heading = "AVAILABLE ACTIONS";
        String action1 = "(W)     - Walk up";
        String action2 = "(A)     - Walk left";
        String action3 = "(S)     - Walk down";
        String action4 = "(D)     - Walk right";
        String action5 = "(SPACE) - Switch worlds";
        String action6 = "(:Q)    - Save and quit";
        String action1Possible = "N";
        String action2Possible = "N";
        String action3Possible = "N";
        String action4Possible = "N";

        if (environment.getPlayer().canMoveUp()) {
            action1Possible = "Y";
        }
        if (environment.getPlayer().canMoveLeft()) {
            action2Possible = "Y";
        }
        if (environment.getPlayer().canMoveDown()) {
            action3Possible = "Y";
        }
        if (environment.getPlayer().canMoveRight()) {
            action4Possible = "Y";
        }

        return new String[]{heading,
            action1Possible, action1,
            action2Possible, action2,
            action3Possible, action3,
            action4Possible, action4,
            action5, action6};
    }

    /**
     * If a player has lives, each is added to a string of ❤ characters. If
     * the player runs out of lives, the DEAD is returned.
     *
     * @return : The player lives in hearts if they exist. Otherwise, the string DEAD.
     */
    public String[] displayLives() {
        int playerLives = environment.getPlayer().getLives();
        StringBuilder lives = new StringBuilder();
        String livesHeading = "PLAYER LIVES";

        if (playerLives < 1) {
            lives.append("DEAD");
        } else {
            for (int i = 0; i < playerLives; i += 1) {
                lives.append("❤");
            }
        }
        return new String[]{livesHeading, lives.toString()};
    }

    /**
     * Display the current amount of charges that the player has picked up.
     *
     * @return : A string array relating to the appropriate display information.
     */
    public String[] displayCharges() {
        int charges = environment.getPlayer().getBuddy().getCharges();
        int maxCharges = environment.getPlayer().getBuddy().getChargesNeeded();
        StringBuilder chargesNeeded = new StringBuilder();
        StringBuilder chargesGotten = new StringBuilder();
        String chargesHeading = "BUDDY CHARGES";

        if (charges > maxCharges) {
            charges = maxCharges;
        }

        for (int i = 0; i < charges; i += 1) {
            chargesGotten.append('≡');
        }

        for (int j = 0; j < maxCharges; j += 1) {
            chargesNeeded.append('≡');
        }
        return new String[]{chargesHeading,
                chargesGotten.toString(),
                chargesNeeded.toString()};
    }

    /**
     * Information returned relates to the tiles, or area, that the mouse is currently over.
     *
     * @return : A description of what the mouse is over.
     */
    public String[] displayMouseHover() {
        int maxWorldX = environment.getWidth();
        int maxWorldY = environment.getHeight();
        String mouseOverTitle = "MOUSE OVER";
        String mouseOverMessage = "You can see a";
        String mouseOver;

        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();

        int xPos = (int) x - hudWidth;
        int yPos = (int) y - hudHeight;

        if (xPos >= 0 && xPos < maxWorldX && yPos >= 0 && yPos < maxWorldY) {
            mouseOver = environment.getCurrentWorld()[xPos][yPos].description();
            if (mouseOver.charAt(0) == 'N') {
                if (mouseOver.charAt(1) == 'L') {
                    mouseOver = mouseOver.substring(3, mouseOver.length());
                } else if (mouseOver.charAt(1) == 'D') {
                    mouseOverMessage = "You remember seeing";
                    mouseOver = "a " + mouseOver.substring(3, mouseOver.length());
                } else if (mouseOver.substring(1, mouseOver.length()).equals("player")) {
                    mouseOverMessage = "This is you, the";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else if (mouseOver.substring(1, mouseOver.length()).equals("zombie")) {
                    mouseOverMessage = "This is you, the";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else if (mouseOver.substring(1, mouseOver.length()).equals("buddy")) {
                    mouseOverMessage = "This is your";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else if (mouseOver.substring(1, mouseOver.length()).equals("trap")) {
                    mouseOverMessage = "This is a";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else {
                    mouseOverMessage = "This is an";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                }
            } else if (mouseOver.charAt(0) == 'V') {
                if (mouseOver.substring(1, mouseOver.length()).equals("player")) {
                    mouseOverMessage = "This is you, the";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else if (mouseOver.substring(1, mouseOver.length()).equals("virtual zombie")) {
                    mouseOverMessage = "This is you, the";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else if (mouseOver.substring(1, mouseOver.length()).equals("buddy")) {
                    mouseOverMessage = "This is your";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else if (mouseOver.substring(1, mouseOver.length()).equals("wall tile")) {
                    mouseOverMessage = "You can see a";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else if (mouseOver.substring(2, mouseOver.length()).equals("static")) {
                    mouseOverMessage = "You can see a";
                    mouseOver = "floor tile";
                } else if (mouseOver.substring(1, mouseOver.length()).equals("energy")) {
                    mouseOverMessage = "This is a resource,";
                    mouseOver = mouseOver.substring(1, mouseOver.length());
                } else {
                    mouseOverMessage = "This is";
                    mouseOver = mouseOver.substring(2, mouseOver.length());
                }
            }
        } else if (xPos > -hudWidth && yPos > -hudHeight) {
            mouseOverMessage = "This is the";
            mouseOver = "HUD";
        } else {
            mouseOverMessage = "This is";
            mouseOver = "nothing";
        }

        return new String[]{mouseOverTitle, mouseOverMessage, mouseOver + "."};
    }

    /**
     * @return : Instructions for the game.
     */
    public String[] displayInstructions() {
        String heading = "INSTRUCTIONS";

        String line1 = "You and your buddy";
        String line2 = "(a hovering unit that";
        String line3 = "projects the virtual ";
        String line4 = "world) are exploring";
        String line5 = "a cave. Unfortunately,";
        String line6 = "your buddy is low on";
        String line7 = "charge. In order to";
        String line8 = "rid the cave of traps,";
        String line9 = "you must fully charge";
        String line10 = "your buddy by";
        String line11 = "collecting energy";
        String line12 = "charges. These can";
        String line13 = "only be seen and";
        String line14 = "picked up in the";
        String line15 = "virtual world,";
        String line16 = "projected by your";
        String line17 = "buddy. Watch out";
        String line18 = "though! Even though";
        String line19 = "you can't see the";
        String line20 = "traps in the virtual";
        String line21 = "world, they still";
        String line22 = "hurt you! Finally,";
        String line23 = "don't be afraid to";
        String line24 = "use your mouse to";
        String line25 = "see what things are!";

        return new String[]{heading, line1, line2, line3, line4, line5, line6, line7, line8,
            line9, line10, line11, line12, line13, line14, line15, line16, line17,
            line18, line19, line20, line21, line22, line23, line24, line25};
    }
}
