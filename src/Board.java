import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board extends JFrame {
    static private final int WIDTH_MARGIN = 300;
    static private final int HEIGHT_MARGIN = 38;
    static private Square[][] squares;
    static public ArrayList<Piece> pieces;
    static public final int DIM = 8;
    public static Square prevSq = null;
    public static Square backupPrevSq = null;
    public static Square backupNextSq = null;
    public static String playerColor;
    public static JLabel whoseTurn;

    public Board(String col) throws IOException {
        squares = new Square[DIM][DIM];
        initSquares();
        initPieces();
        super.setSize(DIM * Square.SQUARE_WIDTH + WIDTH_MARGIN, DIM * Square.SQUARE_WIDTH + HEIGHT_MARGIN);
        super.add(new JPanel());
        prevSq = new Square(10, 10);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setTitle("Chess. " + col + "'s Window.");
        super.setVisible(true);
    }

    private void initSquares() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                squares[j][i] = new Square(i, j); // reversed j and i !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                super.add(squares[j][i]);
            }
        }
        whoseTurn = new JLabel(ChessGame.turnColor + "'s Turn!");
        whoseTurn.setFont(new Font(null, Font.BOLD, 26));
        Square sq = new Square(9, 3, 200, 100);
        sq.add(whoseTurn);
        super.add(sq);
    }

    public void refreshFrame() {
        super.invalidate();
        super.revalidate();
        super.repaint();
    }

    private void initPieces() throws IOException {
        pieces = new ArrayList<>();
        initPawnRows();
        initBishops();
        initKnights();
        initQueens();
        initKings();
        initRooks();
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

    public static King getKing(String col) {
        for (Piece p : pieces) {
            if (p instanceof King && p.color.equals(col))
                return (King) p;
        }
        return null;
    }

    public static JComponent cloneSwingComponent(JComponent c) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(c);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (JComponent) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean executeMove(Move mv) {
        Square initSquare = squares[mv.getInitialY()][mv.getInitialX()];
        Square nextSquare = squares[mv.getDestY()][mv.getDestX()];

        Board.backupPrevSq = (Square)Board.cloneSwingComponent(initSquare);
        Board.backupNextSq = (Square)Board.cloneSwingComponent(nextSquare);

        boolean validMove;
        if (mv.getP().getColor().equals(ChessGame.turnColor)) {
            validMove = mv.getP().isValidMove(mv);
        } else {
            validMove = false;
        }

        if (validMove) {
            nextSquare.removeCurrentPiece(mv.getCapturedP() != null);
            nextSquare.setCurrentPiece(initSquare.getCurrentPiece());
            initSquare.removeCurrentPiece(false);
            return true;
        }

        return false;
    }

    public void undoMove(Move mv) {
        Square initSquare = squares[mv.getInitialY()][mv.getInitialX()];
        Square nextSquare = squares[mv.getDestY()][mv.getDestX()];

        nextSquare.removeCurrentPiece(false);
        nextSquare.setCurrentPiece(Board.backupNextSq.getCurrentPiece());
        nextSquare.setXCord(Board.backupNextSq.getXCord());
        nextSquare.setYCord(Board.backupNextSq.getYCord());

        initSquare.removeCurrentPiece(false);
        initSquare.setCurrentPiece(Board.backupPrevSq.getCurrentPiece());
        initSquare.setXCord(Board.backupPrevSq.getXCord());
        initSquare.setYCord(Board.backupPrevSq.getYCord());
    }

    static public Piece getPieceAtIndex(int i, int j) {
        return squares[j][i].getCurrentPiece();
    }

    public boolean isStalemate() {
        boolean whiteStalled = (Board.getKing("white").isKingTrapped() && !Board.getKing("white").kingInDanger());
        boolean blackStalled = (Board.getKing("black").isKingTrapped() && !Board.getKing("black").kingInDanger());

        boolean canWhiteMove = canTeamMove("white");
        boolean canBlackMove = canTeamMove("black");

        boolean stalemate = (whiteStalled && !canWhiteMove) || (blackStalled && !canBlackMove);

        if (stalemate) ChessGame.winner = 's';
        return stalemate;
    }

    public boolean canTeamMove(String col) {
        ArrayList<Piece> pieces = Board.pieces
                .stream()
                .filter(p -> p.getColor().equals(col))
                .collect(Collectors.toCollection(ArrayList::new));
        boolean feasibleMoves = false;
        for (Piece p : pieces) {
            if (p instanceof King) continue;
            feasibleMoves |= p.canMove();
        }
        return feasibleMoves;
    }

    public char isGameFinished() {
        King whiteKing = Board.getKing("white");
        King blackKing = Board.getKing("black");

        if (isStalemate()) {
            return ChessGame.winner;
        }

        if (!whiteKing.kingInDanger() && !blackKing.kingInDanger()) {
            return 'n';
        }

        boolean whiteKingCantMove = whiteKing.isKingTrapped() && whiteKing.kingInDanger();
        boolean canWhiteKingBeSaved = false ;
        if (whiteKingCantMove) {
            canWhiteKingBeSaved = whiteKing.canKingBeSaved(this);
        }

        if (whiteKingCantMove && !canWhiteKingBeSaved) {
            ChessGame.winner = 'b';
            return ChessGame.winner;
        }

        boolean blackKingCantMove = whiteKing.isKingTrapped() && whiteKing.kingInDanger();
        boolean canBlackKingBeSaved = false;
        if (blackKingCantMove) {
            canBlackKingBeSaved = blackKing.canKingBeSaved(this);
        }
        if (blackKingCantMove && !canBlackKingBeSaved) {
            ChessGame.winner = 'w';
        }
        return ChessGame.winner;
    }
}

