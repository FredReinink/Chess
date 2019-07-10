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

	public void setPiece(Piece piece) {
		if (piece != null) {
			piece.setPosition(position);
			
			if (this.piece != null) {
				this.piece.setDead(true);
			}
		}
		this.piece = piece;
	}
}
