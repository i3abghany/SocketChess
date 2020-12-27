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
    static private final int DIM_N = 8;
    static private final int DIM_M = 8;
    static private final int WIDTH_MARGIN = 15;
    static private final int HEIGHT_MARGIN = 38;
    static private Square[][] squares;
    static private ArrayList<Piece> pieces;

    public Board() throws IOException {
        squares = new Square[DIM_N][DIM_M];
        initSquares();
        initPieces();
        super.setSize(DIM * Square.SQUARE_WIDTH + WIDTH_MARGIN, DIM * Square.SQUARE_WIDTH + HEIGHT_MARGIN);
        super.add(new JPanel());
        super.setVisible(true);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initSquares() {
        for (int i = 0; i < DIM_M; i++) {
            for (int j = 0; j < DIM_N; j++) {
                squares[j][i] = new Square(i, j); // reversed j and i !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                super.add(squares[j][i]);
            }
        }
    }

    private void initPieces() throws IOException {
        pieces = new ArrayList<>();
//        testInit();
        initPawnRows();
        initBishops();
        initKnights();
        initQueens();
        initKings();
        initRooks();
    }

    private void testInit() throws IOException {
        for (int i = 0; i < DIM_N; i++) {
            for (int j = 0; j < DIM_M; j++) {
                initPieceCell("rook", squares[i][j], "white");
            }
        }
    }

    private void initRooks() throws IOException {
        initPieceCell("rook", squares[0][0], "white");
        initPieceCell("rook", squares[0][7], "white");

        initPieceCell("rook", squares[7][0], "black");
        initPieceCell("rook", squares[7][7], "black");
    }

    private void initKings() throws IOException {
        initPieceCell("king", squares[0][3], "white");
        initPieceCell("king", squares[7][3], "black");
    }

    private void initQueens() throws IOException {
        initPieceCell("queen", squares[0][4], "white");
        initPieceCell("queen", squares[7][4], "black");
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
        sq.setCurrentPiece(p);
        sq.add(p);
        sq.getCurrentPiece().setOpaque(false);
        BufferedImage img = ImageIO.read(new File(p.getImageFileName()));
        img = Scalr.resize(img, 40);
        JLabel picLabel = new JLabel(new ImageIcon(img));
        sq.getCurrentPiece().add(picLabel);
    }

    private void initPawnRows() throws IOException {
        for (int i = 0; i < DIM; i++) {
            initPieceCell("pawn", squares[1][i], "white");
        }
        for (int i = 0; i < DIM; i++) {
            initPieceCell("pawn", squares[6][i], "black");
        }
    }

    static public Piece getPieceAtIndex(int i, int j) {
        return squares[i][j].getCurrentPiece();
    }

    public static void main(String[] args) throws IOException {
        Board b = new Board();
        System.out.println(Board.getPieceAtIndex(7, 7).name);

    }
}