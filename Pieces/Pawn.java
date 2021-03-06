package Pieces;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;
import Resources.Name;

/**
 * Handles pawn movement including en passent and promotions.
 * 
 * @author Fred Reinink
 */
public class Pawn extends Piece{
	
	private static final int UP_BOARD = -1;
	private static final int DOWN_BOARD = 1;
	
	int startingRow;
	int promotionRow;
	int moveDirection;

	public Pawn(Player owner) {
		super(owner);

		if (this.getOwner().getName() == Name.white) {
			startingRow = Board.WHITE_PAWN_ROW;
			promotionRow = Board.BLACK_KING_ROW;
			moveDirection = UP_BOARD;
		} else {
			startingRow = Board.BLACK_PAWN_ROW;
			promotionRow = Board.WHITE_KING_ROW;
			moveDirection = DOWN_BOARD;
		}
	}

	@Override
	public void setPossibleMoves(Board board) {
		resetPossibleMoves();
		resetAggressiveMoves();

		Square[][] squares = board.getSquares();

		if (position.getRow() != promotionRow) {
			//if the square immediately in front of the pawn does not contain a piece, it is a valid move
			Square candidateMove = squares[position.getRow() + moveDirection][position.getColumn()];
			if (!Board.containsPiece(candidateMove)) {
				possibleMoves.add(candidateMove);
			}
			//if the square in front and to the right of the pawn contains an enemy piece OR is eligible for en passent, it is a valid move
			if (position.getColumn() != 7) {
				candidateMove = squares[position.getRow() + moveDirection][position.getColumn()+1];
				if (Board.containsEnemyPiece(candidateMove,this.owner.getName()) || candidateMove.getEnPassentAvailable() == true) {
					possibleMoves.add(candidateMove);
					aggressiveMoves.add(candidateMove);
				}
			}
			//if the square in front and to the left of the pawn contains an enemy piece OR is eligible for en passent, it is a valid move
			if (position.getColumn() != 0) {
				candidateMove = squares[position.getRow() + moveDirection][position.getColumn()-1];
				if (Board.containsEnemyPiece(candidateMove,this.owner.getName()) || candidateMove.getEnPassentAvailable() == true) {
					possibleMoves.add(candidateMove);
					aggressiveMoves.add(candidateMove);
				}
			}
		}
		//if the pawn is on its starting row, it can move two squares ahead, but only if there is not a piece in the way
		if (position.getRow() == startingRow) {
			Square candidateMove = squares[position.getRow() + (moveDirection*2)][position.getColumn()];
			Square inTheWay = squares[position.getRow() + moveDirection][position.getColumn()];
			if ((!Board.containsPiece(candidateMove)) && (!Board.containsPiece(inTheWay))) {
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
			board.placePiece(new Pawn(owner), pawnToKill);
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
	
	@Override
	public void promotionHandler(Board board, Coordinate newPosition) {
		if (newPosition.getRow() == promotionRow){
			board.promotePawn(this);
		}
	}
}
