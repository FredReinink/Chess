
public class Square {
	private int X;
	private int Y;
	private Piece piece;
	private boolean aggression;
	
	public Square(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}
	
	public Square(int X, int Y, Piece piece) {
		this.X = X;
		this.Y = Y;
		this.piece = piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void setAggression(boolean b) {
		aggression = b;
	}
}
