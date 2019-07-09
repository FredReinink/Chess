package Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import Logic.Board;
import Logic.ChessUtility;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class King extends Piece implements Serializable {

	public King(Player owner) {
		super(owner);
	}
	
	public King(Player owner, Coordinate position) {
		super(owner,position);
	}

	@Override
	public void setValidMoves(Board board){		
		verifyCheck(board);
		
		validMoves.removeAll(validMoves);
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
	
	/** Determines whether this king is currently under check and sets the check variable in Player accordingly
	 * 
	 * @param board the instance of Board the king is on
	 */
	public void verifyCheck(Board board) {
		Square[][] squares = board.getSquares();
		
		if (squares[this.position.getRow()][this.position.getColumn()].isAttackedByEnemyOf(this.owner)){
			this.owner.setCheck(true);
			System.out.println("setting check to true");
		} else {
			this.owner.setCheck(false);
		}
	}

}
