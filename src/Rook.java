import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(String col, Square sq) {
        super(col, sq);
        this.name = "rook";
        this.ImageFileName = "C:\\Users\\Mahmo\\Desktop\\SocketChess\\img\\" + this.color + "_" + this.name + ".png";
    }
    @Override
    public boolean isValidMove(Move move) {
        int initialX = move.getInitialX();
        int initialY = move.getInitialY();

        int destX = move.getDestX();
        int destY = move.getDestY();

        if (validateCrossings(initialX, initialY, destX, destY)) return false;

        if ((move.getCapturedP() == null)
                || (move.getCapturedP() != null
                && !move.getP().getColor().equals(move.getCapturedP().getColor()))) {
            if (move.getDestY() == move.getInitialY()
                    && move.getDestX() != move.getInitialX()) {
                return true;
            }
            if (move.getDestY() != move.getInitialY()
                    && move.getDestX() == move.getInitialX()) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateCrossings(int initialX, int initialY, int destX, int destY) {
        int DIR_X = (initialX - destX) > 0 ? 0 :
                (initialX - destY) == 0 ? 1 : 2;

        int DIR_Y = (initialY - destY) > 0 ? 0 :
                (initialY - destY) == 0 ? 1 : 2;

        if (DIR_X == 0) {
            for (int i = destX + 1; i < initialX; i++) {
                if (Board.getPieceAtIndex(i, initialY) != null) {
                    return true;
                }
            }
        } else if (DIR_X == 2) {
            for (int i = initialX + 1; i < destX; i++) {
                if (Board.getPieceAtIndex(i, initialY) != null) {
                    return true;
                }
            }
        }

        if (DIR_Y == 0) {
            for (int i = destY + 1; i < initialY; i++) {
                if (Board.getPieceAtIndex(initialX, i) != null) {
                    return true;
                }
            }
        } else if (DIR_Y == 2) {
            for (int i = initialY + 1; i < destY; i++) {
                if (Board.getPieceAtIndex(initialX, i) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
