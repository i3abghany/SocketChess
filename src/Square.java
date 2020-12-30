import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Square extends JPanel {
    private int xCord, yCord;
    private Piece currentPiece;
    public static final int SQUARE_WIDTH = 50;
    private static final Border b = BorderFactory.createLineBorder(Color.ORANGE, 2);

    Square() {
        xCord = 0;
        yCord = 0;
        currentPiece = null;
    }

    Square(int x, int y) {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                Board.backupPrevSq = (Square)Board.cloneSwingComponent(Board.prevSq);
                Board.backupNextSq = (Square)Board.cloneSwingComponent((Square) me.getComponent());

                Square nextSquare = (Square) me.getComponent();
                if (Board.prevSq == nextSquare) {
                    if (nextSquare.getBorder() == null)
                        nextSquare.setBorder(b);
                    else nextSquare.removeBorder();
                    Board.prevSq = new Square(10, 10);
                    return;
                } else {
                    nextSquare.setBorder(b);
                    Board.prevSq.removeBorder();
                }

                if (Board.prevSq.getCurrentPiece() == null) {
                    Board.prevSq = nextSquare;
                    return;
                }

                int initialX = Board.prevSq.getXCord();
                int initialY = Board.prevSq.getYCord();

                int destX = nextSquare.getXCord();
                int destY = nextSquare.getYCord();

                Move mv = new Move(initialX, initialY, destX, destY);
                boolean validMove;
                if (mv.getP().getColor().equals(ChessGame.turnColor) && mv.getP().getColor().equals(Board.playerColor)) {
                    if (ChessGame.winner == 'w' || ChessGame.winner == 'b') {
                        validMove = false;
                    } else {
                        validMove = mv.getP().isValidMove(mv);
                    }
                } else {
                    validMove = false;
                }

                boolean dangerOnKing = Board.getKing(ChessGame.turnColor).kingInDanger();

                if (validMove) {
                    nextSquare.removeCurrentPiece(mv.getCapturedP() != null);
                    nextSquare.setCurrentPiece(Board.prevSq.getCurrentPiece());
                    Board.prevSq.removeCurrentPiece(false);
                    if (ChessGame.turnColor.equals("white"))
                        ChessGame.turnColor = "black";
                    else
                        ChessGame.turnColor = "white";

                    if (dangerOnKing) {
                        String prevCol = ChessGame.turnColor.equals("white") ? "black" : "white";
                        if(Board.getKing(prevCol).kingInDanger()) {
                            ChessGame.turnColor = prevCol;
                            nextSquare.removeCurrentPiece(false);
                            nextSquare.setCurrentPiece(Board.backupNextSq.getCurrentPiece());
                            nextSquare.setXCord(Board.backupNextSq.getXCord());
                            nextSquare.setYCord(Board.backupNextSq.getYCord());

                            nextSquare.removeBorder();
                            Board.prevSq.removeBorder();


                            Board.prevSq.removeCurrentPiece(false);
                            Board.prevSq.setCurrentPiece(Board.backupPrevSq.getCurrentPiece());
                            Board.prevSq.setXCord(Board.backupPrevSq.getXCord());
                            Board.prevSq.setYCord(Board.backupPrevSq.getYCord());
                            return;
                        }
                    }

                    try {
                        Client.moveDone(mv);
                        char isFinished = ChessGame.isGameFinished();
                        if (isFinished == 'w' || isFinished == 'b') {
                            ChessGame.displayWinner();
                        } else if (isFinished == 's'){
                            ChessGame.displayStalemate();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Board.prevSq = nextSquare;
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

    void setXCord(int x) { xCord = x; }
    void setYCord(int y) { yCord = y; }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        if (currentPiece == null) return;
        this.currentPiece = currentPiece;
        this.currentPiece.setCurrSquare(this);
        super.add(currentPiece);
    }
    public void removeCurrentPiece(boolean kill) {
        if (this.currentPiece != null) {
            super.removeAll();
            if (kill) Board.pieces.remove(currentPiece);
            this.currentPiece = null;
        }
    }
}
