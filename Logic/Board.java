package Logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import Pieces.*;
import Resources.Name;
import Runner.Controller;

/**
 * This class handles the chess board, including board state, win conditions, check, and is composed of all squares and pieces.
 * 
 * @author Fred Reinink
 */
public class Board implements Serializable{
	
	public static final int NUM_ROWS = 8;
	public static final int NUM_COLS = 8;
	
	//Row and column #s increase starting from the top left corner (0,0).
	public static final int WHITE_KING_ROW = 7;
	public static final int WHITE_PAWN_ROW = 6;
	public static final int WHITE_ENPASSENT_ROW = 5;

	public static final int ROW_4 = 4;
	public static final int ROW_3 = 3;
	
	public static final int BLACK_ENPASSENT_ROW = 2;
	public static final int BLACK_PAWN_ROW = 1;
	public static final int BLACK_KING_ROW = 0;

	public static final int COL_1 = 1;
	public static final int COL_2 = 2;
	public static final int COL_3 = 3;
	public static final int COL_4 = 4;
	public static final int COL_5 = 5;
	public static final int COL_6 = 6;
	public static final int COL_7 = 7;

	public static final Coordinate WHITE_KING_STARTING_POSITION = new Coordinate(WHITE_KING_ROW, 4);
	public static final Coordinate BLACK_KING_STARTING_POSITION = new Coordinate(BLACK_KING_ROW, 4);

	public static final Coordinate WHITE_KING_LEFT_CASTLE_POSITION = new Coordinate(WHITE_KING_ROW, 2);
	public static final Coordinate WHITE_KING_RIGHT_CASTLE_POSITION = new Coordinate(WHITE_KING_ROW, 6);
	public static final Coordinate BLACK_KING_LEFT_CASTLE_POSITION = new Coordinate(BLACK_KING_ROW, 2);
	public static final Coordinate BLACK_KING_RIGHT_CASTLE_POSITION = new Coordinate(BLACK_KING_ROW, 6);

	public static final Coordinate WHITE_ROOK_LEFT_CASTLE_POSITION = new Coordinate(WHITE_KING_ROW, 3);
	public static final Coordinate WHITE_ROOK_RIGHT_CASTLE_POSITION = new Coordinate(WHITE_KING_ROW, 5);
	public static final Coordinate BLACK_ROOK_LEFT_CASTLE_POSITION = new Coordinate(BLACK_KING_ROW, 3);
	public static final Coordinate BLACK_ROOK_RIGHT_CASTLE_POSITION = new Coordinate(BLACK_KING_ROW, 5);
	

	private transient Controller controller;

	private Square[][] squares;
	private Player white;
	private Player black;
	
	private HashMap<String,Integer> pastStates = new HashMap<String,Integer>(); //past board states mapped to values corresponding to the number of times that state has occurred
	
	
	public Controller getController() {
		return controller;
	}

	public Player getWhite() {
		return white;
	}

	public Player getBlack() {
		return black;
	}

	/**
	 * @return returns the 2d array of squares that make up this board.
	 */
	public Square[][] getSquares() {
		return squares;
	}
	
	/**
	 * Clears all mappings in the pastStates map. Used for tracking board repetitions. 
	 * For efficiency, call this method any time an irreversible move occurs.
	 */
	public void clearPastStates() {
		pastStates.clear();
	}
	
	/**
	 * Builds a board out of squares and places pieces in their designated starting position.
	 * 
	 * @param white the player with Name white.
	 * @param black the player with Name black.
	 * @param controller the controller running this instance of the board.
	 */
	public Board(Player white, Player black, Controller controller) {
		this.controller = controller;
		this.white = white;
		this.black = black;

		squares = new Square[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < NUM_ROWS; i ++) {
			for (int j = 0; j < NUM_COLS; j ++) {
				squares[i][j] = new Square(new Coordinate(i,j));
			}
		}
		placeOrderedPieces(white);
		placeOrderedPieces(black);
	}
	
	/**
	 * Builds a board out of squares with only kings in their designated starting position.
	 */
	public Board(Player white, Player black) {
		this.white = white;
		this.black = black;

		squares = new Square[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < NUM_ROWS; i ++) {
			for (int j = 0; j < NUM_COLS; j ++) {
				squares[i][j] = new Square(new Coordinate(i,j));
			}
		}
		
		placePiece(white.getKing(), WHITE_KING_STARTING_POSITION);
		placePiece(black.getKing(), BLACK_KING_STARTING_POSITION);
	}

	/**
	 * Updates the board state by updating possible/valid moves, setting check variables and checking for win conditions.
	 */
	public void update() {
		setPossibleMoves();
		setCheck();
		setValidMoves();
		winConditionHandler();
	}
	
