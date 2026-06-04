package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

public class Encounter {

    private static boolean inEncounter = false;
    private static boolean showFinishedHUD = false;

    private static long encounterStartTime;
    private static long encounterEndTime;

    private static final int gameTime = 30000;
    private static final int finishedDisplayTime = 10000;
    private static int currentCoins = 0;



    public static void encounterGame(TETile[][] world) {
        if (!inEncounter) {
            inEncounter = true;
            showFinishedHUD = false;
            currentCoins = 0;
            encounterStartTime = System.currentTimeMillis();
            World.addCoins(world);
        }

    }


    public static int updateEncounter(TETile[][] world) {
        if (inEncounter) {
            long elapsed = System.currentTimeMillis() - encounterStartTime;

            if (elapsed >= gameTime) {
                World.removeCoins(world);
                inEncounter = false;
                showFinishedHUD = true;
                encounterEndTime = System.currentTimeMillis();
                World.addTree(world);
                return currentCoins;
            }
        }

        if (showFinishedHUD) {
            long sinceEnd = System.currentTimeMillis() - encounterEndTime;
            if (sinceEnd >= finishedDisplayTime) {
                showFinishedHUD = false;
            }
        }
        return currentCoins;
    }

    public static void addCoin() {
        currentCoins ++;
    }

    public static boolean isInEncounter() {
        return inEncounter;
    }

    public static boolean isShowingFinishedHUD() {
        return showFinishedHUD;
    }

    public static int getCurrentCoins() {
        return currentCoins;
    }

    public static long getTimeLeft() {
        if (!inEncounter) return 0;
        long elapsed = System.currentTimeMillis() - encounterStartTime;
        return Math.max((gameTime - elapsed) / 1000, 0);
    }
}
