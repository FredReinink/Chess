package Logic;

import java.io.Serializable;

import Pieces.Piece;

public class Square implements Serializable{
	private Coordinate position;
	private Piece piece = null;
	private boolean enPassentAvailable = false;

	public String toString() {
		if (piece == null) {
			return "[              ]";
		}
		return piece.toString();
	}

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

	public Square(Coordinate c, Piece piece) {
		position = c;
		this.piece = piece;
		piece.setPosition(c);
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
