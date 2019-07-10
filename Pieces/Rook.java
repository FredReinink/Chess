package Pieces;

import java.util.ArrayList;

import Logic.Board;
import Logic.ChessUtility;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class Rook extends Piece{

	public Rook(Player owner) {
		super(owner);
	}

	@Override
	public void setPossibleMoves(Board board){
		resetPossibleMoves();
		resetAggressiveMoves();
		
		Square[][] squares = board.getSquares();
		
		boolean blocked = false;
		int row = position.getRow() - 1;
		while (row >= 0 && blocked == false) {
			blocked = vectorMoveHelper(squares[row][position.getColumn()]);
			row--;
		}
		
		blocked = false;
		row = position.getRow() + 1;
		while (row <= 7 && blocked == false) {
			blocked = vectorMoveHelper(squares[row][position.getColumn()]);
			row++;
		}
		
		blocked = false;
		int column = position.getColumn() - 1;
		while (column >= 0 && blocked == false) {
			blocked = vectorMoveHelper(squares[position.getRow()][column]);
			column--;
		}
		
		blocked = false;
		column = position.getColumn() + 1;
		while (column <= 7 && blocked == false) {
			blocked = vectorMoveHelper(squares[position.getRow()][column]);
			column++;
		}
		
		aggressiveMoves.addAll(possibleMoves);
	}

}
