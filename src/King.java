public class King extends Piece {
    public King(String col, Square sq) {
        super(col, sq);
        this.name = "King";
        this.ImageFileName = "C:\\Users\\Mahmo\\Desktop\\SocketChess\\img\\" + this.color + "_" + this.name + ".png";
    }

    @Override
    public boolean isValidMove(Move move) {
        if (canKingBeKilledAt(move.getDestX(), move.getDestY()))
            return false;

        if ((move.getCapturedP() == null)
                || (move.getCapturedP() != null
                && !move.getP().getColor().equals(move.getCapturedP().getColor()))) {
            if (Math.abs(move.getDestY() - move.getInitialY()) <= 1
                    && Math.abs(move.getDestX() - move.getInitialX()) <= 1) {
                return true;
            }
        }
        return false;
    }

    private boolean canKingBeKilledAt(int dX, int dY) {
        String col = this.color;
        for (Piece p : Board.pieces) {
            if (p instanceof King || p.getColor().equals(col)) {
                continue;
            }
            Move mv = new Move(p.getCurrSquare().getXCord(), p.getCurrSquare().getYCord(), dX, dY);
            if (p.isValidMove(mv)) {
                return true;
            }
        }
        return false;
    }

    public boolean kingInDanger() {
        return canKingBeKilledAt(this.getCurrSquare().getXCord(), this.getCurrSquare().getYCord());
    }
}
