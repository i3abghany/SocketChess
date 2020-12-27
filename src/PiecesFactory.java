public class PiecesFactory {
    public static Piece getPiece(String name, String col) {
        Piece p = null;
        switch(name) {
            case "pawn" -> p = new Pawn(col, null);
            case "bishop" -> p = new Bishop(col, null);
            case "knight" -> p = new Knight(col, null);
        }
        return p;
    }
}
