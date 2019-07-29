package Pieces;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;

/**
 * @author Fred Reinink
 */
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
		
		//knight movement is "L shaped" so a possible move = currentRow +/- 1/2 AND currentColumn +/- 2/1 
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
