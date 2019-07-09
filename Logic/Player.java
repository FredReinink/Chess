package Logic;

import java.io.Serializable;
import java.util.ArrayList;

import Resources.Name;
import Pieces.*;

public class Player implements Serializable {
	private ArrayList<Piece> pieces;
	private Name name;
	private boolean check;
	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * @return player's pieces list
	 */
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public String toString() {
		return pieces.toString();
	}
	
	/**
	 * @return the player's name 
	 */
	public Name getName() {
		return name;
	}
	
	/**
	 * Initializes the player and creates a player's initial pieces.
	 * Pieces are stored in this order: [pawn, pawn, pawn, pawn, pawn, pawn, pawn, pawn, rook, knight, bishop, queen, king, bishop, knight, rook]
	 * 
	 * @param name the name of the player to create
	 */
	public Player(Name name) {
		check = false;
		this.name = name;

		pieces = new ArrayList<Piece>();
		
		//Create pawns
		for (int i = 0; i < 8 ; i++) {
			pieces.add(new Pawn(this));
		}
		
		pieces.add(new Rook(this));
		pieces.add(new Knight(this));
		pieces.add(new Bishop(this));
		pieces.add(new Queen(this));
		pieces.add(new King(this));
		pieces.add(new Bishop(this));
		pieces.add(new Knight(this));
		pieces.add(new Rook(this));
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param playerToCopy
	 */
	public Player(Player playerToCopy) {
		check = false;
		this.name = playerToCopy.getName();
		pieces = new ArrayList<Piece>();
		
		for (Piece piece : pieces) {
			if (piece instanceof King) {
				pieces.add(new King(this,piece.getPosition()));
			}
			if (piece instanceof Knight) {
				pieces.add(new Knight(this,piece.getPosition()));
			}
			if (piece instanceof Pawn) {
				pieces.add(new Pawn(this,piece.getPosition()));
			}
			if (piece instanceof Queen) {
				pieces.add(new Queen(this,piece.getPosition()));
			}
			if (piece instanceof Rook) {
				pieces.add(new Rook(this,piece.getPosition()));
			}
			if (piece instanceof Bishop) {
				pieces.add(new Bishop(this,piece.getPosition()));
			}
		}
	}
}
