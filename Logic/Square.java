package Logic;

import Pieces.Piece;

public class Square {
	private int x;
	private int y;
	private Piece piece = null;
	private boolean attacked;
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Square(int x, int y, Piece piece) {
		this.x = x;
		this.y = y;
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
