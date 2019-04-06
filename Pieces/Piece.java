package Pieces;

import java.util.ArrayList;

import Logic.*;

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
	
	/**
	 * Determines if there is an enemy piece on the square
	 * @param square the square to check
	 * @return
	 */
	public boolean containsEnemyPiece(Square square) {
		if ((square.getPiece() != null) && (!(square.getPiece().owner.getName() == this.owner.getName()))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if there is a friendly piece on the square
	 * @param square the square to check
	 * @return
	 */
	public boolean containsFriendlyPiece(Square square) {
		if ((square.getPiece() != null) && (square.getPiece().owner.getName() == this.owner.getName())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if there is a piece on the square
	 * @param square the square to check
	 * @return
	 */
	public boolean containsPiece(Square square) {
		if (square.getPiece() != null) {
			return true;
		}
		return false;
		
	}
	
	public void resetMoves() {
		validMoves.removeAll(validMoves);
	}
	
	public abstract void setValidMoves(Board board);
}
