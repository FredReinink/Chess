package Pieces;

import java.io.Serializable;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;

public class Queen extends Piece implements Serializable {

	public Queen(Player owner) {
		super(owner);
	}
	
	public Queen(Player owner, Coordinate position) {
		super(owner,position);
	}

	@Override
	public void setValidMoves(Board board){
		resetMoves();
		
		//Since the Queen's valid moves consist of all the moves a Rook and a Bishop can do, can simply treat a Queen as a Rook and 
		//Bishop for the purposes of determining valid moves
		Bishop bishop = new Bishop(this.owner);
		bishop.setPosition(this.position);
		
		Rook rook = new Rook(this.owner);
		rook.setPosition(this.position);
		
		rook.setValidMoves(board);
		bishop.setValidMoves(board);
		
		validMoves.addAll(rook.getValidMoves());
		validMoves.addAll(bishop.getValidMoves());
	}

}
