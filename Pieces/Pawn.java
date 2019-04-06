package Pieces;

import Logic.*;
import Resources.Name;

public class Pawn extends Piece{

	public Pawn(Player owner) {
		super(owner);
	}

	@Override
	public void setValidMoves(Board board) {
		resetMoves();
		Square[][] squares = board.getSquares();
		
		int startingRow;
		int promotionRow;
		int moveOffset;
		if (this.getOwner().getName() == Name.white) {
			startingRow = 6;
			promotionRow = 0;
			moveOffset = -1;
		} else {
			startingRow = 1;
			promotionRow = 7;
			moveOffset = 1;
		}
		
		if (this.position.getRow() == promotionRow) {
			//promotion
			return;
		}
		
		//if the square immediately in front of the pawn does not contain a piece, it is a valid move
		Square validCandidate = squares[position.getRow() + moveOffset][position.getColumn()];
		if (!ChessUtility.containsPiece(validCandidate)) {
			validMoves.add(validCandidate);
		}
		//if the square to the right of the pawn contains an enemy piece, it is a valid move
		if (position.getColumn() != 7) {
			validCandidate = squares[position.getRow() + moveOffset][position.getColumn()+1];
			if (ChessUtility.containsEnemyPiece(validCandidate,this.owner.getName())) {
				validMoves.add(validCandidate);
			}
		}
		//if the square to the left of the pawn contains an enemy piece, it is a valid move
		if (position.getColumn() != 0) {
			validCandidate = squares[position.getRow() + moveOffset][position.getColumn()-1];
			if (ChessUtility.containsEnemyPiece(validCandidate,this.owner.getName())) {
				validMoves.add(validCandidate);
			}
		}
		//if the pawn is on its starting row, it can move two squares ahead, but only if there is not a piece in the way
		if (position.getRow() == startingRow) {
			validCandidate = squares[position.getRow() + (moveOffset*2)][position.getColumn()];
			Square inTheWay = squares[position.getRow() + moveOffset][position.getColumn()];
			if ((!ChessUtility.containsPiece(validCandidate)) && (!ChessUtility.containsPiece(inTheWay))) {
				validMoves.add(validCandidate);
			}
		}
	}
	


}
