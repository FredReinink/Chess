package Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import Logic.*;
import Resources.Name;

public abstract class Piece implements Serializable{
	private boolean dead = false;
	protected Player owner;
	protected Coordinate position;
	protected ArrayList<Square> possibleMoves = new ArrayList<Square>();
	protected ArrayList<Square> validMoves = new ArrayList<Square>();
	protected ArrayList<Square> aggressiveMoves = new ArrayList<Square>();
	
	public Piece(Player owner) {
		this.owner = owner;
	}
	
	public void move(Board board, Coordinate newPosition) {
		board.placePiece(null, position);
		board.placePiece(this, newPosition);
	}
	
	/**
	 * @return whether the piece is dead or not
	 */
	public boolean isDead() {
		return dead;
	}

	/**
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
	
	public void resetValidMoves() {
		validMoves.removeAll(validMoves);
	}
	
	public void resetPossibleMoves() {
		possibleMoves.removeAll(possibleMoves);
	}
	
	public void resetAggressiveMoves() {
		aggressiveMoves.removeAll(aggressiveMoves);
	}
	
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
			
			boardCopy.setPossibleMoves();
			boardCopy.setCheck();
			if ((this.owner.getName() == Name.white) && (!boardCopy.getWhite().isInCheck())) {
				validMoves.add(possibleMove);
			}
			if ((this.owner.getName() == Name.black) && (!boardCopy.getBlack().isInCheck())) {
				validMoves.add(possibleMove);
			}
		}
	}

	
	/**
	 * Sets all possible moves for this piece
	 * @param board the instance of the board the piece is on
	 */
	public abstract void setPossibleMoves(Board board);


}
