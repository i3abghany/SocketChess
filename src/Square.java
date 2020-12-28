import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Square extends JPanel {
    private int xCord, yCord;
    private Piece currentPiece;
    public static final int SQUARE_WIDTH = 50;
    public static Square prevSq = null;
    private static final Border b = BorderFactory.createLineBorder(Color.ORANGE, 2);

    Square() {
        xCord = 0;
        yCord = 0;
        currentPiece = null;
    }

    Square(int x, int y) {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                Square nextSquare = (Square) me.getComponent();

                if (prevSq == nextSquare) {
                    if (nextSquare.getBorder() == null)
                        nextSquare.setBorder(b);
                    else nextSquare.removeBorder();
                    prevSq = new Square(10, 10);
                    return;
                } else {
                    nextSquare.setBorder(b);
                    prevSq.removeBorder();
                }

                if (prevSq.getCurrentPiece() == null) {
                    prevSq = nextSquare;
                    return;
                }

                int initialX = prevSq.getXCord();
                int initialY = prevSq.getYCord();

                int destX = nextSquare.getXCord();
                int destY = nextSquare.getYCord();

                Move mv = new Move(initialX, initialY, destX, destY);
                boolean validMove = prevSq.getCurrentPiece().isValidMove(mv);
                boolean dangerOnKing = Board.getKing("white").kingInDanger();
                if ((validMove && !dangerOnKing) || (validMove && mv.getP() instanceof King)) {
                    nextSquare.removeCurrentPiece(mv.getCapturedP() != null);
                    nextSquare.setCurrentPiece(prevSq.getCurrentPiece());
                    prevSq.removeCurrentPiece(false);
                }

                prevSq = nextSquare;
            }
        });

        xCord = x;
        yCord = y;
        if ((xCord % 2 != 0 && yCord % 2 == 0) || (xCord % 2 == 0 && yCord % 2 != 0)) {
            super.setOpaque(true);
        } else {
            super.setBackground(Color.WHITE);
            super.setOpaque(true);
        }
        setLocation(x * SQUARE_WIDTH, y * SQUARE_WIDTH);
        super.setSize(SQUARE_WIDTH, SQUARE_WIDTH);
    }

    public void removeBorder() {
        super.setBorder(null);
    }

    int getXCord() { return xCord; }
    int getYCord() { return yCord; }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
        this.currentPiece.setCurrSquare(this);
        super.add(currentPiece);
    }
    public void removeCurrentPiece(boolean kill) {
        if (this.currentPiece != null) {
            super.removeAll();
            if (kill) Board.pieces.remove(currentPiece);
            // this.currentPiece.setCurrSquare(null);
            this.currentPiece = null;
        }
    }
}
