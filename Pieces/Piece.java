package Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import Logic.*;
import Resources.Name;

public abstract class Piece implements Serializable{
	private boolean dead = false;
	protected Player owner;
	protected Coordinate position;
	
	/* A possible move is a move that a piece can make purely based on a piece's rules for movement (includes potentially illegal moves where a move places the owner in check). */
	protected ArrayList<Square> possibleMoves = new ArrayList<Square>();
	
	/* A valid move is a move that a player is legally allowed to make. */
	private ArrayList<Square> validMoves = new ArrayList<Square>();
	
	/* An aggressive move is a move that a piece can make where it can take an opposing piece. All possible moves are aggressive moves except in the case where the possible move is a pawn moving directly forward. */
	protected ArrayList<Square> aggressiveMoves = new ArrayList<Square>();
	
	
	public Piece(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * Moves this piece to the desired position on the board
	 * 
	 * @param board the instance of the board the piece is on
	 * @param newPosition the desired position
	 */
	public void move(Board board, Coordinate newPosition) {
		enPassentCheck(board, position, newPosition);
		
		board.placePiece(null, position);
		board.placePiece(this, newPosition);
	}
	
	public void enPassentCheck(Board board, Coordinate startingPosition, Coordinate newPosition) {
		board.resetEnPassent();
		
		if (this instanceof Pawn) {
			if (startingPosition.getRow() == Board.WHITE_PAWN_ROW && newPosition.getRow() == 4) {
				Square enPassentSquare = board.getSquares()[Board.WHITE_ENPASSENT_ROW][position.getColumn()];
				enPassentSquare.setEnPassentAvailable(true);
			} else if (startingPosition.getRow() == Board.BLACK_PAWN_ROW && newPosition.getRow() == 3) {
				Square enPassentSquare = board.getSquares()[Board.BLACK_ENPASSENT_ROW][position.getColumn()];
				enPassentSquare.setEnPassentAvailable(true);
			}
		}
	}
	
	/**
	 * @return whether the piece is dead or not
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Sets the piece to be dead and clears all move lists.
	 * 
	 * @param dead whether the piece is dead or not
	 */
	public void setDead(boolean dead) {
		resetPossibleMoves();
		resetValidMoves();
		resetAggressiveMoves();
		this.dead = dead;
	}

	/**
	 * Helper method for pieces that use vectors to determine possible moves.
	 * 
	 * Checks if a square is a valid move. If the square contains an enemy piece, adds that square to possible moves before returning that vector as blocked.
	 * If the square contains a friendly piece, returns that vector as immediately blocked.
	 * 
	 * @param candidateMove 
	 * @return false when the vector is not blocked by the candidateMove, true otherwise
	 */
	protected boolean vectorMoveHelper(Square candidateMove) {
		boolean blocked = false;
		if (candidateMove.getPiece() == null) {
			possibleMoves.add(candidateMove);
		} else if (ChessUtility.containsEnemyPiece(candidateMove,this.owner.getName())) {
			possibleMoves.add((candidateMove));
			blocked = true;
		} else if (ChessUtility.containsFriendlyPiece(candidateMove,this.owner.getName())) {
			blocked = true;
		}
		return blocked;
	}
	
	/**
	 * Helper method for pieces that select specific squares to determine possible moves.
	 * 
	 * Adds a square corresponding to a Coordinate c to the board if it is a possible move.
	 * 
	 * @param board the instance of board the piece wants to move on.
	 * @param c the coordinate of the square the piece wants to move to.
	 */
	protected void pointMoveHelper(Board board, Coordinate c) {
		if (c.offBoard() == false) {
			Square candidateMove = board.getSquares()[c.getRow()][c.getColumn()];
			if (!ChessUtility.containsFriendlyPiece(candidateMove,this.owner.getName())) {
				possibleMoves.add(candidateMove);
			}
		}
	}
	
	public ArrayList<Square> getPossibleMoves(){
		return possibleMoves;
	}
	
	public ArrayList<Square> getValidMoves() {
		return validMoves;
	}
	
	public ArrayList<Square> getAggressiveMoves() {
		return aggressiveMoves;
	}
	
	public Coordinate getPosition() {
		return position;
	}
	
	public void setPosition(Coordinate c) {
		position = c;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	/**
	 * Clears all valid moves for this piece.
	 */
	public void resetValidMoves() {
		validMoves.removeAll(validMoves);
	}
	
	/**
	 * Clears all possible moves for this piece.
	 */
	public void resetPossibleMoves() {
		possibleMoves.removeAll(possibleMoves);
	}
	
	/**
	 * Clears all aggressive moves for this piece.
	 */
	public void resetAggressiveMoves() {
		aggressiveMoves.removeAll(aggressiveMoves);
	}
	
	/**
	 * Sets all valid moves for this piece.
	 * 
	 * @param board the instance of the board the piece is on
	 */
	public void setValidMoves(Board board) {
		resetValidMoves();
		
		ArrayList<Square> possibleMovesCopy = new ArrayList<Square>(possibleMoves);
		
		System.out.println("\n-----------------------------------------");
		System.out.println("For " + this + " At " + this.getPosition().getRow() + " " + this.getPosition().getColumn());
		for (Square possibleMove : possibleMovesCopy) {
			System.out.println("Checking move: " + possibleMove.getPosition().getRow() + " " + possibleMove.getPosition().getColumn());
			Board boardCopy = (Board) ChessUtility.deepCopy(board);
			
			//gets the piece on the copied board that corresponds to this piece
			Piece copiedPiece = boardCopy.getSquares()[position.getRow()][position.getColumn()].getPiece();
			System.out.println(copiedPiece);
			
			if (copiedPiece != null) {
				copiedPiece.move(boardCopy, possibleMove.getPosition());
			}
			
			boardCopy.testBoardUpdate();
			if ((this.owner.getName() == Name.white) && (!boardCopy.getWhite().isInCheck())) {
				validMoves.add(possibleMove);
			}
			if ((this.owner.getName() == Name.black) && (!boardCopy.getBlack().isInCheck())) {
				validMoves.add(possibleMove);
			}
		}
	}

	
	/**
	 * Sets all possible and aggressive moves for this piece.
	 * 
	 * @param board the instance of the board the piece is on
	 */
	public abstract void setPossibleMoves(Board board);


}
