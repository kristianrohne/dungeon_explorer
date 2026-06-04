package core;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private static final TETile floor = Tileset.FLOOR;
    private static final TETile wall = Tileset.WALL;
    private static final TETile nothing = Tileset.NOTHING;
    private static final TETile encounterTile = Tileset.TREE;
    private static final TETile coin = Tileset.FLOWER;
    private static final List<Point> centers = new ArrayList<>();
    private static int encounterX;
    private static int encounterY;

    private static class Point {
        public final int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static TETile[][] generateWorld(long seed, int worldWidth, int worldHeight) {
        double fillRatio = 0.0;
        TETile[][] world = null;

        while (fillRatio < 0.50) {
            centers.clear();
            world = new TETile[worldWidth][worldHeight];
            Random rand = new Random(seed);
            fillWithNothing(world);

            // For adding room where encounter Tile is
            Point encounterCenter = addRandomRoom(world, rand, true);
            if (encounterCenter != null) centers.add(encounterCenter);

            // Adding normal rooms
            int numRooms = RandomUtils.uniform(rand, 12, 18);
            for (int i = 0; i < numRooms -1; i++) {
                Point center = addRandomRoom(world, rand, false);
                if (center != null) centers.add(center);
            }

            for (int i = 0; i < centers.size() - 1; i++) {
                drawHallway(world, centers.get(i), centers.get(i + 1));
            }

            addWalls(world);
            fillRatio = calculateFillRatio(world);

            seed++; // Change seed to avoid infinite loops with bad seeds
        }

        System.out.printf("Fill ratio: %.2f%%%n", fillRatio * 100);
        return world;
    }

    private static Point addRandomRoom(TETile[][] world, Random rand, boolean encounterRoom) {
        int roomSize = RandomUtils.uniform(rand, 5, 9);

        for (int attempts = 0; attempts < 100; attempts++) {
            int startX = rand.nextInt(world.length - roomSize - 2) + 1;
            int startY = rand.nextInt(world[0].length - roomSize - 2) + 1;

            if (!overlaps(world, startX, startY, roomSize)) {
                if (!encounterRoom){
                    drawRoom(world, startX, startY, roomSize);
                }
                if (encounterRoom) {
                    drawEncounterRoom(world, startX, startY, roomSize, rand);
                }
                return new Point(startX + roomSize / 2, startY + roomSize / 2);
            }
        }
        return null; // Failed after 100 attempts
    }


    private static void drawEncounterRoom (TETile[][] world, int startX, int startY, int size, Random rand) {
        encounterX = RandomUtils.uniform(rand, startX, startX + size);
        encounterY = RandomUtils.uniform(rand, startY, startY + size);
        drawRoom(world, startX, startY, size);
        addTree(world);
    }
    private static void drawRoom(TETile[][] world, int startX, int startY, int size) {
        for (int x = startX; x < startX + size; x++) {
            for (int y = startY; y < startY + size; y++) {
                world[x][y] = floor; // Room tiles
            }
        }
    }

    private static void drawHallway(TETile[][] world, Point a, Point b) {
        TETile tile = floor;

        int x1 = a.x, y1 = a.y;
        int x2 = b.x, y2 = b.y;

        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            if (world[x][y1] != encounterTile) {
                world[x][y1] = tile;
            }
        }

        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            if (world[x2][y] != encounterTile) {
                world[x2][y] = tile;
            }
        }
    }

    private static void addWalls(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (world[x][y] == nothing && touchesFloor(world, x, y)) {
                    world[x][y] = wall;
                }
            }
        }
    }

    private static void fillWithNothing(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = nothing;
            }
        }
    }

    private static boolean overlaps(TETile[][] world, int startX, int startY, int size) {
        for (int x = startX - 1; x < startX + size + 1; x++) {
            for (int y = startY - 1; y < startY + size + 1; y++) {
                if (x < 0 || y < 0 || x >= world.length || y >= world[0].length) continue;
                if (world[x][y] != nothing) return true;
            }
        }
        return false;
    }

    private static boolean touchesFloor(TETile[][] world, int x, int y) {
        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        for (int[] d : directions) {
            int nx = x + d[0];
            int ny = y + d[1];

            if (nx >= 0 && ny >= 0 && nx < world.length && ny < world[0].length) {
                if (world[nx][ny] == floor) {
                    return true;
                } else if (world[nx][ny] == encounterTile) {
                    return true;
                }
            }
        }
        return false;
    }

    private static double calculateFillRatio(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;
        int totalTiles = width * height;
        int filledTiles = 0;

        for (TETile[] teTiles : world) {
            for (int y = 0; y < height; y++) {
                if (teTiles[y] == floor || teTiles[y] == wall) {
                    filledTiles++;
                }
            }
        }
        return (double) filledTiles / totalTiles;
    }

    // For adding coins to the encounter game
    public static void addCoins(TETile[][] world) {
        for (Point p : centers) {
            int x = p.x;
            int y = p.y;
            world[x][y] = coin;
        }
    }

    // For removing coins to the encounter game
    public static void removeCoins(TETile[][] world) {
        for (Point p : centers) {
            int x = p.x;
            int y = p.y;
            world[x][y] = floor;
        }
    }

    public static void addTree (TETile[][] world) {
        world[encounterX][encounterY] = Tileset.TREE;
    }
}
