public class Rook extends Piece {
    public Rook(String col, Square sq) {
        super(col, sq);
        this.name = "rook";
        this.ImageFileName = "C:\\Users\\Mahmo\\Desktop\\SocketChess\\img\\" + this.color + "_" + this.name + ".png";
    }
    @Override
    public boolean isValidMove(Move move) {
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

        return false;    }
}
