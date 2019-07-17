package Pieces;

import java.util.ArrayList;

import Logic.Board;
import Logic.ChessUtility;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;
import Resources.Side;

public class Rook extends Piece{
	private Side side;
	
	public Side getSide() {
		return side;
	}

	public Rook(Player owner, Side side) {
		super(owner);
		this.side = side;
	}
	
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

	@Override
	public void castleForfeitChecker() {
		if (side == Side.left) {
			owner.getKing().setLeftCastleAvailable(false);
		} else if (side == Side.right) {
			owner.getKing().setRightCastleAvailable(false);
		}
	}
}
