package Pieces;

import java.util.ArrayList;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class Knight extends Piece{

	public Knight(Player owner) {
		super(owner);
	}

	@Override
	public void setValidMoves(Board board){
		resetMoves();
		Square[][] squares = board.getSquares();
		
		
	}
	
	public void knightHelper(Board board, Square validCandidate) {
		
	}

}
