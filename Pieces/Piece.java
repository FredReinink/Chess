package Pieces;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Logic.*;
import Resources.Name;

public abstract class Piece implements Serializable {
	protected Player owner;
	protected Coordinate position;
	protected ArrayList<Square> validMoves = new ArrayList<Square>();

	public static Piece deepCopy(Piece pieceToCopy) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(pieceToCopy);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Piece) ois.readObject();
		} catch (IOException e) {
			System.out.println("Piece Copying error: IO");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Piece Copying error: ClassNotFound");
			e.printStackTrace();
			return null;
		}
	}

	public Piece(Player owner) {
		this.owner = owner;
	}

	public Piece(Player owner, Coordinate position) {
		this.owner = owner;
		this.position = new Coordinate(position);
	}

	/**
	 * Adds valid moves to the list of valid moves. Filters candidate moves that will either:
	 * 1. If the owners king is already in check, does not get the owner out of check
	 * 2. Place the owners king in check
	 * 
	 * @param candidateMove the move to be checked
	 */
	public void addValidMove(Board board, Square candidateMove, Piece pieceToMove) {
		Board testBoard = board.deepCopy(board);
		Piece testPiece = deepCopy(pieceToMove);

		testPiece.move(testBoard, candidateMove.getPosition());

		if (this.owner.getName() == Name.white) {
			if (!testBoard.getWhite().isCheck()) {
				validMoves.add(candidateMove);
			}
		} else if (this.owner.getName() == Name.black) {
			if (!testBoard.getBlack().isCheck()) {
				validMoves.add(candidateMove);
			}
		}
	}

	/**
	 * Moves a piece from it's current square to another
	 * 
	 * @param board the instance of board the piece is on
	 * @param newPosition the position to move the piece to
	 */
	public void move(Board board, Coordinate newPosition) {
		board.placePiece(null, position);
		board.placePiece(this, newPosition);
	}

	/**
	 * Helper method for pieces that use vectors to determine valid moves.
	 * 
	 * Checks if a square is a valid move. If the square contains an enemy piece, adds that square to valid moves before returning that vector as blocked.
	 * If the square contains a friendly piece, returns that vector as immediately blocked.
	 * 
	 * @param candidateMove 
	 * @return false when the vector is not blocked by the candidateMove, true otherwise
	 */
	protected boolean vectorMoveHelper(Board board, Square candidateMove) {
		boolean blocked = false;
		if (candidateMove.getPiece() == null) {
			addValidMove(board, candidateMove, this);
		} else if (ChessUtility.containsEnemyPiece(candidateMove,this.owner.getName())) {
			addValidMove(board, candidateMove, this);
			blocked = true;
		} else if (ChessUtility.containsFriendlyPiece(candidateMove,this.owner.getName())) {
			blocked = true;
		}
		return blocked;
	}

	/**
	 * Helper method for pieces that select specific squares to determine valid moves.
	 * 
	 * Adds a square corresponding to a Coordinate c to the board if it is a valid move.
	 * 
	 * @param board the instance of board the piece wants to move on.
	 * @param c the coordinate of the square the piece wants to move to.
	 */
	protected void pointMoveHelper(Board board, Coordinate c) {
		if (c.offBoard() == false) {
			Square candidateMove = board.getSquares()[c.getRow()][c.getColumn()];
			if (!ChessUtility.containsFriendlyPiece(candidateMove,this.owner.getName())) {
				addValidMove(board, candidateMove, this);
			}
		}
	}

	public ArrayList<Square> getValidMoves(){
		return validMoves;
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
	 * Removes all valid moves
	 */
	public void resetMoves() {
		validMoves.removeAll(validMoves);
	}

	/**
	 * Sets all valid moves for this piece
	 * @param board the instance of the board the piece is on
	 */
	public abstract void setValidMoves(Board board);
}
