package byog.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */
public class Tileset {
    // Player and buddy tiles
    public static final TETile PLAYER = new TETile('@', Color.WHITE, new Color(92, 112, 103),
            "Nplayer");
    public static final TETile DEADPLAYER = new TETile('@', new Color(102, 0, 0),
            new Color(92, 112, 103), "Nzombie");
    public static final TETile BUDDY = new TETile('●', new Color(102, 255, 255),
            new Color(92, 112, 103), "Nbuddy");

    // Player and buddy tiles (VIRTUAL)
    public static final TETile PLAYERV = new TETile('@', Color.WHITE, Color.BLACK,
            "Vplayer");
    public static final TETile DEADPLAYERV = new TETile('@', new Color(102, 0, 0),
            new Color(0, 0, 0), "Vvirtual zombie");
    public static final TETile BUDDYV = new TETile('●', new Color(102, 255, 255),
            Color.BLACK, "Vbuddy");

    // Wall tiles (LIGHT)
    public static final TETile WALL1 = new TETile('#', Color.BLACK, new Color(195, 127, 124),
            "NL1wall tile");
    public static final TETile WALL2 = new TETile('#', Color.BLACK, new Color(206, 138, 122),
            "NL2wall tile");
    public static final TETile WALL3 = new TETile('#', Color.BLACK, new Color(231, 168, 133),
            "NL3wall tile");
    public static final TETile WALL4 = new TETile('#', Color.BLACK, new Color(242, 202, 157),
            "NL4wall tile");
    public static final TETile WALL5 = new TETile('#', Color.BLACK, new Color(240, 226, 174),
            "NL5wall tile");

    // Wall tiles (DARK)
    public static final TETile WALL1D = new TETile('#', Color.BLACK, new Color(64, 62, 66),
            "ND1wall tile");
    public static final TETile WALL2D = new TETile('#', Color.BLACK, new Color(64, 63, 64),
            "ND2wall tile");
    public static final TETile WALL3D = new TETile('#', Color.BLACK, new Color(66, 65, 61),
            "ND3wall tile");
    public static final TETile WALL4D = new TETile('#', Color.BLACK, new Color(61, 68, 60),
            "ND4wall tile");
    public static final TETile WALL5D = new TETile('#', Color.BLACK, new Color(57, 71, 61),
            "ND5wall tile");

    // Floor tiles (LIGHT)
    public static final TETile FLOOR = new TETile('·', new Color(171, 186, 179),
           new Color(92, 112, 103),
          "NL1floor tile");

    // Floor tiles (DARK)
    public static final TETile FLOORD = new TETile('·', new Color(51, 51, 51),
            new Color(12, 26, 39),
            "ND1floor tile");

    // Wall tiles (VIRTUAL)
    public static final TETile WALLV = new TETile('#', new Color(0, 255, 255), new Color(0, 0, 0),
            "Vwall tile");


    // Floor tiles (VIRTUAL)
    public static final TETile FLOORV = new TETile('·', new Color(0, 255, 255),
            new Color(0, 0, 0),
            "Vfloor tile");

    // Static tiles (VIRTUAL)
    public static final TETile STATIC1 = new TETile('·', new Color(0, 255, 255),
            new Color(10, 10, 10), "V1static");
    public static final TETile STATIC2 = new TETile('·', new Color(0, 255, 255),
            new Color(17, 17, 17), "V2static");
    public static final TETile STATIC3 = new TETile('·', new Color(0, 255, 255),
            new Color(24, 24, 24), "V3static");
    public static final TETile STATIC4 = new TETile('·', new Color(0, 255, 255),
            new Color(31, 31, 31), "V4static");
    public static final TETile STATIC5 = new TETile('·', new Color(0, 255, 255),
            new Color(38, 38, 38), "V5static");

    // Trap and energy tiles
    public static final TETile TRAP = new TETile('X', new Color(204, 255, 153),
            new Color(92, 112, 103), "Ntrap");
    public static final TETile ENERGY = new TETile('≡', new Color(255, 255, 0),
            new Color(0, 0, 0), "Venergy");

    // Transition tiles
    public static final TETile TOANALOG1 = new TETile('▦', new Color(195, 127, 124),
            new Color(92, 112, 103), "TransitionToAnalog");
    public static final TETile TOANALOG2 = new TETile('▦', new Color(206, 138, 122),
            new Color(92, 112, 103), "TransitionToAnalog");
    public static final TETile TOANALOG3 = new TETile('▦', new Color(231, 168, 133),
            new Color(92, 112, 103), "TransitionToAnalog");
    public static final TETile TOANALOG4 = new TETile('▦', new Color(242, 202, 157),
            new Color(92, 112, 103), "TransitionToAnalog");
    public static final TETile TOANALOG5 = new TETile('▦', new Color(240, 226, 174),
            new Color(92, 112, 103), "TransitionToAnalog");
    public static final TETile TOANALOG6 = new TETile('▦', new Color(171, 186, 179),
            new Color(92, 112, 103), "TransitionToAnalog");

    public static final TETile TODIGITAL = new TETile('▦', new Color(0, 255, 255),
            new Color(0, 0, 0), "TransitionToDigital");

    // Empty tiles
    public static final TETile NOTHING = new TETile(' ', Color.black, new Color(6, 13, 19),
            "Nempty tile");

    // Original tiles
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile FLOWER = new TETile('❀', new Color(204, 255, 153),
            new Color(92, 112, 103), "flower");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}
