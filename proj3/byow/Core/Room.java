package byow.Core;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int width;
    private int height;
    private int startX;
    private int startY;
    private Position botLeft;
    private boolean isConnected;

    private List<Position> wallPos = new ArrayList<>();
    private List<Position> floorPos = new ArrayList<>();

    public Room(int width, int height, Position botLeft) {
        this.width = width;
        this.height = height;
        this.startX = botLeft.getX();
        this.startY = botLeft.getY();
        this.isConnected = false;
        getFloorTiles();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getBotLeft() {
        return botLeft;
    }

    public List<Position> getWallPos() {
        return wallPos;
    }

    public List<Position> getFloorPos() {
        return floorPos;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean value) {
        isConnected = value;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public Position getCenter() {
        return new Position(width / 2 + startX, height / 2 + startY);
    }

    private void getFloorTiles() {
        for (int x = startX; x <= width + startX; x++) {
            for (int y = startY; y <= height + startY; y++) {
                Position p = new Position(x, y);
                if (x == startX || x == width + startX || y == startY || y == height + startY) {
                    wallPos.add(p);
                } else {
                    floorPos.add(p);
                }
            }
        }
    }
}
