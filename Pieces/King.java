package Pieces;

import java.util.ArrayList;

import Logic.Board;
import Logic.ChessUtility;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class King extends Piece {

	public King(Player owner) {
		super(owner);
	}

	@Override
	public void setPossibleMoves(Board board){		
		resetPossibleMoves();
		Square[][] squares = board.getSquares();
		
		int currentRow = this.position.getRow();
		int currentColumn = this.position.getColumn();
		
		for (int i = -1; i <= 1; i+=2) {
			Coordinate c = new Coordinate(currentRow + i, currentColumn + i);
			pointMoveHelper(board,c);
			
			c = new Coordinate(currentRow + i, currentColumn);
			pointMoveHelper(board,c);
			
			c = new Coordinate (currentRow, currentColumn + i);
			pointMoveHelper(board,c);
			
			c = new Coordinate(currentRow + i, currentColumn - i);
			pointMoveHelper(board,c);
		}
	}

}
