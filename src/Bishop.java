public class Bishop extends Piece {
    public Bishop(String col, Square sq) {
        super(col, sq);
        this.name = "bishop";
        this.ImageFileName = "C:\\Users\\Mahmo\\Desktop\\SocketChess\\img\\" + this.color + "_" + this.name + ".png";
    }
    @Override
    public boolean isValidMove(Move move) {
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
}
