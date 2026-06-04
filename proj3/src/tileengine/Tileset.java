package tileengine;

import java.awt.Color;

public class Tileset {
    public static final TETile AVATAR = new TETile('♔', new Color(220, 220, 255), new Color(10, 10, 20), "explorer", 0);

    // Define the floor color
    public static final Color FLOOR_COLOR = new Color(128, 192, 128); // The original floor color


    public static final TETile FLOOR = new TETile('.', new Color(50, 100, 150), new Color(10, 30, 50), "blue water floor", 2);

    public static final TETile WALL = new TETile('▦', new Color(90, 110, 130), new Color(20, 20, 30), "soft bluish wall", 1);

        public static final TETile NOTHING = new TETile(
            '▦',
            new Color(10, 20, 40),
            new Color(5, 10, 25),
            "dark blue-grey background",
            3
        );
    public static final TETile GREEN_CAVE_FLOOR = new TETile(
            '.',
            new Color(100, 255, 180),
            new Color(20, 40, 30),
            "mossy cave floor",
            101
    );

    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass", 4);
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water", 5);
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower", 6);
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black, "locked door", 7);
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black, "unlocked door", 8);
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand", 9);
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain", 10);
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree", 11);

    public static final TETile CELL = new TETile('█', Color.white, Color.black, "cell", 12);
    public static final TETile BLUE_WATER_FLOOR = new TETile('.', new Color(70, 130, 180), new Color(10, 30, 50), "shimmery water floor", 13);

}


