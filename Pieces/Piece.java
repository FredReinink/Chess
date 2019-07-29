package Pieces;

import java.io.Serializable;

import java.util.ArrayList;

import Logic.*;
import Resources.Name;

/**
 * Piece and all it's subclasses handle piece movement and game logic surrounding piece-specific rules.
 * 
 * @author Fred Reinink
 *
 */
public abstract class Piece implements Serializable{

	protected Player owner;
	protected Coordinate position;
	private boolean dead = false;
	
	/* A possible move is a move that a piece can make purely based on a piece's rules for movement (includes potentially illegal moves where a move places the owner in check etc.). */
	protected ArrayList<Square> possibleMoves = new ArrayList<Square>();
	
	/* A valid move is a move that a player is legally allowed to make. */
	private ArrayList<Square> validMoves = new ArrayList<Square>();
	
	/* An aggressive move is a move that a piece can make where it can take an opposing piece. All possible moves are aggressive moves except in the case where the possible move is a pawn moving directly forward or a king is castling */
	protected ArrayList<Square> aggressiveMoves = new ArrayList<Square>();
		
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

	public Piece(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * @return whether the piece is dead or not
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Sets this piece to be dead and clears all it's move lists.
	 */
	public void setDead(boolean dead) {
		resetPossibleMoves();
		resetValidMoves();
		resetAggressiveMoves();
		this.dead = dead;
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
	 * Moves this piece to the desired position on the board and handles all special conditions related to moving a piece. 
	 * 
	 * @param board the instance of the board the piece is on
	 * @param newPosition coordinate of the desired position
	 */
	public void move(Board board, Coordinate newPosition) {
		castleForfeitChecker(board);
		enPassentHandler(board, newPosition);
		castlingHandler(board, newPosition);
		
		board.placePiece(null, position);
		board.placePiece(this, newPosition);
		
		promotionHandler(board, newPosition);
	}
	
	/**
	 * Sets all valid moves for this piece.
	 * 
	 * @param board the instance of the board the piece is on
	 */
	public void setValidMoves(Board board) {
		resetValidMoves();
		
		ArrayList<Square> possibleMovesCopy = new ArrayList<Square>(possibleMoves);
		
		for (Square possibleMove : possibleMovesCopy) {
			Board boardCopy = (Board) ChessUtility.deepCopy(board);
			
			//gets the piece on the copied board that corresponds to this piece
			Piece copiedPiece = boardCopy.getSquares()[position.getRow()][position.getColumn()].getPiece();
			
			if (copiedPiece != null) {
				copiedPiece.move(boardCopy, possibleMove.getPosition());
			}
			
			boardCopy.testBoardUpdate();
			if ((this.owner.getName() == Name.white) && (!boardCopy.getWhite().isInCheck())) {
				validMoves.add(possibleMove);
				owner.setHasAValidMove(true);
			}
			if ((this.owner.getName() == Name.black) && (!boardCopy.getBlack().isInCheck())) {
				validMoves.add(possibleMove);
				owner.setHasAValidMove(true);
			}
		}
	}
	
	/**
	 * Helper method for pieces that select specific squares to determine possible moves.
	 * 
	 * Adds a square corresponding to a Coordinate c to the board if it is a possible move.
	 * 
	 * @param board the instance of board the piece wants to move on.
	 * @param c the coordinate of the square the piece wants to move to.
	 */
	protected void pointMoveHelper(Board board, Coordinate positionToCheck) {
		
		if (positionToCheck.isOffBoard() == false) {
			
			Square[][] squares = board.getSquares();
			Square candidateMove = squares[positionToCheck.getRow()][positionToCheck.getColumn()];
			
			if (!Board.containsFriendlyPiece(candidateMove,owner.getName())) {
				possibleMoves.add(candidateMove);
			}
		}
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
		} else if (Board.containsEnemyPiece(candidateMove,owner.getName())) {
			possibleMoves.add((candidateMove));
			blocked = true;
		} else if (Board.containsFriendlyPiece(candidateMove,owner.getName())) {
			blocked = true;
		}
		
		return blocked;
	}
	
	/**
	 * When castling is used, this method moves the involved pieces to the right squares.
	 * 
	 * @param board the instance of the board the piece is on.
	 * @param newPosition coordinate of the desired position.
	 */
	public void castlingHandler(Board board, Coordinate newPosition) {
	}
	
	/**
	 * Checks if a move results in a pawn being on it's promotion row. If so, gets user input for what piece type they would like and promotes the pawn accordingly.
	 * 
	 * @param board the instance of the board the piece is on.
	 * @param newPosition the coordinate of the desired position.
	 */
	public void promotionHandler(Board board, Coordinate newPosition) {
	}
	
	/**
	 * If a player forfeits their right to castle by moving their king or rook, sets the corresponding castleAvailable variables in King to false.
	 * 
	 * @param board the instance of the board the piece is on.
	 * @param newPosition the coordinate of the desired position.
	 */
	public void castleForfeitChecker(Board board) {
	}
	
	/**
	 * When en passent is used, this method kills the opposing pawn.
	 * When a pawn uses it's double-step move, this method sets enPassentAvailable for the appropriate square and removes enPassentAvailable after one turn.
	 * 
	 * @param board the board the instance of the board the piece is on.
	 * @param newPosition the coordinate of the desired position.
	 */
	public void enPassentHandler(Board board, Coordinate newPosition) {
		board.resetEnPassent();
	}
	
	/**
	 * Sets all possible and aggressive moves for this piece.
	 * 
	 * @param board the instance of the board the piece is on
	 */
	public abstract void setPossibleMoves(Board board);

}
