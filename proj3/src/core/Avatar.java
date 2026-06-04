package core;

import tileengine.TETile;
import tileengine.Tileset;
import java.util.Random;

public class Avatar {
    private int x;
    private int y;
    private TETile tileUnder; // remembers the tile the avatar is standing on
    private static int highScore = 0;

    private static final TETile encounterTile = Tileset.TREE;
    private static final TETile coin = Tileset.FLOWER;

    public Avatar(long seed, TETile[][] world, int highScore) {
        Avatar.highScore = highScore;

        Random rand = new Random(seed);
        while (true) {
            int startX = rand.nextInt(world.length);
            int startY = rand.nextInt(world[0].length);

            if (world[startX][startY] == Tileset.FLOOR) {
                this.x = startX;
                this.y = startY;
                break;
            }
        }

        tileUnder = world[x][y]; // should be FLOOR
        world[x][y] = Tileset.AVATAR;
    }

    public Avatar(int x, int y, TETile[][] world, int highScore) {
        this.x = x;
        this.y = y;
        Avatar.highScore = highScore;

        tileUnder = world[x][y];
        world[x][y] = Tileset.AVATAR;
    }

    private boolean isWalkable(TETile tile) {
        return tile == Tileset.FLOOR
            || tile == Tileset.TREE
            || tile == Tileset.FLOWER;
    }

    public void move(char c, TETile[][] world) {
        int newX = x;
        int newY = y;

        char upper = Character.toUpperCase(c);
        if (upper == 'W') {
            newY = y + 1;
        } else if (upper == 'S') {
            newY = y - 1;
        } else if (upper == 'D') {
            newX = x + 1;
        } else if (upper == 'A') {
            newX = x - 1;
        }

        // Bounds check then walkability check
        if (newX < 0 || newX >= world.length || newY < 0 || newY >= world[0].length) {
            return;
        }
        if (!isWalkable(world[newX][newY])) {
            return;
        }

        // Restore the tile the avatar was standing on, then move
        world[x][y] = tileUnder;
        x = newX;
        y = newY;
        tileUnder = world[x][y]; // save what's under the new position

        if (tileUnder == encounterTile) {
            Encounter.encounterGame(world);
        }
        if (tileUnder == coin) {
            Encounter.addCoin();
        }

        world[x][y] = Tileset.AVATAR;
    }

    public int updateHighScore(int currentCoins) {
        if (currentCoins > highScore) {
            highScore = currentCoins;
        }
        return highScore;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
