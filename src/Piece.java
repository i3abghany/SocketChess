import java.awt.event.MouseAdapter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

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
