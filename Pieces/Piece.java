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
	
	public abstract void setValidMoves(Board board);
}
