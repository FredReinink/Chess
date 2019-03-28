package Logic;

import Pieces.Piece;

public class Square {
	private int row;
	private int column;
	private Piece piece = null;
	private boolean attacked;
	
	public String toString() {
		if (piece == null) {
			return "[              ]";
		}
		return piece.toString();
	}
	
	public Square(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Square(int row, int column, Piece piece) {
		this.row = row;
		this.column = column;
		this.piece = piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void removePiece() {
		piece = null;
	}
	
	public void setAggression(boolean b) {
		attacked = b;
	}
}
