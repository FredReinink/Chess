package Logic;

import java.io.Serializable;
import java.util.ArrayList;

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
	 * Updates the board state.
	 */
	public void update() {
		setPossibleMoves();
		setCheck();
		setValidMoves();
		winConditionHandler();
	}

	public void winConditionHandler() {
		//null check is for boards that are created to test whether a move results in check. These boards do not have an initialized value for controller and do not need to test for win conditions
		if (controller != null) {
			if (!white.hasAValidMove()) {
				if (white.isInCheck()) {
					controller.checkmate(black);
				} else {
					//stalemate
				}
			} else if (!black.hasAValidMove()) {
				if (black.isInCheck()) {
					controller.checkmate(white);
				} else {
					//stalemate
				}
			}
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

	public String toString() {
		String result = "";

		result += "Player white has pieces: " + white.toString();
		result += "\nPlayer black has pieces: " + black.toString();
		result += "\nBoard representation:\n";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				result += " " + board[i][j].toString();
			}
			result += "\n";	
		}

		return result;
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
	 * @param p the piece to be placed
	 * @param row the row of the square to place the piece
	 * @param column the column coordinate of the square to place the piece
	 */
	public void placePiece(Piece p, Coordinate position) {
		board[position.getRow()][position.getColumn()].setPiece(p);
	}

}
