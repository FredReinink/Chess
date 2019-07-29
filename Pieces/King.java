package Pieces;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;
import Logic.Square;
import Resources.Name;
import Resources.Side;

public class King extends Piece {
	private boolean leftCastleAvailable;
	private boolean rightCastleAvailable;


	public boolean isLeftCastleAvailable() {
		return leftCastleAvailable;
	}

	/**
	 * Sets leftCastleAvailable. If this method signifies a player has forfeited left castling rights, calls clearPastStates() on the board.
	 * 
	 * @param leftCastleAvailable
	 * @param board the instance of board this piece is on
	 */
	public void setLeftCastleAvailable(boolean leftCastleAvailable, Board board) {
		if (this.leftCastleAvailable && !leftCastleAvailable) {
			board.clearPastStates();
		}
		
		this.leftCastleAvailable = leftCastleAvailable;
	}

	public boolean isRightCastleAvailable() {
		return leftCastleAvailable;
	}

	
	/**
	 * Sets rightCastleAvailable. If this method signifies a player has forfeited right castling rights, calls clearPastStates() on the board.
	 * 
	 * @param rightCastleAvailable
	 * @param board the instance of board this piece is on
	 */
	public void setRightCastleAvailable(boolean rightCastleAvailable, Board board) {
		if (this.rightCastleAvailable && !rightCastleAvailable) {
			board.clearPastStates();
		}
		
		this.rightCastleAvailable = rightCastleAvailable;
	}

	public King(Player owner) {
		super(owner);

		leftCastleAvailable = true;
		rightCastleAvailable = true;
	}

	@Override
	public void setPossibleMoves(Board board){		
		resetPossibleMoves();
		resetAggressiveMoves();

		int currentRow = position.getRow();
		int currentColumn = position.getColumn();

		for (int i = -1; i <= 1; i+=2) {
			Coordinate c = new Coordinate(currentRow + i, currentColumn + i);
			pointMoveHelper(board,c);

			c = new Coordinate(currentRow + i, currentColumn);
			pointMoveHelper(board,c);

			c = new Coordinate (currentRow, currentColumn + i);	
			pointMoveHelper(board,c);

			c = new Coordinate(currentRow + i, currentColumn - i);
			pointMoveHelper(board,c);
		}

		aggressiveMoves.addAll(possibleMoves);
		
		setCastleAsPossibleMove(board);
	}
	
	public void castlingHandler(Board board, Coordinate newPosition) {
		Side castlingSide = null;
		Coordinate rookPosition = null;
		
		if (owner.getName() == Name.white) {
			if (position.equals(Board.WHITE_KING_STARTING_POSITION)) {
				if (newPosition.equals(Board.WHITE_KING_LEFT_CASTLE_POSITION)) {
					castlingSide = Side.left;
					rookPosition = Board.WHITE_ROOK_LEFT_CASTLE_POSITION;
				} else if (newPosition.equals(Board.WHITE_KING_RIGHT_CASTLE_POSITION)) {
					castlingSide = Side.right;
					rookPosition = Board.WHITE_ROOK_RIGHT_CASTLE_POSITION;
				}
			}
		} else {
			if (position.equals(Board.BLACK_KING_STARTING_POSITION)) {
				if (newPosition.equals(Board.BLACK_KING_LEFT_CASTLE_POSITION)) {
					castlingSide = Side.left;
					rookPosition = Board.BLACK_ROOK_LEFT_CASTLE_POSITION;
				} else if (newPosition.equals(Board.BLACK_KING_RIGHT_CASTLE_POSITION)) {
					castlingSide = Side.right;
					rookPosition = Board.BLACK_ROOK_RIGHT_CASTLE_POSITION;
				}
			}
		}
		
		if (castlingSide != null && rookPosition != null) {
			Rook rookToMove = owner.getRookonSide(castlingSide);
			rookToMove.move(board, rookPosition);
		}
	}

