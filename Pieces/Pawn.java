package Pieces;

import Logic.*;
import Resources.Name;

public class Pawn extends Piece{

	public Pawn(Player owner) {
		super(owner);
	}

	@Override
	public void setValidMoves(Board board) {
		validMoves.removeAll(validMoves);
		Square[][] squares = board.getSquares();
		
		int startingRow;
		int moveOffset;
		if (this.getOwner().getName() == Name.white) {
			startingRow = 6;
			moveOffset = -1;
		} else {
			startingRow = 1;
			moveOffset = 1;
		}
		
		//if the square immediately in front of the pawn does not contain a piece, it is a valid move
		Square validCandidate = squares[position.getRow() + moveOffset][position.getColumn()];
		if (!containsPiece(validCandidate)) {
			validMoves.add(validCandidate);
		}
		//if the square to the right of the pawn contains an enemy piece, it is a valid move
		if (position.getColumn() != 7) {
			validCandidate = squares[position.getRow() + moveOffset][position.getColumn()+1];
			if (containsEnemyPiece(validCandidate)) {
				validMoves.add(validCandidate);
			}
		}
		//if the square to the left of the pawn contains an enemy piece, it is a valid move
		if (position.getColumn() != 0) {
			validCandidate = squares[position.getRow() + moveOffset][position.getColumn()-1];
			if (containsEnemyPiece(validCandidate)) {
				validMoves.add(validCandidate);
			}
		}
		//if the pawn is on its starting row, it can move two squares ahead, but only if there is not a piece in the way
		if (position.getRow() == startingRow) {
			validCandidate = squares[position.getRow() + (moveOffset*2)][position.getColumn()];
			Square inTheWay = squares[position.getRow() + moveOffset][position.getColumn()];
			if ((!containsPiece(validCandidate)) && (!containsPiece(inTheWay))) {
				validMoves.add(validCandidate);
			}
		}
	}
	
	/**
	 * Determines if there is an enemy piece on the square
	 * @param square the square to check
	 * @return
	 */
	public boolean containsEnemyPiece(Square square) {
		if ((square.getPiece() != null) && (!(square.getPiece().owner.getName() == this.owner.getName()))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if there is a friendly piece on the square
	 * @param square the square to check
	 * @return
	 */
	public boolean containsFriendlyPiece(Square square) {
		if ((square.getPiece() != null) && (square.getPiece().owner.getName() == this.owner.getName())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if there is a piece on the square
	 * @param square the square to check
	 * @return
	 */
	public boolean containsPiece(Square square) {
		if (square.getPiece() != null) {
			return true;
		}
		return false;
		
	}

}
