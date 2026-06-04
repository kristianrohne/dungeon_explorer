package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.Color;

import static core.World.generateWorld;


public class Main {

    private static final int SCREEN_HEIGHT = 40;
    private static final int WORLD_HEIGHT = 39;
    private static final int TER_WIDTH = 50;
    private static final int TILE_SIZE = 16;

    public static void main(String[] args) {
        // Disable DirectDraw/Direct3D acceleration on Windows — a common cause
        // of partial-frame rendering artifacts with Java2D on Windows 10.
        System.setProperty("sun.java2d.noddraw", "true");

        // Set canvas to final game dimensions once, before anything draws.
        StdDraw.setCanvasSize(TER_WIDTH * TILE_SIZE, SCREEN_HEIGHT * TILE_SIZE);
        StdDraw.enableDoubleBuffering();
        showMenu();
    }

    public static void showMenu() {
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.clear(StdDraw.BLUE);
        StdDraw.setPenColor(StdDraw.WHITE);

        StdDraw.text(50, 80, "Welcome to our World!");
        StdDraw.text(50, 60, "(N) New Game");
        StdDraw.text(50, 50, "(L) Load Game");
        StdDraw.text(50, 40, "(Q) Quit");
        StdDraw.show();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());

                if (c == 'N') {
                    String seed = buildSeed();
                    long seedVal = Long.parseLong(seed);
                    runGame(seedVal, 0, 0, 0);
                    break;
                } else if (c == 'Q') {
                    System.exit(0);
                } else if (c == 'L') {
                    GameState.loadGame();
                    runGame(GameState.seed, GameState.x, GameState.y, GameState.highScore);
                    break;
                }
            }
        }
    }

    private static String buildSeed() {
        StringBuilder seed = new StringBuilder();
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.clear(StdDraw.BLUE);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(50, 80, "Welcome to our World!");
        StdDraw.text(50, 60, "Enter seed followed by S");
        StdDraw.show();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (Character.isDigit(c)) {
                    seed.append(c);
                } else if (c == 's' || c == 'S') {
                    return seed.toString();
                }

                StdDraw.clear(StdDraw.BLUE);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(50, 80, "Welcome to our World!");
                StdDraw.text(50, 60, "Enter seed followed by S");
                StdDraw.text(50, 30, seed.toString());
                StdDraw.show();
            }
        }
    }

    public static void runGame(long seed, int x, int y, int highScore) {
        TERenderer ter = new TERenderer();
        ter.initialize(TER_WIDTH, SCREEN_HEIGHT);
        TETile[][] world = generateWorld(seed, TER_WIDTH, WORLD_HEIGHT);
        boolean colonPressed = false;

        Avatar player;
        if (x > 0 || y > 0) {
            player = new Avatar(x, y, world, highScore);
        } else {
            player = new Avatar(seed, world, highScore);
        }

        HUD hud = new HUD(world, SCREEN_HEIGHT, TER_WIDTH);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());

                if (colonPressed) {
                    if (c == 'q') {
                        GameState.seed = seed;
                        GameState.x = player.getX();
                        GameState.y = player.getY();
                        GameState.highScore = highScore;
                        GameState.saveGame();
                        System.exit(0);
                    }
                    colonPressed = false;
                } else if (c == ':') {
                    colonPressed = true;
                } else if (c == 'w' || c == 's' || c == 'a' || c == 'd') {
                    player.move(c, world);
                }
            }

            int currentCoins = Encounter.updateEncounter(world);
            highScore = player.updateHighScore(currentCoins);

            StdDraw.clear(Color.BLACK);
            ter.drawTiles(world);
            hud.displayHUD(highScore);

            StdDraw.show();
            StdDraw.pause(16);
        }
    }
}
