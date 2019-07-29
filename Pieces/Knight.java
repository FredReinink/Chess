package Pieces;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;

public class Knight extends Piece{

	public Knight(Player owner) {
		super(owner);
	}

	@Override
	public void setPossibleMoves(Board board){
		resetPossibleMoves();
		resetAggressiveMoves();
		
		int currentRow = position.getRow();
		int currentColumn = position.getColumn();
		
		for (int i = -2; i <= 2; i+=4) {
			for (int j = -1; j <= 1; j+=2) {
				Coordinate c = new Coordinate(currentRow + i, currentColumn + j);
				pointMoveHelper(board,c);
				
				c = new Coordinate(currentRow + j, currentColumn + i);
				pointMoveHelper(board,c);
			}
		}
		
		aggressiveMoves.addAll(possibleMoves);
	}

}
