import java.io.IOException;

public class ChessGame {
    private final Board b;
    public static String myColor;
    public static String turnColor = "white";

    public void executeMove(Move mv) {
        if (b.executeMove(mv)) {
            turnColor = turnColor.equals("white") ? "black" : "white";
            b.refreshFrame();
        }
    }

    public boolean isStalemate() {
        return b.isStalemate();
    }

    public ChessGame(String col) throws IOException {
        b = new Board();
        myColor = col;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ChessGame c = new ChessGame("white");
        c.executeMove(new Move(6, 0, 5, 2));
    }
}
