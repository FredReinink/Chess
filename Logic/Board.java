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

public class Board implements Serializable{
	public static final int WHITE_KING_ROW = 7;
	public static final int WHITE_PAWN_ROW = 6;
	public static final int WHITE_ENPASSENT_ROW = 5;

	public static final int ROW_4 = 4;
	public static final int ROW_3 = 3;

	public static final int BLACK_KING_ROW = 0;
	public static final int BLACK_PAWN_ROW = 1;
	public static final int BLACK_ENPASSENT_ROW = 2;

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

	private Square[][] board;
	private Player white;
	private Player black;
	
	//past board states mapped to values corresponding to the number of times that state has occurred
	private HashMap<String,Integer> pastStates = new HashMap<String,Integer>();
	
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
		return board;
	}
	
	/**
	 * Clears all mappings in the pastStates map. For efficiency, call this method any time an irreversible move occurs.
	 * 
	 */
	public void clearPastStates() {
		pastStates.clear();
	}

	/**
	 * Updates the board state.
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
		String stateEncoding = getStateEncoding();
		
		boolean threeFoldRepetition = false;
		
		boolean alreadyAdded = false;
		
		for (String key : pastStates.keySet()) {
			if (stateEncoding.equals(key)) {
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
		//null check is for boards that are created to test whether a move results in check. These boards do not have an initialized value for controller and do not need to test for win conditions
		if (controller != null) {
			if (!white.hasAValidMove()) {
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
	 * Determines whether the opposing player is attacking the specified square
	 * 
	 * @param name the name of the friendly player
	 * @param squareToCheck the square to check
	 * @return
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
	}

	/**
	 * Sets all valid moves for both players. This function trims possible moves that are illegal and so this function should only be called after setPossibleMoves().
	 * A valid move is a move that a player is legally allowed to make.
	 */
	public void setValidMoves() {
		white.setValidMoves(this);
		black.setValidMoves(this);
	}

	/**
	 * Sole constructor.
	 * Builds a board out of squares. Each square is initialized with x and y coordinates.
	 * 
	 * @param white the player with Name white
	 * @param black the player with Name black
	 */
	public Board(Player white, Player black, Controller controller) {
		this.controller = controller;
		this.white = white;
		this.black = black;

		board = new Square[8][8];
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j ++) {
				board[i][j] = new Square(new Coordinate(i,j));
			}
		}
		placeOrderedPieces(white);
		placeOrderedPieces(black);
		System.out.println(this.toString());
	}

	/**
	 * Places pieces of a specified player on the board. Assumes that the player's piece list adheres to the following encoding:
	 * [pawn, pawn, pawn, pawn, pawn, pawn, pawn, pawn, rook, knight, bishop, queen, king, bishop, knight, rook]
	 * 
	 * @param player the player whose pieces are to be placed
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

		int j = 8;
		for (int i = 0; i < 8; i++) {
			board[pawnRow][i].setPiece(pieces.get(i));
			board[kingRow][i].setPiece(pieces.get(j));
			j++;
		}	
	}

	/**
	 * Sets all enPassentAvailable variables to false
	 */
	public void resetEnPassent() {
		for (int i = 0; i < 8; i++) {
			board[WHITE_ENPASSENT_ROW][i].setEnPassentAvailable(false);;
			board[BLACK_ENPASSENT_ROW][i].setEnPassentAvailable(false);;
		}
	}

	/**
	 * Promotes a specified pawn.
	 * 
	 * @param pawn the pawn to promote
	 */
	public void promotePawn(Pawn pawn) {
		if (controller != null) {
			Player owner = pawn.getOwner();
			Piece selectedPiece = controller.promotePawn(owner);
			owner.addPiece(selectedPiece);
			placePiece(selectedPiece, pawn.getPosition());
		}
	}

	/**
	 * Places one piece on the square with the designated coordinates
	 * 
	 * @param pieceToPlace the piece to be placed
	 * @param newPosition the coordinate of the position to place the piece at
	 */
	public void placePiece(Piece pieceToPlace, Coordinate newPosition) {
		Square newSquare = board[newPosition.getRow()][newPosition.getColumn()];
		if (pieceToPlace != null && newSquare.getPiece() != null) {
			//irreversible move (piece capture)
			clearPastStates();
		}
		
		newSquare.setPiece(pieceToPlace);
	}
	
	
	/**
	 * Calculates and returns an encoding that represents this board's unique state. Does not include castling rights (because pastStates is cleared every time castling rights change).
	 * 
	 * Encoding: From left to right and top to bottom, squares are represented by the piece occupying that square as well as their en Passent availability. 
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
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j <8; j++) {
				Square square = board[i][j];
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
