package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Engine {
    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private long seed;
    private TETile[][] world;
    private Position curr;
    private Position start;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        boolean b = true;
        boolean menu = true;
        drawMenu();
        while (b) {
            if (menu) {
                if (StdDraw.hasNextKeyTyped()) {
                    Character c = StdDraw.nextKeyTyped();
                    if (c.equals('n') || c.equals('N')) {
                        String s = "";
                        drawSeed("");
                        while (b) {
                            if (StdDraw.hasNextKeyTyped()) {
                                Character next = StdDraw.nextKeyTyped();
                                if (!(next.equals('s') || next.equals('S'))) {
                                    s += next;
                                    drawSeed(s);
                                } else {
                                    seed = Long.parseLong(s);
                                    break;
                                }
                            }
                        }
                        ter.initialize(WIDTH, HEIGHT);
                        startGame();
                        ter.renderFrame(world);
                        menu = false;
                        curr = start;
                    } else if (c.equals('q') || c.equals('Q')) {
                        System.exit(0);
                    } else if (c.equals('l') || c.equals('L')) {
                        load();
                        ter.initialize(WIDTH, HEIGHT);
                        startGame();
                        world[start.getX()][start.getY()] = Tileset.FLOOR;
                        world[curr.getX()][curr.getY()] = Tileset.AVATAR;
                        ter.renderFrame(world);
                        menu = false;
                    }
                }
            }
            if (!menu) {
                game();
            }
        }
    }

    public void game() {
        while (true) {
            hud();
            if (StdDraw.hasNextKeyTyped()) {
                Character move = StdDraw.nextKeyTyped();
                if (move.equals('w')) {
                    if (world[curr.getX()][curr.getY() + 1] == Tileset.FLOOR) {
                        world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                        world[curr.getX()][curr.getY() + 1] = Tileset.AVATAR;
                        curr = new Position(curr.getX(), curr.getY() + 1);
                        ter.renderFrame(world);
                    }
                }
                if (move.equals('s')) {
                    if (world[curr.getX()][curr.getY() - 1] == Tileset.FLOOR) {
                        world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                        world[curr.getX()][curr.getY() - 1] = Tileset.AVATAR;
                        curr = new Position(curr.getX(), curr.getY() - 1);
                        ter.renderFrame(world);
                    }
                }
                if (move.equals('d')) {
                    if (world[curr.getX() + 1][curr.getY()] == Tileset.FLOOR) {
                        world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                        world[curr.getX() + 1][curr.getY()] = Tileset.AVATAR;
                        curr = new Position(curr.getX() + 1, curr.getY());
                        ter.renderFrame(world);
                    }
                }
                if (move.equals('a')) {
                    if (world[curr.getX() - 1][curr.getY()] == Tileset.FLOOR) {
                        world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                        world[curr.getX() - 1][curr.getY()] = Tileset.AVATAR;
                        curr = new Position(curr.getX() - 1, curr.getY());
                        ter.renderFrame(world);
                    }
                }
                if (move.equals(':')) {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            Character t = StdDraw.nextKeyTyped();
                            if (t.equals('q') || t.equals('Q')) {
                                save();
                                System.exit(0);
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public void startGame() {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        WorldGenerator w = new WorldGenerator(finalWorldFrame, seed);
        world = w.getWorld();
        start = w.spawn(world);
    }

    public void drawMenu() {
        StdDraw.setCanvasSize(700, 700);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        drawFrame();
    }

    public void drawSeed(String s) {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT - 10, "Enter seed: " + s);
        StdDraw.show();
    }

    public void drawFrame() {
        // Clear frame
        StdDraw.clear(Color.BLACK);
        // set font
        StdDraw.setPenColor(Color.WHITE);
        // draw input screen in center
        StdDraw.text(WIDTH / 2, HEIGHT - 10, "CS61B: THE GAME");
        Font f = new Font("Monaco", Font.BOLD, 23);
        StdDraw.setFont(f);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT - 27.5, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT - 30, "Quit (Q)");
        StdDraw.show();
    }

    public void hud() {
        ter.renderFrame(world);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        Font f = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(f);
        StdDraw.setPenColor(Color.WHITE);
        if (world[x][y] == Tileset.WALL) {
            StdDraw.text(WIDTH - 2, HEIGHT - 1, "WALL");
        } else if (world[x][y] == Tileset.FLOOR) {
            StdDraw.text(WIDTH - 2, HEIGHT - 1, "FLOOR");
        } else if (world[x][y] == Tileset.AVATAR) {
            StdDraw.text(WIDTH - 2, HEIGHT - 1, "PLAYER");
        }
        StdDraw.show();
    }

    private void save() {
        File f = new File("byow/Core/saved.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String s = String.valueOf(seed) + "," + curr.getX() + "," + curr.getY();
        Utils.writeContents(f, s);
    }

    private void load() {
        File f = new File("byow/Core/saved.txt");
        if (f.exists()) {
            String s = Utils.readContentsAsString(f);
            String[] list = s.split(",");
            seed = Long.parseLong(list[0]);
            curr = new Position(Integer.parseInt(list[1]), Integer.parseInt(list[2]));
        }
    }
    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        int i = 0;
        if ((input.charAt(i) == 'n') || input.charAt(i) == 'N') {
            String seedCount = "";
            i++;
            while (input.charAt(i) != 's' && input.charAt(i) != 'S') {
                seedCount += input.charAt(i);
                i++;
            }
            seed = Long.parseLong(seedCount);
            startGame();
            curr = start;
        } else if (input.charAt(i) == 'q' || input.charAt(i) == 'Q') {
            System.exit(0);
        } else if (input.charAt(i) == 'l' || input.charAt(i) == 'L') {
            load();
            startGame();
            world[start.getX()][start.getY()] = Tileset.FLOOR;
            world[curr.getX()][curr.getY()] = Tileset.AVATAR;
        }
        i++;
        while (i < input.length()) {
            char move = input.charAt(i);
            if (move == 'w' || move == 'W') {
                if (world[curr.getX()][curr.getY() + 1] == Tileset.FLOOR) {
                    world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                    world[curr.getX()][curr.getY() + 1] = Tileset.AVATAR;
                    curr = new Position(curr.getX(), curr.getY() + 1);
                }
                i++;
            }
            if (move == 's' || move == 'S') {
                if (world[curr.getX()][curr.getY() - 1] == Tileset.FLOOR) {
                    world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                    world[curr.getX()][curr.getY() - 1] = Tileset.AVATAR;
                    curr = new Position(curr.getX(), curr.getY() - 1);
                }
                i++;
            }
            if (move == 'd' || move == 'D') {
                if (world[curr.getX() + 1][curr.getY()] == Tileset.FLOOR) {
                    world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                    world[curr.getX() + 1][curr.getY()] = Tileset.AVATAR;
                    curr = new Position(curr.getX() + 1, curr.getY());
                }
                i++;
            }
            if (move == 'a' || move == 'A') {
                if (world[curr.getX() - 1][curr.getY()] == Tileset.FLOOR) {
                    world[curr.getX()][curr.getY()] = Tileset.FLOOR;
                    world[curr.getX() - 1][curr.getY()] = Tileset.AVATAR;
                    curr = new Position(curr.getX() - 1, curr.getY());
                }
                i++;
            }
            if (move == ':') {
                i++;
                char t = input.charAt(i);
                if (t == 'q' || t == 'Q') {
                    save();
                    i++;
                } else {
                    break;
                }
            }
        }
        return world;
    }
}
