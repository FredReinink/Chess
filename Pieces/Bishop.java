package Pieces;

import java.util.ArrayList;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;

public class Bishop extends Piece{

	public Bishop(Player owner) {
		super(owner);
	}

	@Override
	public void setValidMoves(Board board){
		resetMoves();
		Square[][] squares = board.getSquares();
		
		//up left
		int row = position.getRow() - 1;
		int column = position.getColumn() - 1;
		while (row >= 0 && column >= 0) {
			Square validCandidate = squares[row][column];
			if (validCandidate.getPiece() == null) {
				validMoves.add(validCandidate);
			} else if (containsEnemyPiece(validCandidate)) {
				validMoves.add((validCandidate));
				break;
			} else if (containsFriendlyPiece(validCandidate)) {
				break;
			}
			row--;
			column--;
		}
		
		//up right
		row = position.getRow() - 1;
		column = position.getColumn() + 1;
		while (row >= 0 && column <= 7) {
			Square validCandidate = squares[row][column];
			if (validCandidate.getPiece() == null) {
				validMoves.add(validCandidate);
			} else if (containsEnemyPiece(validCandidate)) {
				validMoves.add((validCandidate));
				break;
			} else if (containsFriendlyPiece(validCandidate)) {
				break;
			}
			row--;
			column++;
		}
		
		//down left
		row = position.getRow() + 1;
		column = position.getColumn() - 1;
		while (row <= 7 && column >= 0) {
			Square validCandidate = squares[row][column];
			if (validCandidate.getPiece() == null) {
				validMoves.add(validCandidate);
			} else if (containsEnemyPiece(validCandidate)) {
				validMoves.add((validCandidate));
				break;
			} else if (containsFriendlyPiece(validCandidate)) {
				break;
			}
			row++;
			column--;
		}
		
		//down right
		row = position.getRow() + 1;
		column = position.getColumn() + 1;
		while (row <= 7 && column <= 7) {
			Square validCandidate = squares[row][column];
			if (validCandidate.getPiece() == null) {
				validMoves.add(validCandidate);
			} else if (containsEnemyPiece(validCandidate)) {
				validMoves.add((validCandidate));
				break;
			} else if (containsFriendlyPiece(validCandidate)) {
				break;
			}
			row++;
			column++;
		}
	}

}
