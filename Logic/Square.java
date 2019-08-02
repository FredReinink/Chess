package Logic;

import java.io.Serializable;

import Pieces.Piece;

/**
 * This is essentially just a data structure class. 
 * One instance of a Square represents one checker on a chess board.
 * Stores pieces and keeps track of en passent availability.
 * 
 * @author Fred Reinink
 */
public class Square implements Serializable{
	
	private Coordinate position;
	private Piece piece = null;
	private boolean enPassentAvailable = false;

	public void setEnPassentAvailable(boolean b) {
		enPassentAvailable = b;
	}

	public boolean getEnPassentAvailable() {
		return enPassentAvailable;
	}

	public Square(Coordinate c) {
		position = c;
	}

	public Coordinate getPosition() {
		return position;
	}

	public int getRow() {
		return position.getRow();
	}

	public int getColumn() {
		return position.getColumn();
	}

	public Piece getPiece() {
		return piece;
	}

	public Square(Coordinate position, Piece piece) {
		this.position = position;
		this.piece = piece;
		piece.setPosition(position);
	}

	/**
	 * Sets a piece on this square. If this square is currently occupied by another piece, sets that piece to be dead.
	 * 
	 * @param piece the piece to set
	 */
	public void setPiece(Piece pieceToSet) {
		if (pieceToSet != null) {
			pieceToSet.setPosition(position);

			if (piece != null) {
				piece.setDead(true);
			}
		}
		piece = pieceToSet;
	}
}
