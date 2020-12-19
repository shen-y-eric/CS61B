package byow.Core;

public class Player {
    private Position p;

    public Player(Position p) {
        this.p = p;
    }
    public Position getP() {
        return p;
    }
    public int getX() {
        return p.getX();
    }
    public int getY() {
        return p.getY();
    }
    public void setP(Position newP) {
        p = newP;
    }
}