	/**
	 * Adds castling to the list of possible moves for this king
	 * 
	 * @param board the board the king is on
	 */
	public void setCastleAsPossibleMove(Board board) {
		Square[][] squares = board.getSquares();
	
		if (!owner.isInCheck()) {
			if (leftCastleAvailable && !isLeftCastleBlocked(board) && !doesLeftCastleMoveThroughCheck(board)) {
				Square castleMove;
				if (owner.getName() == Name.white) {
					castleMove = squares[Board.WHITE_KING_LEFT_CASTLE_POSITION.getRow()][Board.WHITE_KING_LEFT_CASTLE_POSITION.getColumn()];
				} else {
					castleMove = squares[Board.BLACK_KING_LEFT_CASTLE_POSITION.getRow()][Board.BLACK_KING_LEFT_CASTLE_POSITION.getColumn()];
				}
				possibleMoves.add(castleMove);
			}
			if (rightCastleAvailable && !isRightCastleBlocked(board) && !doesRightCastleMoveThroughCheck(board)) {
				Square castleMove;
				if (owner.getName() == Name.white) {
					castleMove = squares[Board.WHITE_KING_RIGHT_CASTLE_POSITION.getRow()][Board.WHITE_KING_RIGHT_CASTLE_POSITION.getColumn()];
				} else {
					castleMove = squares[Board.BLACK_KING_RIGHT_CASTLE_POSITION.getRow()][Board.BLACK_KING_RIGHT_CASTLE_POSITION.getColumn()];
				}
				possibleMoves.add(castleMove);
			}
		}
	}

	/**
	 * Determines whether there is a piece occcupying the squares between the left rook and the king
	 * 
	 * @param board the board the king is on
	 * @return whether left castling is blocked
	 */
	public boolean isLeftCastleBlocked(Board board) {
		Square[][] squares = board.getSquares();

		if (owner.getName() == Name.white) {
			if (!Board.containsPiece(squares[Board.WHITE_KING_ROW][1])) {
				if (!Board.containsPiece(squares[Board.WHITE_KING_ROW][2])) {
					if (!Board.containsPiece(squares[Board.WHITE_KING_ROW][3])) {
						return false;
					}
				}
			}
		} else {
			if (!Board.containsPiece(squares[Board.BLACK_KING_ROW][1])) {
				if (!Board.containsPiece(squares[Board.BLACK_KING_ROW][2])) {
					if (!Board.containsPiece(squares[Board.BLACK_KING_ROW][3])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Determines whether there is a piece occcupying the squares between the right rook and the king
	 * 
	 * @param board the board the king is on
	 * @return whether right castling is blocked
	 */
	public boolean isRightCastleBlocked(Board board) {
		Square[][] squares = board.getSquares();

		if (owner.getName() == Name.white) {
			if (!Board.containsPiece(squares[Board.WHITE_KING_ROW][5])) {
				if (!Board.containsPiece(squares[Board.WHITE_KING_ROW][6])) {
					return false;
				}
			}
		} else {
			if (!Board.containsPiece(squares[Board.BLACK_KING_ROW][5])) {
				if (!Board.containsPiece(squares[Board.BLACK_KING_ROW][6])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Determines whether the king would move through check if it were to left castle.
	 * 
	 * @param board the board the king is on
	 * @return whether the king moves through check
	 */
	public boolean doesLeftCastleMoveThroughCheck(Board board) {
		Square[][] squares = board.getSquares();
		if (owner.getName() == Name.white) {
			if (!board.isAttacked(Name.white, squares[Board.WHITE_KING_ROW][2])) {
				if (!board.isAttacked(Name.white, squares[Board.WHITE_KING_ROW][3])) {
					return false;
				}
			}
		} else {
			if (!board.isAttacked(Name.black, squares[Board.BLACK_KING_ROW][2])) {
				if (!board.isAttacked(Name.black, squares[Board.BLACK_KING_ROW][3])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Determines whether the king would move through check if it were to right castle.
	 * 
	 * @param board the board the king is on
	 * @return whether the king moves through check
	 */
	public boolean doesRightCastleMoveThroughCheck(Board board) {
		Square[][] squares = board.getSquares();
		if (owner.getName() == Name.white) {
			if (!board.isAttacked(Name.white, squares[Board.WHITE_KING_ROW][5])) {
				if (!board.isAttacked(Name.white, squares[Board.WHITE_KING_ROW][6])) {
					return false;
				}
			}
		} else {
			if (!board.isAttacked(Name.black, squares[Board.BLACK_KING_ROW][5])) {
				if (!board.isAttacked(Name.black, squares[Board.BLACK_KING_ROW][6])) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void castleForfeitChecker(Board board) {
		setLeftCastleAvailable(false, board);
		setRightCastleAvailable(false, board);
	}

}
