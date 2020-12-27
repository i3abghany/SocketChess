public class PiecesFactory {
    public static Piece getPiece(String name, String col) {
        Piece p = null;
        switch(name) {
            case "pawn" -> p = new Pawn(col, null);
            case "bishop" -> p = new Bishop(col, null);
            case "knight" -> p = new Knight(col, null);
            case "queen" -> p = new Queen(col, null);
            case "king" -> p = new King(col, null);
            case "rook" -> p = new Rook(col, null);
        }
        return p;
    }
}
