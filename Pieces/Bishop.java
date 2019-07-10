package Pieces;

import Logic.*;

public class Bishop extends Piece{

	public Bishop(Player owner) {
		super(owner);
	}

	@Override
	public void setPossibleMoves(Board board){
		resetPossibleMoves();
		resetAggressiveMoves();
		
		Square[][] squares = board.getSquares();
		
		//up left
		boolean blocked = false;
		int row = position.getRow() - 1;
		int column = position.getColumn() - 1;
		while (row >= 0 && column >= 0 && blocked == false) {
			blocked = vectorMoveHelper(squares[row][column]);
			row--;
			column--;
		}
		
		//up right
		blocked = false;
		row = position.getRow() - 1;
		column = position.getColumn() + 1;
		while (row >= 0 && column <= 7 && blocked == false) {
			blocked = vectorMoveHelper(squares[row][column]);
			row--;
			column++;
		}
		
		//down left
		blocked = false;
		row = position.getRow() + 1;
		column = position.getColumn() - 1;
		while (row <= 7 && column >= 0 && blocked == false) {
			blocked = vectorMoveHelper(squares[row][column]);
			row++;
			column--;
		}
		
		//down right
		blocked = false;
		row = position.getRow() + 1;
		column = position.getColumn() + 1;
		while (row <= 7 && column <= 7 && blocked == false) {
			blocked = vectorMoveHelper(squares[row][column]);
			row++;
			column++;
		}
		
		aggressiveMoves.addAll(possibleMoves);
	}

}
