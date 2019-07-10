package GUI;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Logic.Board;
import Logic.Square;
import Pieces.*;
import Resources.Name;
import Runner.Controller;

public class Display extends JPanel{

	public static final String WHITE_PAWN_PATH = "src/Resources/PieceImages/WhitePawn.png";
	public static final String BLACK_PAWN_PATH = "src/Resources/PieceImages/BlackPawn.png";
	public static final String WHITE_BISHOP_PATH = "src/Resources/PieceImages/WhiteBishop.png";
	public static final String BLACK_BISHOP_PATH = "src/Resources/PieceImages/BlackBishop.png";
	public static final String BLACK_KING_PATH = "src/Resources/PieceImages/BlackKing.png";
	public static final String WHITE_KING_PATH = "src/Resources/PieceImages/WhiteKing.png";
	public static final String BLACK_KNIGHT_PATH = "src/Resources/PieceImages/BlackKnight.png";
	public static final String WHITE_KNIGHT_PATH = "src/Resources/PieceImages/WhiteKnight.png";
	public static final String WHITE_QUEEN_PATH = "src/Resources/PieceImages/WhiteQueen.png";
	public static final String BLACK_QUEEN_PATH = "src/Resources/PieceImages/BlackQueen.png";
	public static final String BLACK_ROOK_PATH = "src/Resources/PieceImages/BlackRook.png";
	public static final String WHITE_ROOK_PATH = "src/Resources/PieceImages/WhiteRook.png";

	public static final int CHECKER_SIZE = 80;
	public static final int FRAME_WIDTH_OFFSET = 6;
	public static final int FRAME_HEIGHT_OFFSET = 29;
	public static final int FRAME_WIDTH = CHECKER_SIZE * 8 + FRAME_WIDTH_OFFSET;
	public static final int FRAME_HEIGHT = CHECKER_SIZE * 8 + FRAME_HEIGHT_OFFSET;

	public static final String BROWN_RGB_VALUE = "10046464";
	public static final Color WHITE_SQUARE_COLOUR = Color.LIGHT_GRAY;
	public static final Color BLACK_SQUARE_COLOUR = Color.decode(BROWN_RGB_VALUE); 


	private Controller controller;
	private Graphics g;
	private Board board;

	public void setBoard(Board board) {
		this.board = board;
	}

	public Display (Controller c){
		controller = c;
		addMouseListener(controller);
		JFrame frame = new JFrame();
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.getContentPane().add(this);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chess");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void paint(Graphics g){
		this.g = g;

		g.setColor(WHITE_SQUARE_COLOUR);
		g.fillRect(0, 0, CHECKER_SIZE * 8, CHECKER_SIZE * 8);

		g.setColor(BLACK_SQUARE_COLOUR);
		//Paints the checkers. Checkers are black when the row index is even and the column index is odd or the row index is odd and the column index is even
		//i == rows j == columns
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i % 2 == 0 && j % 2 == 1) || (i % 2== 1 && j % 2 == 0)) {
					g.fillRect(i*CHECKER_SIZE, j*CHECKER_SIZE, CHECKER_SIZE, CHECKER_SIZE);
				}
			}
		}
		if (board != null) {

		}

		drawAllPieces();
		drawSquareHighlights();
	}
	
	public void drawSquareHighlights(){
		Square selectedSquare = controller.getSelectedSquare();
		
		try {
			System.out.println("the selected piece is " + selectedSquare.getPiece() + " at position Row: " + selectedSquare.getRow() + " Column: " + selectedSquare.getColumn());
			ArrayList<Square> validMoves = selectedSquare.getPiece().getPossibleMoves();
			
			
			System.out.println("this piece has " + validMoves.size() + " valid moves");
			
			for (Square s : validMoves) {
				g.setColor(Color.YELLOW);
				g.drawRect(s.getColumn()*CHECKER_SIZE, s.getRow()*CHECKER_SIZE, CHECKER_SIZE, CHECKER_SIZE);
				//make rectangle thicker
				g.drawRect((s.getColumn()*CHECKER_SIZE)+1, (s.getRow()*CHECKER_SIZE)+1, CHECKER_SIZE-2, CHECKER_SIZE-2);
				g.drawRect((s.getColumn()*CHECKER_SIZE)+2, (s.getRow()*CHECKER_SIZE)+2, CHECKER_SIZE-4, CHECKER_SIZE-4);
			}
		} catch (NullPointerException e) {
			//do nothing. Null pointer here means selectedSquare has not been initialized yet.
		}
	}

	/**
	 * Draws all pieces on the board
	 */
	public void drawAllPieces() {
		Square[][] squares = board.getSquares();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = squares[i][j].getPiece();
				if (piece != null) {
					drawPiece(findFile(piece),squares[i][j]);
				}
			}
		}
	}
	
	/**
	 * @param piece The piece to find the image file of
	 * @return returns Image File of the desired piece
	 */
	public File findFile(Piece piece) {
		File pieceFile = null;

		if (piece instanceof Pawn) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_PAWN_PATH);
			} else {
				pieceFile = new File(BLACK_PAWN_PATH);
			}
		} else if (piece instanceof Bishop) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_BISHOP_PATH);
			} else {
				pieceFile = new File(BLACK_BISHOP_PATH);
			}
		} else if (piece instanceof Knight) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_KNIGHT_PATH);
			} else {
				pieceFile = new File(BLACK_KNIGHT_PATH);
			}
		} else if (piece instanceof Rook) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_ROOK_PATH);
			} else {
				pieceFile = new File(BLACK_ROOK_PATH);
			}
		} else if (piece instanceof King) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_KING_PATH);
			} else {
				pieceFile = new File(BLACK_KING_PATH);
			}
		} else if (piece instanceof Queen) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_QUEEN_PATH);
			} else {
				pieceFile = new File(BLACK_QUEEN_PATH);
			}
		}
		return pieceFile;
	}

	/**
	 * Draws the image contained by the file on the specified square
	 * @param pieceFile the file containing the image to draw
	 * @param square the square to draw the image on
	 */
	public void drawPiece(File pieceFile, Square square) {
		Image pieceImage;
		try {
			pieceImage = ImageIO.read(pieceFile);
			Image scaledImage = pieceImage.getScaledInstance(CHECKER_SIZE, CHECKER_SIZE, 0);
			g.drawImage(scaledImage, transformCoordinate(square.getColumn()), transformCoordinate(square.getRow()), null);
		} catch (IOException e) {
			System.out.println("Could not load piece image resource");
			e.printStackTrace();
		} 
	}

	/**
	 * Transforms a board coordinate into an absolute coordinate on the JFrame
	 * @param coord the board coordinate to transform
	 * @return returns the absolute position of the coord
	 */
	public int transformCoordinate(int coord) {
		return coord * CHECKER_SIZE;
	}

}