package Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import Logic.Board;
import Logic.ChessUtility;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class Knight extends Piece implements Serializable {

	public Knight(Player owner) {
		super(owner);
	}
	
	public Knight(Player owner, Coordinate position) {
		super(owner,position);
	}

	@Override
	public void setValidMoves(Board board){
		resetMoves();
		int currentRow = this.position.getRow();
		int currentColumn = this.position.getColumn();
		
		for (int i = -2; i <= 2; i+=4) {
			for (int j = -1; j <= 1; j+=2) {
				Coordinate c = new Coordinate(currentRow + i, currentColumn + j);
				pointMoveHelper(board,c);
				
				c = new Coordinate(currentRow + j, currentColumn + i);
				pointMoveHelper(board,c);
			}
		}
	}

}
