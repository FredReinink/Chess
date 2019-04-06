package Pieces;

import java.util.ArrayList;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class Queen extends Piece{

	public Queen(Player owner) {
		super(owner);
	}

	@Override
	public void setValidMoves(Board board){
		resetMoves();
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
