# sp25-proj3-g382
SP25 Project 3
A 2D tile-based exploration game built in Java using the Princeton StdDraw library.

Gameplay
Navigate a procedurally generated world, collect coins during timed encounter events, and chase a high score.

Move: W / A / S / D
Save & Quit: :Q
HUD: hover your mouse over any tile to see its description; high score is always shown in the top-right corner
Encounters
Walk onto a tree tile to trigger a 30-second coin-collection mini-game. Coins (flowers) are scattered across the map — grab as many as you can before time runs out. Your high score tracks the best coin total across encounters.

Running the Game
Compile and run proj3/src/core/Main.java with the Princeton algs4.jar on the classpath (located in proj3/lib/).

javac -cp proj3/lib/algs4.jar proj3/src/**/*.java
java  -cp proj3/lib/algs4.jar:proj3/src core.Main
On Windows use ; instead of : as the classpath separator.

Menu Options
Key	Action
N	New game — prompts for a numeric seed (end with S)
L	Load last saved game
Q	Quit
World Generation
The world is generated from a seed using a room-and-hallway algorithm:

Rooms are placed randomly until the floor fill-ratio exceeds 50%.
Rooms are connected by L-shaped hallways in placement order.
One room is designated as the encounter spawn point.
The same seed always produces the same map.

Project Structure
proj3/src/
  core/
    Main.java        — entry point, menu, game loop
    World.java       — procedural world generation
    Avatar.java      — player movement and tile interaction
    Encounter.java   — timed coin-collection event logic
    HUD.java         — heads-up display (tile description, score, timer)
    GameState.java   — save / load
  tileengine/
    TERenderer.java  — renders the tile grid via StdDraw
    TETile.java      — tile data model
    Tileset.java     — tile definitions (floor, wall, avatar, tree, flower, ...)
  utils/
    RandomUtils.java — seeded random helpers
Authors
Group 382 — UC Berkeley CS61B SP25