	/**
	 * Adds a past board state to the pastStates hashMap and increments the number of times the board state has been encountered. 
	 * If this board state has never been encountered before, initializes it in the pastStates hashMap with 1 occurrence.
	 * Returns true if the same board state is set to be added for the third time, meaning a three-fold repetition has occurred. 
	 * 
	 * @return Returns true when a three-fold repetition has occurred. False otherwise.
	 */
	public boolean addState() {
		boolean threeFoldRepetition = false;

		boolean alreadyAdded = false;
		
		String stateEncoding = getStateEncoding();
		for (String key : pastStates.keySet()) {
			if (stateEncoding.equals(key)) { //if this board state has already occurred
				alreadyAdded = true;
				if (pastStates.get(key) == 2) {
					threeFoldRepetition = true;
				} else {
					pastStates.put(key, pastStates.get(key) + 1);
				}
			}
		}
		
		if (!alreadyAdded) {
			pastStates.put(stateEncoding, 1);
		}
		
		return threeFoldRepetition;
	}

	/**
	 * Evaluates the board state for checkmate/draws and calls the game-ending methods in controller.
	 */
	public void winConditionHandler() {
		if (controller != null) {    //null check is for boards that are created to test whether a move results in check... 
			if (!white.hasAValidMove()) {    //These boards do not have an initialized value for controller and do not need to test for win conditions.
				if (white.isInCheck()) {
					controller.checkmate(black);
				} else {
					controller.draw("Stalemate");
				}
			} else if (!black.hasAValidMove()) {
				if (black.isInCheck()) {
					controller.checkmate(white);
				} else {
					controller.draw("Stalemate");
				}
			}
		}
		
		if (addState()) {
			controller.draw("Three-Fold Repetition");
		}
	}

	/**
	 * Updates the board state without setting valid moves. Use this method on a copied test board to determine if a move places the owner in check.
	 */
	public void testBoardUpdate() {
		setPossibleMoves();
		setCheck();
	}

	/**
	 * Determines whether either player is currently in check and sets the inCheck variable in Player accordingly.
	 */
	public void setCheck() {
		white.setinCheck(false);
		black.setinCheck(false);

		Coordinate whiteKingPosition = white.getKing().getPosition();

		for (Piece piece : black.getPieces()) {
			for (Square square : piece.getAggressiveMoves()) {
				if (square.getPosition().equals(whiteKingPosition)) {
					white.setinCheck(true);
				} 
			}
		}

		Coordinate blackKingPosition = black.getKing().getPosition();

		for (Piece piece : white.getPieces()) {
			for (Square square : piece.getAggressiveMoves()) {
				if (square.getPosition().equals(blackKingPosition)) {
					black.setinCheck(true);
				} 
			}
		}
	}

