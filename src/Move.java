import javax.swing.*;
import java.io.*;

public class Move implements Serializable {
    private Piece p;
    private Piece capturedP;
    private int initialX, initialY;
    private int destX, destY;

    public Move(int px, int py, int dx, int dy) {
//        this.p = Board.getPieceAtIndex(px, py);
//        this.capturedP = Board.getPieceAtIndex(dx, dy);
        this.p = new King("white", new Square(1, 5));
        this.capturedP = new Queen("black", new Square(4, 13));
        this.initialX = px;
        this.initialY = py;
        this.destX = dx;
        this.destY = dy;
    }

    public int getDestY() {
        return destY;
    }

    public void setDestY(int destY) {
        this.destY = destY;
    }

    public int getDestX() { return destX; }

    public void setDestX(int destX) {
        this.destX = destX;
    }

    public int getInitialX() {
        return initialX;
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    public Piece getCapturedP() {
        return capturedP;
    }

    public void setCapturedP(Piece capturedP) {
        this.capturedP = capturedP;
    }

    public Piece getP() {
        return p;
    }

    public void setP(Piece p) {
        this.p = p;
    }

    public boolean isDiagonal() {
        return initialX + initialY == destX + destY;
    }

    public boolean isCrossing() {
        return initialX == destX || initialY == destY;
    }

    public static byte[] serializeMove(Move mv) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(mv);
        return baos.toByteArray();
    }

    public static Move deserializeMove(byte[] mv) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(mv);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Move) ois.readObject();
    }
}
