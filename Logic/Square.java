package Logic;

import java.io.Serializable;

import Pieces.Piece;

public class Square implements Serializable {
	private Coordinate position;
	private Piece piece = null;
	private Player[] attackedBy = new Player[2];
	private boolean enPassent = false;

	public String toString() {
		if (piece == null) {
			return "[              ]";
		}
		return piece.toString();
	}

	public void setEnPassent(boolean b) {
		enPassent = b;
	}
	
	public boolean getEnPassent() {
		return enPassent;
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
		this.piece = piece;
		if (piece != null) {
			piece.setPosition(position);
		}
	}
	
	
	/** Adds the player to the array of players the square is currently under attack by.
	 * 
	 * @param player the Player to add
	 */
	public void addAttackedBy(Player player) {
		if (attackedBy[0] == null){
			attackedBy[0] = player;
		} else if (attackedBy[1] == null) {
			attackedBy[1] = player;
		}
	}
	
	/** Clears the array of players that are currently attacking this square
	 * 
	 */
	public void resetAttackedBy() {
		attackedBy = new Player[2];
	}
	
	
	/**
	 * 
	 * @param player the friendly player
	 * @return whether the square is attacked by the other player
	 */
	public boolean isAttackedByEnemyOf(Player player) {
		boolean attackedByEnemy = false;
		
		if (attackedBy[0] != player && attackedBy[0] != null) {
			attackedByEnemy = true;
		}
		if (attackedBy[1] != player && attackedBy[1] != null) {
			attackedByEnemy = true;
		}
		
		return attackedByEnemy;
		
	}

}
