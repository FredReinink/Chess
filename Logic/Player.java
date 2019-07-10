package Logic;

import java.io.Serializable;
import java.util.ArrayList;

import Resources.Name;
import Pieces.*;

public class Player implements Serializable{
	private King king;
	private boolean inCheck;
	private ArrayList<Piece> pieces;
	private Name name;
	
	public void setinCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}
	
	public boolean isInCheck() {
		return inCheck;
	}
	
	/**
	 * Sets possible moves for all alive pieces belonging to this player.
	 * 
	 * @param board
	 */
	public void setPossibleMoves(Board board){
		for (Piece p : pieces) {
			if (!p.isDead()) {
				p.setPossibleMoves(board);
			}
		}
	}
	
	/**
	 * Sets valid moves for all alive pieces belonging to this player.
	 * 
	 * @param board
	 */
	public void setValidMoves(Board board) {
		for (Piece p : pieces) {
			if (!p.isDead()) {
				p.setValidMoves(board);
			}
		}
	}
	
	public King getKing() {
		return king;
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
	 * Sole Constructor.
	 * Initializes the players name, and creates a player's initial pieces.
	 * Pieces are created according to this encoding: [pawn, pawn, pawn, pawn, pawn, pawn, pawn, pawn, rook, knight, bishop, queen, king, bishop, knight, rook]
	 * 
	 * @param name the name of the player to create
	 */
	public Player(Name name) {
		this.name = name;

		pieces = new ArrayList<Piece>();
		
		//Create pawns
		for (int i = 0; i < 8 ; i++) {
			pieces.add(new Pawn(this));
		}
		
		king = new King(this);
		
		pieces.add(new Rook(this));
		pieces.add(new Knight(this));
		pieces.add(new Bishop(this));
		pieces.add(new Queen(this));
		pieces.add(king);
		pieces.add(new Bishop(this));
		pieces.add(new Knight(this));
		pieces.add(new Rook(this));
	}
}
