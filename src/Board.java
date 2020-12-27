import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// RANKS ARE ROWS.
// FILES ARE COLS.

public class Board extends JFrame {
    static private final int DIM = 8;
    static private final int WIDTH_MARGIN = 15;
    static private final int HEIGHT_MARGIN = 38;
    static private Square[][] squares;
    static private ArrayList<Piece> pieces;

    public Board() throws IOException {
        squares = new Square[DIM][DIM];
        initSquares();
        initPieces();
        super.setSize(DIM * Square.SQUARE_WIDTH + WIDTH_MARGIN, DIM * Square.SQUARE_WIDTH + HEIGHT_MARGIN);
        super.setVisible(true);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initSquares() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                squares[j][i] = new Square(i, j); // reversed j and i !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                super.add(squares[j][i]);
            }
        }
    }

    private void initPieces() throws IOException {
        pieces = new ArrayList<>();
        initPawnRows();
        initBishops();
        initKnights();
    }

    private void initKnights() throws IOException {
        initPieceCell("knight", squares[0][1], "white");
        initPieceCell("knight", squares[0][6], "white");

        initPieceCell("knight", squares[7][1], "black");
        initPieceCell("knight", squares[7][6], "black");
    }

    private void initBishops() throws IOException {
        initPieceCell("bishop", squares[0][2], "white");
        initPieceCell("bishop", squares[0][5], "white");

        initPieceCell("bishop", squares[7][2], "black");
        initPieceCell("bishop", squares[7][5], "black");
    }

    public void initPieceCell(String name, Square sq, String col) throws IOException {
        Piece p = PiecesFactory.getPiece(name, col);
        p.setCurrSquare(sq);
        pieces.add(p);
        BufferedImage img = ImageIO.read(new File(p.getImageFileName()));
        img = Scalr.resize(img, 40);
        JLabel picLabel = new JLabel(new ImageIcon(img));
        sq.add(picLabel);
    }

    private void initPawnRows() throws IOException {
        for (int i = 0; i < DIM; i++) {
            Pawn p = new Pawn("white", squares[1][i]);
            pieces.add(p);
            BufferedImage pawnImage = ImageIO.read(new File(p.getImageFileName()));
            pawnImage = Scalr.resize(pawnImage, 40);
            JLabel picLabel = new JLabel(new ImageIcon(pawnImage));
            squares[1][i].add(picLabel);
        }
        for (int i = 0; i < DIM; i++) {
            Pawn p = new Pawn("black", squares[6][i]);
            pieces.add(p);
            BufferedImage pawnImage = ImageIO.read(new File(p.getImageFileName()));
            pawnImage = Scalr.resize(pawnImage, 40);
            JLabel picLabel = new JLabel(new ImageIcon(pawnImage));
            squares[6][i].add(picLabel);
        }
    }

    static public Piece getPieceAtIndex(int i, int j) {
        return squares[i][j].getCurrentPiece();
    }

    public static void main(String[] args) throws IOException {
        new Board();
    }
}