	/**
	 * Determines whether the opposing player is attacking the specified square.
	 * 
	 * @param name the name of the friendly player.
	 */
	public boolean isAttacked(Name name, Square squareToCheck) {
		
		if (name == Name.white) {
			for (Piece piece : black.getPieces()) {
				for (Square square : piece.getAggressiveMoves()) {
					if (square.getPosition().equals(squareToCheck.getPosition())) {
						return true;
					}
				}
			}
		} else {
			for (Piece piece : white.getPieces()) {
				for (Square square : piece.getAggressiveMoves()) {
					if (square.getPosition().equals(squareToCheck.getPosition())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Sets all possible moves for both players.
	 * A possible move is a move that a piece can make purely based on a piece's rules for movement (includes potentially illegal moves where a move places the owner in check).
	 */
	public void setPossibleMoves() {
		white.setPossibleMoves(this);
		black.setPossibleMoves(this);
		white.setPossibleMoves(this);
	}

	/**
	 * Sets all valid moves for both players. This function trims possible moves that are illegal and so should only be called after setPossibleMoves().
	 * A valid move is a move that a player is legally allowed to make.
	 */
	public void setValidMoves() {
		white.setValidMoves(this);
		black.setValidMoves(this);
		white.setValidMoves(this);
	}

	/**
	 * Places pieces of a specified player on the board. Assumes that the player's piece list adheres to the following encoding:
	 * [pawn, pawn, pawn, pawn, pawn, pawn, pawn, pawn, rook, knight, bishop, queen, king, bishop, knight, rook]
	 * 
	 * @param player the player whose pieces are to be placed.
	 */
	public void placeOrderedPieces(Player player) {
		int pawnRow;
		int kingRow;

		if (player.getName() == Name.white) {
			pawnRow = WHITE_PAWN_ROW;
			kingRow = WHITE_KING_ROW;
		} else {
			pawnRow = BLACK_PAWN_ROW;
			kingRow = BLACK_KING_ROW;
		}

		ArrayList<Piece> pieces = player.getPieces();

		int j = 8; //major pieces start on the 8th index in the piece list encoding.
		for (int i = 0; i < NUM_ROWS; i++) {
			Piece pawnToPlace = pieces.get(i);
			squares[pawnRow][i].setPiece(pawnToPlace);
			
			Piece majorPieceToPlace = pieces.get(j);
			squares[kingRow][i].setPiece(majorPieceToPlace);
			j++;
		}	
	}

	/**
	 * Sets all enPassentAvailable variables to false
	 */
	public void resetEnPassent() {
		for (int i = 0; i < NUM_ROWS; i++) {
			squares[WHITE_ENPASSENT_ROW][i].setEnPassentAvailable(false);;
			squares[BLACK_ENPASSENT_ROW][i].setEnPassentAvailable(false);;
		}
	}

	/**
	 * Promotes the specified pawn after getting user input for desired promotion type.
	 * Creates and places the promoted piece.
	 * 
	 * @param pawn the pawn to promote.
	 */
	public void promotePawn(Pawn pawn) {
		if (controller != null) {    //Pawn promotions are irrelevant on test boards...
			Player owner = pawn.getOwner();    //These boards do not have an initialized value for controller and do not need to handle promotions.
			Piece selectedPiece = controller.promotePawn(owner);
			owner.addPiece(selectedPiece);
			placePiece(selectedPiece, pawn.getPosition());
		}
	}

	/**
	 * Places one piece on the square with the designated coordinates.
	 * 
	 * @param pieceToPlace the piece to be placed.
	 * @param newPosition the coordinate of the position to place the piece at.
	 */
	public void placePiece(Piece pieceToPlace, Coordinate newPosition) {
		Square newSquare = squares[newPosition.getRow()][newPosition.getColumn()];
		if (pieceToPlace != null && newSquare.getPiece() != null) {	    //irreversible move (piece capture) so call clearPastStates()
			clearPastStates();
		}
		
		newSquare.setPiece(pieceToPlace);
	}
	
	/**
	 * Determines if there is a piece on the square.
	 * 
	 * @param square the square to check.
	 * @return true if there is a piece, false otherwise.
	 */
	public static boolean containsPiece(Square square) {
		if (square.getPiece() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if there is an enemy piece on the square.
	 * 
	 * @param square the square to check.
	 * @return true if there is an enemy piece, false otherwise.
	 */
	public static boolean containsEnemyPiece(Square square, Name name) {
		if ((square.getPiece() != null) && (!(square.getPiece().getOwner().getName() == name))) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if there is a friendly piece on the square.
	 * 
	 * @param square the square to check.
	 * @return true if there is a friendly piece, false otherwise.
	 */
	public static boolean containsFriendlyPiece(Square square, Name name) {
		if ((square.getPiece() != null) && (square.getPiece().getOwner().getName() == name)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Calculates and returns an encoding that represents this board's unique state. Does not include castling rights (because pastStates is cleared every time castling rights change).
	 * 
	 * From left to right and top to bottom, squares are represented by the piece occupying that square as well as their en Passent availability. 
	 * 
	 * Empty square: 0
	 * 
	 * White Pawn: a
	 * White Rook: b
	 * White Knight: c
	 * White Bishop: d
	 * White Queen: e
	 * White King: f
	 * 
	 * Black Pawn: g
	 * Black Rook: h
	 * Black Knight: i
	 * Black Bishop: j
	 * Black Queen: k
	 * Black King: l
	 * 
	 * If en passent is available for a particular square, z is appended to the square's encoding.
	 * 
	 * @return a string representing the board's unique state.
	 */
	public String getStateEncoding() {
		String stateEncoding = ""; 
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_ROWS; j++) {
				Square square = squares[i][j];
				Piece piece = square.getPiece();
				
				if (piece == null) {
					stateEncoding += "0";
				} else if (piece instanceof Pawn) {
					if (piece.getOwner().getName() == Name.white) {
						stateEncoding += "a";
					} else {
						stateEncoding += "g";
					}
				} else if (piece instanceof Rook) {
					if (piece.getOwner().getName() == Name.white) {
						stateEncoding += "b";
					} else {
						stateEncoding += "h";
					}
				} else if (piece instanceof Knight) {
					if (piece.getOwner().getName() == Name.white) {
						stateEncoding += "c";
					} else {
						stateEncoding += "i";
					}
				} else if (piece instanceof Bishop) {
					if (piece.getOwner().getName() == Name.white) {
						stateEncoding += "d";
					} else {
						stateEncoding += "j";
					}
				} else if (piece instanceof Queen) {
					if (piece.getOwner().getName() == Name.white) {
						stateEncoding += "e";
					} else {
						stateEncoding += "k";
					}
				} else if (piece instanceof King) {
					if (piece.getOwner().getName() == Name.white) {
						stateEncoding += "f";
					} else {
						stateEncoding += "l";
					}
				}
				
				if (square.getEnPassentAvailable()) {
					stateEncoding += "z";
				}
			}
		}
		
		return stateEncoding;
	}
}
