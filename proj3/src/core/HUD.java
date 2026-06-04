package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;

import java.awt.*;

public class HUD {

    private final TETile[][] world;
    private final int screenHeight;
    private final int screenWidth;

    public HUD(TETile[][] w, int screenH, int screenW) {
        world = w;
        screenHeight = screenH;
        screenWidth = screenW;
    }

    public void displayHUD(int highScore) {
        // Draw high score on the right side of the HUD strip
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(screenWidth - 15, screenHeight - 0.5, "High Score: " + highScore);

        if (Encounter.isInEncounter()) {
            encounterHUD(Encounter.getTimeLeft());
        } else if (Encounter.isShowingFinishedHUD()) {
            encounterFinishedHUD(Encounter.getCurrentCoins());
        } else {
            normalHUD();
        }
    }

    private void normalHUD() {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        if (mouseX >= 0 && mouseX < world.length && mouseY >= 0 && mouseY < world[0].length) {
            String description = world[mouseX][mouseY].description();
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(1, screenHeight - 0.5, "Tile: " + description);
        }
    }

    private void encounterHUD(long timeLeft) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textLeft(1, screenHeight - 0.5,
                "Catch all coins before time is up! " + timeLeft + "s left.");
    }

    private void encounterFinishedHUD(int coins) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textLeft(1, screenHeight - 0.5,
                "Time's up! You caught " + coins + " coins.");
    }
}
