package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;

    private long seed;
    private Random rand;

    private List<Room> roomList = new ArrayList<>();

    private Player p;

    private TETile[][] world;
    public WorldGenerator(TETile[][] world1, long seed1) {
        seed = seed1;
        world = world1;
        rand = new Random(seed);
        generateWorld();
    }

    public TETile[][] getWorld() {
        return world;
    }

    public void generateWorld() {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        for (int i = 0; i < 60; i++) {
            generateRoom();
        }

        boolean track = false;
        while (!track) {
            List<Boolean> array = new ArrayList<>();
            for (Room r : roomList) {
                if (!r.isConnected()) {
                    generateHallway();
                }
                array.add(r.isConnected());
            }
            if (areAllTrue(array)) {
                track = true;
            }
        }
    }

    public static boolean areAllTrue(List<Boolean> array) {
        for (boolean b : array) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public void generateRoom() {
        int w = RandomUtils.uniform(rand, 3, 10);
        int h = RandomUtils.uniform(rand, 3, 10);
        int startX = RandomUtils.uniform(rand, 0, world.length - 5);
        int startY = RandomUtils.uniform(rand, 0, world[0].length - 5);

        if (w + startX >= world.length) {
            w = world.length - startX - 1;
        }
        if (h + startY >= world[0].length) {
            h = world[0].length - startY - 1;
        }

        Position botLeft = new Position(startX, startY);
        Room r = new Room(w, h, botLeft);

        if (validRoom(r)) {
            roomList.add(r);
            for (int x = startX; x <= w + startX; x++) {
                for (int y = startY; y <= h + startY; y++) {
                    if (x == startX || x == w + startX || y == startY || y == h + startY) {
                        world[x][y] = Tileset.WALL;
                    } else {
                        world[x][y] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    public boolean validRoom(Room r) {
        for (int x = r.getStartX(); x <= r.getWidth() + r.getStartX(); x++) {
            for (int y = r.getStartY(); y <= r.getHeight() + r.getStartY(); y++) {
                if (world[x][y] == Tileset.FLOOR || world[x][y] == Tileset.WALL) {
                    return false;
                }
            }
        }
        return true;
    }

    public void generateHallway() {
        int startIndex = RandomUtils.uniform(rand, 0, roomList.size());
        int endIndex = RandomUtils.uniform(rand, 0, roomList.size());

        while (startIndex == endIndex) {
            endIndex = RandomUtils.uniform(rand, 0, roomList.size() - 1);
        }

        Room startR = roomList.get(startIndex);
        Room endR = roomList.get(endIndex);

        Position startCenter = startR.getCenter();
        Position endCenter = endR.getCenter();

        int yCount = 0;
        int yDist = endCenter.getY() - startCenter.getY();
        while (yDist != yCount) {
            if (yDist < 0) {
                if (world[startCenter.getX()][startCenter.getY() + yCount] != Tileset.FLOOR) {
                    world[startCenter.getX()][startCenter.getY() + yCount] = Tileset.FLOOR;
                    world[startCenter.getX() + 1][startCenter.getY() + yCount] = Tileset.WALL;
                    world[startCenter.getX() - 1][startCenter.getY() + yCount] = Tileset.WALL;
                }
                yCount--;
            } else {
                if (world[startCenter.getX()][startCenter.getY() + yCount] != Tileset.FLOOR) {
                    world[startCenter.getX()][startCenter.getY() + yCount] = Tileset.FLOOR;
                    world[startCenter.getX() + 1][startCenter.getY() + yCount] = Tileset.WALL;
                    world[startCenter.getX() - 1][startCenter.getY() + yCount] = Tileset.WALL;
                }
                yCount++;
            }
        }

        int xCount = 0;
        int xDist = endCenter.getX() - startCenter.getX();
        while (xDist != xCount) {
            if (xDist < 0) {
                if (world[startCenter.getX() + xCount][endCenter.getY()] != Tileset.FLOOR) {
                    world[startCenter.getX() + xCount][endCenter.getY()] = Tileset.FLOOR;
                    world[startCenter.getX() + xCount][endCenter.getY() + 1] = Tileset.WALL;
                    world[startCenter.getX() + xCount][endCenter.getY() - 1] = Tileset.WALL;
                }
                xCount--;
            } else {
                if (world[startCenter.getX() + xCount][endCenter.getY()] != Tileset.FLOOR) {
                    world[startCenter.getX() + xCount][endCenter.getY()] = Tileset.FLOOR;
                    world[startCenter.getX() + xCount][endCenter.getY() + 1] = Tileset.WALL;
                    world[startCenter.getX() + xCount][endCenter.getY() - 1] = Tileset.WALL;
                }
                xCount++;
            }
        }
        Position pos = new Position(startCenter.getX(), endCenter.getY());
        drawTurn(pos, xDist, yDist);
        startR.setIsConnected(true);
        endR.setIsConnected(true);
    }

    private void drawTurn(Position pos, int distX, int distY) {
        int x = pos.getX();
        int y = pos.getY();
        if (distX < 0) {
            if (distY < 0) {
                if (world[x + 1][y] == Tileset.NOTHING) {
                    world[x + 1][y] = Tileset.WALL;
                }
                world[x + 1][y - 1] = Tileset.WALL;
                world[x][y + 1] = Tileset.FLOOR;
            } else {
                if (world[x + 1][y] == Tileset.NOTHING) {
                    world[x + 1][y] = Tileset.WALL;
                }
                world[x + 1][y + 1] = Tileset.WALL;
                world[x][y - 1] = Tileset.FLOOR;
            }
        } else {
            if (distY < 0) {
                if (world[x - 1][y] == Tileset.NOTHING) {
                    world[x - 1][y] = Tileset.WALL;
                }
                world[x - 1][y - 1] = Tileset.WALL;
                world[x][y + 1] = Tileset.FLOOR;
            } else {
                if (world[x - 1][y] == Tileset.NOTHING) {
                    world[x - 1][y] = Tileset.WALL;
                }
                world[x - 1][y + 1] = Tileset.WALL;
                world[x][y - 1] = Tileset.FLOOR;
            }
        }
    }

    public Position spawn(TETile[][] w) {
        int i = RandomUtils.uniform(rand, 0, roomList.size());
        Room r = roomList.get(i);

        Position center = r.getCenter();
        w[center.getX()][center.getY()] = Tileset.AVATAR;
        p = new Player(center);
        return center;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Engine e = new Engine();
        //TETile[][] tiles = e.interactWithInputString("lswd");
        //ter.renderFrame(tiles);
        e.interactWithKeyboard();
    }
}

