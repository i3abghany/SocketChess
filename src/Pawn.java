public class Pawn extends Piece {

    public Pawn(String col, Square sq) {
        super(col, sq);
        this.name = "pawn";
        this.ImageFileName = "C:\\Users\\Mahmo\\Desktop\\SocketChess\\img\\" + this.color + "_" + this.name + ".png";
    }

    @Override
    public boolean isValidMove(Move move) {
        switch (color) {
            case "white":
                if (Math.abs(move.getDestX() - move.getInitialX()) == 1
                        && move.getDestY() - move.getInitialY() == 1
                        && move.getCapturedP() != null
                        && "black".equals(move.getCapturedP().getColor())) {
                    return true;
                }
                break;
            case "black":
                    if (Math.abs(move.getDestX() - move.getInitialX()) == 1
                            && move.getDestY() - move.getInitialY() == -1
                            && move.getCapturedP() != null
                            && "white".equals(move.getCapturedP().getColor())) {
                    return true;
                }
                break;
        }

        switch (color) {
            case "white":
                if (move.getInitialY() == 2
                        && move.getDestX() == move.getInitialX()
                        && move.getDestY() - move.getInitialY() <= 2
                        && move.getDestY() - move.getInitialY() >= 1
                        && move.getCapturedP() == null) {
                    return true;
                }
                break;
            case "black":
                if (move.getInitialY() == 7
                        && move.getDestX() == move.getInitialX()
                        && move.getDestY() - move.getInitialY() >= -2
                        && move.getDestY() - move.getInitialY() <= -1
                        && move.getCapturedP() == null) {
                    return true;
                }
                break;
        }

        switch (color) {
            case "white":
                if (move.getDestX() == move.getInitialX()
                        && move.getDestY() - move.getInitialY() == 1
                        && move.getCapturedP() == null) {
                    return true;
                }
                break;
            case "black":
                if (move.getDestX() == move.getInitialX()
                        && move.getDestY() - move.getInitialY() == -1
                        && move.getCapturedP() == null) {
                    return true;
                }
                break;
        }
        return false;
    }
}
