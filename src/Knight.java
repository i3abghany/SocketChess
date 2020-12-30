public class Knight extends Piece {
    public Knight(String col, Square sq) {
        super(col, sq);
        this.name = "knight";
        this.ImageFileName = "img\\" + this.color + "_" + this.name + ".png";
    }
    @Override
    public boolean isValidMove(Move move) {
        if ((move.getCapturedP() == null)
                || (move.getCapturedP() != null
                && !move.getP().getColor().equals(move.getCapturedP().getColor()))) {
            if ((Math.abs(move.getDestY() - move.getInitialY()) == 1
                    && Math.abs(move.getDestX() - move.getInitialX()) == 2)
                    || (Math.abs(move.getDestY() - move.getInitialY()) == 2
                    && Math.abs(move.getDestX() - move.getInitialX()) == 1)) {
                return true;
            }
        }
        return false;
    }
}
