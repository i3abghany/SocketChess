import java.io.IOException;

public class ChessGame {
    private Board b;
    public static String turnColor = "white";
    private boolean isStalemate() {
        return b.isStalemate();
    }

    public ChessGame() throws IOException {
        b = new Board();
    }
}
