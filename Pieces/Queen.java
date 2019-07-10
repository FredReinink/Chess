package Pieces;

import java.util.ArrayList;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class Queen extends Piece {

	public Queen(Player owner) {
		super(owner);
	}

	@Override
	public void setPossibleMoves(Board board){
		resetPossibleMoves();
		Bishop bishop = new Bishop(this.owner);
		bishop.setPosition(this.position);
		
		Rook rook = new Rook(this.owner);
		rook.setPosition(this.position);
		
		rook.setPossibleMoves(board);
		bishop.setPossibleMoves(board);
		
		possibleMoves.addAll(rook.getPossibleMoves());
		possibleMoves.addAll(bishop.getPossibleMoves());
	}

}
