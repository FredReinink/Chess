package Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import Logic.Board;
import Logic.ChessUtility;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class Rook extends Piece implements Serializable {

	public Rook(Player owner) {
		super(owner);
	}
	
	public Rook(Player owner, Coordinate position) {
		super(owner,position);
	}

	@Override
	public void setValidMoves(Board board){
		resetMoves();
		Square[][] squares = board.getSquares();
		
		//up
		boolean blocked = false;
		int row = position.getRow() - 1;
		while (row >= 0 && blocked == false) {
			blocked = vectorMoveHelper(board, squares[row][position.getColumn()]);
			row--;
		}
		
		//down
		blocked = false;
		row = position.getRow() + 1;
		while (row <= 7 && blocked == false) {
			blocked = vectorMoveHelper(board, squares[row][position.getColumn()]);
			row++;
		}
		
		//left
		blocked = false;
		int column = position.getColumn() - 1;
		while (column >= 0 && blocked == false) {
			blocked = vectorMoveHelper(board,squares[position.getRow()][column]);
			column--;
		}
		
		//right
		blocked = false;
		column = position.getColumn() + 1;
		while (column <= 7 && blocked == false) {
			blocked = vectorMoveHelper(board, squares[position.getRow()][column]);
			column++;
		}
	}

}
