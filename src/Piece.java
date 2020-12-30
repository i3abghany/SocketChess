import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Piece extends JPanel {
    protected Square currSquare;
    protected String name;
    protected String color;
    protected String ImageFileName;

    protected Piece(String col, Square sq) {
        this.currSquare = sq;
        this.color = col;
    }

    public abstract boolean isValidMove(Move m);
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> mvs = new ArrayList<>();
        int initialX = this.getCurrSquare().getXCord();
        int initialY = this.getCurrSquare().getYCord();

        for (int i = 0; i < Board.DIM; i++) {
            for (int j = 0; j < Board.DIM; j++) {
                Move mv = new Move(initialX, initialY, i, j);
                if (this.isValidMove(mv)) {
                    mvs.add(mv);
                }
            }
        }
        return mvs;
    }

    public boolean canMove() {
        return getPossibleMoves().size() != 0;
    }

    public void setBackgroundColor(Color c) {
        super.setBackground(c);
    }

    public Square getCurrSquare() {
        return currSquare;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCurrSquare(Square currSquare) {
        this.currSquare = currSquare;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageFileName() {
        return ImageFileName;
    }
}
