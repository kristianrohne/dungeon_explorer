package core;

import edu.princeton.cs.algs4.In;

import java.io.FileWriter;
import java.io.IOException;

public class GameState {

    private static final String SAVE_FILE = "save_data.txt";
    public static long seed;
    public static int x;
    public static int y;
    public static int highScore = 0;

    public GameState(long seed, int x, int y, int highScore) {
        GameState.seed = seed;
        GameState.x = x;
        GameState.y = y;
        GameState.highScore = highScore;
    }


    public static void saveGame() {
        try {
            FileWriter fw = new FileWriter(SAVE_FILE);
            fw.write(seed + "," + x + "," + y + "," + highScore);
            fw.close();
            System.out.println("Game state saved.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }

    }

    public static void loadGame() {
        In in = new In(SAVE_FILE);
        String line = in.readLine();
        String[] parts = line.split(",");
        seed = Long.parseLong(parts[0]);
        x = Integer.parseInt(parts[1]);
        y = Integer.parseInt(parts[2]);
        highScore = Integer.parseInt(parts[3]);
    }
}
