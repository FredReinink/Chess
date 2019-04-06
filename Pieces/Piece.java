package Pieces;

import java.util.ArrayList;

import Logic.*;
import Resources.Name;

public abstract class Piece {
	protected Player owner;
	protected Coordinate position;
	protected ArrayList<Square> validMoves = new ArrayList<Square>();
	
	public Piece(Player owner) {
		this.owner = owner;
	}
	
	public void move(Board board, Coordinate newPosition) {
		board.placePiece(null, position);
		board.placePiece(this, newPosition);
	}
	

	/**
	 * Helper method for pieces that use vectors to determine valid moves.
	 * 
	 * Checks if a square is a valid move. If the square contains an enemy piece, adds that square to valid moves before returning that vector as blocked.
	 * If the square contains a friendly piece, returns that vector as immediately blocked.
	 * 
	 * @param validCandidate 
	 * @return false when the vector is not blocked by the validCandidate, true otherwise
	 */
	protected boolean vectorMoveHelper(Square validCandidate) {
		boolean blocked = false;
		if (validCandidate.getPiece() == null) {
			validMoves.add(validCandidate);
		} else if (ChessUtility.containsEnemyPiece(validCandidate,this.owner.getName())) {
			validMoves.add((validCandidate));
			blocked = true;
		} else if (ChessUtility.containsFriendlyPiece(validCandidate,this.owner.getName())) {
			blocked = true;
		}
		return blocked;
	}
	
	/**
	 * Helper method for pieces that select specific squares to determine valid moves.
	 * 
	 * Adds a square corresponding to a Coordinate c to the board if it is a valid move.
	 * 
	 * @param board the instance of board the piece wants to move on.
	 * @param c the coordinate of the square the piece wants to move to.
	 */
	protected void pointMoveHelper(Board board, Coordinate c) {
		if (c.offBoard() == false) {
			Square validCandidate = board.getSquares()[c.getRow()][c.getColumn()];
			if (!ChessUtility.containsFriendlyPiece(validCandidate,this.owner.getName())) {
				validMoves.add(validCandidate);
			}
		}
	}
	
	public ArrayList<Square> getValidMoves(){
		return validMoves;
	}
	
	public Coordinate getPosition() {
		return position;
	}
	
	public void setPosition(Coordinate c) {
		position = c;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	
	public void resetMoves() {
		validMoves.removeAll(validMoves);
	}
	
	/**
	 * Sets all valid moves for this piece
	 * @param board the instance of the board the piece is on
	 */
	public abstract void setValidMoves(Board board);
}
