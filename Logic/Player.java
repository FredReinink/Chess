package Logic;

import java.io.Serializable;

import java.util.ArrayList;

import Resources.Name;
import Resources.Side;
import Pieces.*;

/**
 * Player class. Handles player-specific states (check, ability to move) and player pieces.
 * 
 * @author Fred Reinink
 */
public class Player implements Serializable{

	private Name name;
	private King king;
	private boolean inCheck;
	private ArrayList<Piece> pieces;
	private boolean hasAValidMove;

	public void setinCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	public boolean isInCheck() {
		return inCheck;
	}

	public void setHasAValidMove(boolean hasAValidMove) {
		this.hasAValidMove = hasAValidMove;
	}

	public boolean hasAValidMove() {
		return hasAValidMove;
	}

	public King getKing() {
		return king;
	}

	public Name getName() {
		return name;
	}

	/**
	 * @param pieceToAdd the piece to add the this player's piece list.
	 */
	public void addPiece(Piece pieceToAdd) {
		pieces.add(pieceToAdd);
	}

	/**
	 * @return the player's list of pieces (dead and alive).
	 */
	public ArrayList<Piece> getPieces(){
		return pieces;
	}

	/**
	 * Sets possible moves for all alive pieces belonging to this player.
	 * 
	 * @param board the instance of board the pieces are on.
	 */
	public void setPossibleMoves(Board board){
		for (Piece p : pieces) {
			if (!p.isDead()) {
				p.setPossibleMoves(board);
			}
		}
	}

	/**
	 * Sets valid moves for all alive pieces belonging to this player.
	 * 
	 * @param board the instance of board the pieces are on.
	 */
	public void setValidMoves(Board board) {
		hasAValidMove = false;

		for (Piece p : pieces) {
			if (!p.isDead()) {
				p.setValidMoves(board);
			}
		}
	}

	/**
	 * Gets the rook belonging to this player on the specified side.
	 * 
	 * @param side the Side of the desired rook.
	 * @return the Rook if it exists, null otherwise.
	 */
	public Rook getRookonSide(Side side) {
		for (Piece piece : pieces) {
			if (piece instanceof Rook && ((Rook) piece).getSide() == side) {
				return (Rook) piece;
			}
		}
		return null;
	}

	/**
	 * Initializes the players name, and creates a player's initial pieces.
	 * Pieces are created according to this encoding: [pawn, pawn, pawn, pawn, pawn, pawn, pawn, pawn, rook(left), knight, bishop, queen, king, bishop, knight, rook(right)]
	 * 
	 * @param name the name of the player to create.
	 * @param initPieces whether to initialize the player's piece list with a starting configuration.
	 */
	public Player(Name name, boolean initPieces) {
		this.name = name;
		pieces = new ArrayList<Piece>();
		king = new King(this);

		if (initPieces) {
			for (int i = 0; i < 8 ; i++) {
				pieces.add(new Pawn(this));
			}

			pieces.add(new Rook(this,Side.left));
			pieces.add(new Knight(this));
			pieces.add(new Bishop(this));
			pieces.add(new Queen(this));
			pieces.add(king);
			pieces.add(new Bishop(this));
			pieces.add(new Knight(this));
			pieces.add(new Rook(this,Side.right));
		}
	}
}
