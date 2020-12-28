public class Bishop extends Piece {
    public Bishop(String col, Square sq) {
        super(col, sq);
        this.name = "bishop";
        this.ImageFileName = "C:\\Users\\Mahmo\\Desktop\\SocketChess\\img\\" + this.color + "_" + this.name + ".png";
    }
    @Override
    public boolean isValidMove(Move move) {

        int initialX = move.getInitialX();
        int initialY = move.getInitialY();

        int destX = move.getDestX();
        int destY = move.getDestY();
        if (validateDiagonals(initialX, initialY, destX, destY)) return false;
        if ((move.getCapturedP() == null)
                || (move.getCapturedP() != null
                && !move.getP().getColor().equals(move.getCapturedP().getColor()))) {
            if (Math.abs(move.getDestY() - move.getInitialY())
                    == Math.abs(move.getDestX() - move.getInitialX())) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateDiagonals(int initialX, int initialY, int destX, int destY) {
        if (initialX < destX && initialY < destY) {
            int intermediateY = initialY + 1;
            for (int i = initialX + 1; i < destX; i++, intermediateY++) {
                if (Board.getPieceAtIndex(i, intermediateY) != null)
                    return true;
            }
        } else if (initialX < destX && initialY > destY) {
            int intermediateY = initialY - 1;
            for (int i = initialX + 1; i < destX; i++, intermediateY--) {
                if (Board.getPieceAtIndex(i, intermediateY) != null)
                    return true;
            }
        } else if (initialX > destX && initialY < destY) {
            int intermediateY = initialY + 1;
            for (int i = initialX - 1; i > destX; i--, intermediateY++) {
                if (Board.getPieceAtIndex(i, intermediateY) != null)
                    return true;
            }
        } else if (initialX > destX && initialY > destY) {
            int intermediateY = initialY - 1;
            for (int i = initialX - 1; i > destX; i--, intermediateY--) {
                if (Board.getPieceAtIndex(i, intermediateY) != null)
                    return true;
            }
        }
        return false;
    }
}
