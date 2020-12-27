import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {
    private int xCord, yCord;
    private Piece currentPiece;
    public static final int SQUARE_WIDTH = 50;

    Square() {
        xCord = 0;
        yCord = 0;
        currentPiece = null;
    }

    Square(int x, int y) {
        xCord = x;
        yCord = y;
        if ((xCord % 2 != 0 && yCord % 2 == 0) || (xCord % 2 == 0 && yCord % 2 != 0)) {
            super.setOpaque(true);
        }
        else {
            super.setBackground(Color.WHITE);
            super.setOpaque(true);
        }
        setLocation(x * SQUARE_WIDTH, y * SQUARE_WIDTH);
        super.setSize(SQUARE_WIDTH, SQUARE_WIDTH);
    }

    int getXCord() { return xCord; }
    int getYCord() { return yCord; }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }
}
