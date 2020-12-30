import javax.swing.*;
import java.io.IOException;

public class ChessGame {
    private final Board b;
    public static String myColor;
    public static String turnColor = "white";
    public static char winner = 'n';

    public void executeMove(Move mv) {
        if (b.executeMove(mv)) {
            turnColor = turnColor.equals("white") ? "black" : "white";
            b.refreshFrame();
        }
    }

    public char isGameFinished() {
        return b.isGameFinished();
    }

    public Board getBoard() {
        return b;
    }

    public void undoMove(Move mv) {
        b.undoMove(mv);
        b.refreshFrame();
    }

    public boolean isStalemate() {
        return b.isStalemate();
    }

    public ChessGame(String col) throws IOException {
        myColor = col;
        b = new Board();
        Board.playerColor = myColor;
    }

    public void displayWinner() {
        JLabel jl = new JLabel("Game has finished! the winner is the " + (winner == 'w' ? "white" : "black") + "!");
        JOptionPane.showMessageDialog(b, jl, "Winner!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayStalemate() {
        JLabel jl = new JLabel("Game has finished! It was a stalemate.");
        JOptionPane.showMessageDialog(b, jl, "Stalemate!", JOptionPane.INFORMATION_MESSAGE);
    }
}
