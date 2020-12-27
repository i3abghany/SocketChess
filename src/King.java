public class King extends Piece {
    public King(String col, Square sq) {
        super(col, sq);
        this.name = "King";
        this.ImageFileName = "C:\\Users\\Mahmo\\Desktop\\SocketChess\\img\\" + this.color + "_" + this.name + ".png";
    }

    @Override
    public boolean isValidMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedP() == null)
                || (move.getCapturedP() != null
                && !move.getP().getColor().equals(move.getCapturedP().getColor()))) {
            // one square executeMove
            if (Math.abs(move.getDestY() - move.getInitialY()) <= 1
                    && Math.abs(move.getDestX() - move.getInitialX()) <= 1) {
                return true;
            }
        }
        return false;
    }
}
