package Pieces;

import Logic.Board;
import Logic.Player;

/**
 * @author Fred Reinink
 */
public class Queen extends Piece {

	public Queen(Player owner) {
		super(owner);
	}

	@Override
	public void setPossibleMoves(Board board){
		resetPossibleMoves();
		resetAggressiveMoves();
		
		//A Queen can move as if it were a rook and a bishop.
		Bishop bishop = new Bishop(this.owner);
		bishop.setPosition(this.position);
		
		Rook rook = new Rook(this.owner);
		rook.setPosition(this.position);
		
		rook.setPossibleMoves(board);
		bishop.setPossibleMoves(board);
		
		possibleMoves.addAll(rook.getPossibleMoves());
		possibleMoves.addAll(bishop.getPossibleMoves());
		
		aggressiveMoves.addAll(possibleMoves);
	}

}
