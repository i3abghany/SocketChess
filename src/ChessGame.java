import javax.swing.*;
import java.io.IOException;

public class ChessGame {
    private static Board b;
    public static String myColor;
    public static String turnColor = "white";
    public static char winner = 'n';

    public void executeMove(Move mv) {
        if (b.executeMove(mv)) {
            turnColor = turnColor.equals("white") ? "black" : "white";
            b.refreshFrame();
            char isFinished = isGameFinished();
            if (isFinished == 'w' || isFinished == 'b') {
                ChessGame.displayWinner();
            } else if (isFinished == 's'){
                ChessGame.displayStalemate();
            }
            if (isFinished == 'w' || isFinished == 'b') {
                Board.whoseTurn.setText(isFinished == 'w' ? "White" : "Black" + " has won!");
            } else {
                Board.whoseTurn.setText(turnColor + "'s turn!");
            }
            b.refreshFrame();
        }
    }

    public static char isGameFinished() {
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
        b = new Board(col);
        Board.playerColor = myColor;
    }

    public static void displayWinner() {
        JLabel jl = new JLabel("Game has finished! the winner is " + (winner == 'w' ? "white" : "black") + "!");
        JOptionPane.showMessageDialog(ChessGame.b, jl, "Winner!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void displayStalemate() {
        JLabel jl = new JLabel("Game has finished! It was a stalemate.");
        JOptionPane.showMessageDialog(ChessGame.b, jl, "Stalemate!", JOptionPane.INFORMATION_MESSAGE);
    }
}
