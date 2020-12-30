import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public boolean canKingBeKilledAt(int dX, int dY) {
        String col = this.color;
        ArrayList<Piece> pcs = new ArrayList<>(Board.pieces);
        for (Piece p : pcs) {
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

    public boolean isKingTrapped() {
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, 1, -1, -1, 0, 1 };

        int initialX = this.getCurrSquare().getXCord();
        int initialY = this.getCurrSquare().getYCord();

        boolean isKingTrapped = true;
        for (int i = 0; i < dx.length; i++) {
            for (int j = 0; j < dx.length; j++) {
                int nextX = initialX + dx[i];
                int nextY = initialY + dy[j];

                if (nextX < 0 || nextX >= 8 || nextY < 0 || nextY >= 8)
                    continue;

                if (!this.isValidMove(new Move(initialX, initialY, nextX, nextY)))
                    continue;

                isKingTrapped &= this.canKingBeKilledAt(nextX, nextY);
            }
        }
        return isKingTrapped;
    }
    public boolean canKingBeSaved(Board b) {
        ArrayList<Piece> allyPieces = Board.pieces
                .stream()
                .filter(p -> p.getColor().equals(this.getColor()))
                .collect(Collectors.toCollection(ArrayList::new));

        boolean canKingBeSaved = false;
        outer_loop:
        for (Piece whitePiece : allyPieces) {
            ArrayList<Move> mvs = whitePiece.getPossibleMoves();
            for (Move mv : mvs) {
                b.executeMove(mv);
                if (!this.kingInDanger()) {
                    canKingBeSaved = true;
                    b.undoMove(mv);
                    break outer_loop;
                }
                b.undoMove(mv);
            }
        }
        return canKingBeSaved;
    }
}
