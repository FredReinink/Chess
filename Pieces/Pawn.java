package Pieces;

import Logic.*;
import Resources.Name;

public class Pawn extends Piece{
	int startingRow;
	int promotionRow;
	int moveOffset;

	public Pawn(Player owner) {
		super(owner);

		if (this.getOwner().getName() == Name.white) {
			startingRow = Board.WHITE_PAWN_ROW;
			promotionRow = Board.BLACK_KING_ROW;
			moveOffset = -1;
		} else {
			startingRow = Board.BLACK_PAWN_ROW;
			promotionRow = Board.WHITE_KING_ROW;
			moveOffset = 1;
		}
	}

	@Override
	public void setPossibleMoves(Board board) {
		resetPossibleMoves();
		resetAggressiveMoves();

		Square[][] squares = board.getSquares();

		if (this.position.getRow() == promotionRow) {
			//promotion
			return;
		}

		//if the square immediately in front of the pawn does not contain a piece, it is a valid move
		Square candidateMove = squares[position.getRow() + moveOffset][position.getColumn()];
		if (!ChessUtility.containsPiece(candidateMove)) {
			possibleMoves.add(candidateMove);
		}
		//if the square in front and to the right of the pawn contains an enemy piece OR is eligible for en passent, it is a valid move
		if (position.getColumn() != 7) {
			candidateMove = squares[position.getRow() + moveOffset][position.getColumn()+1];
			if (ChessUtility.containsEnemyPiece(candidateMove,this.owner.getName()) || candidateMove.getEnPassentAvailable() == true) {
				possibleMoves.add(candidateMove);
				aggressiveMoves.add(candidateMove);
			}
		}
		//if the square in front and to the left of the pawn contains an enemy piece OR is eligible for en passent, it is a valid move
		if (position.getColumn() != 0) {
			candidateMove = squares[position.getRow() + moveOffset][position.getColumn()-1];
			if (ChessUtility.containsEnemyPiece(candidateMove,this.owner.getName()) || candidateMove.getEnPassentAvailable() == true) {
				possibleMoves.add(candidateMove);
				aggressiveMoves.add(candidateMove);
			}
		}
		//if the pawn is on its starting row, it can move two squares ahead, but only if there is not a piece in the way
		if (position.getRow() == startingRow) {
			candidateMove = squares[position.getRow() + (moveOffset*2)][position.getColumn()];
			Square inTheWay = squares[position.getRow() + moveOffset][position.getColumn()];
			if ((!ChessUtility.containsPiece(candidateMove)) && (!ChessUtility.containsPiece(inTheWay))) {
				possibleMoves.add(candidateMove);
			}
		}
	}

	@Override
	public void enPassentHandler(Board board, Coordinate newPosition) {
		Square[][] squares = board.getSquares();
		Square newSquare = squares[newPosition.getRow()][newPosition.getColumn()];

		if (newSquare.getEnPassentAvailable() == true) {
			Coordinate pawnToKill;
			if (owner.getName() == Name.white) {
				pawnToKill = new Coordinate(Board.ROW_3,newPosition.getColumn());
			} else {
				pawnToKill = new Coordinate(Board.ROW_4,newPosition.getColumn());
			}
			board.placePiece(null, pawnToKill);
		}

		board.resetEnPassent();

		if (position.getRow() == Board.WHITE_PAWN_ROW && newPosition.getRow() == Board.ROW_4) {
			Square enPassentSquare = board.getSquares()[Board.WHITE_ENPASSENT_ROW][position.getColumn()];
			enPassentSquare.setEnPassentAvailable(true);
		} else if (position.getRow() == Board.BLACK_PAWN_ROW && newPosition.getRow() == Board.ROW_3) {
			Square enPassentSquare = board.getSquares()[Board.BLACK_ENPASSENT_ROW][position.getColumn()];
			enPassentSquare.setEnPassentAvailable(true);
		}
	}
}
