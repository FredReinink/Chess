package Logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Pieces.*;
import Resources.Name;

public class Board implements Serializable {
	private Square[][] board;
	private Player white;
	private Player black;
	
	public Board deepCopy(Board boardToCopy) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(boardToCopy);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Board) ois.readObject();
		} catch (IOException e) {
			System.out.println("Board Copying error: IO");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Board Copying error: ClassNotFound");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @return returns the 2d array of squares that make up this board.
	 */
	public Square[][] getSquares() {
		return board;
	}
	
	public Player getWhite() {
		return white;
	}
	
	public Player getBlack() {
		return black;
	}
	
	/**
	 * Updates the board with new piece and player states
	 */
	public void update() {
		setValidMoves();
		setAggression();
		setValidMoves();
	}
	
	/**
	 * Sets all valid moves for all pieces on the board.
	 */
	public void setValidMoves() {
		for (Piece p : white.getPieces()) {
			p.setValidMoves(this);
		}
		for (Piece p : black.getPieces()) {
			p.setValidMoves(this);
		}
	}
	
	/**
	 * Sets the squares to be under attack by the opposing players' valid moves.
	 */
	public void setAggression() {		
		clearAggression();
		
		for (Piece p : white.getPieces()) {
			for (Square s : p.getValidMoves()) {
				s.addAttackedBy(white);
			}
		}
		for (Piece p : black.getPieces()) {
			for (Square s : p.getValidMoves()) {
				s.addAttackedBy(black);
			}
		}
	}
	
	/**
	 * Sets all squares to be not under attack.
	 */
	public void clearAggression() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j].resetAttackedBy();
			}
		}
	}
	
	public String toString() {
		String result = "";
		
		result += "Player white has pieces: " + white.toString();
		result += "\nPlayer black has pieces: " + black.toString();
		result += "\nBoard representation:\n";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				result += " " + board[i][j].toString();
			}
			result += "\n";	
		}
		
		return result;
	}
	
	/**
	 * Builds a board out of squares. Each square is initialized with x and y coordinates.
	 * 
	 * @param white the player with Name white
	 * @param black the player with Name black
	 */
	public Board(Player white, Player black) {
		this.white = white;
		this.black = black;
		
		board = new Square[8][8];
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j ++) {
				board[i][j] = new Square(new Coordinate(i,j));
			}
		}
		placeOrderedPieces(white);
		placeOrderedPieces(black);
		System.out.println(this.toString());
	}
	
	/**
	 * Places pieces of a specified player on the board. Assumes that the player's piece list adheres to the following encoding:
	 * [pawn, pawn, pawn, pawn, pawn, pawn, pawn, pawn, rook, knight, bishop, queen, king, bishop, knight, rook]
	 * 
	 * @param player the player whose pieces are to be placed
	 */
	public void placeOrderedPieces(Player player) {
		int pawnRow;
		int kingRow;
		
		if (player.getName() == Name.white) {
			pawnRow = 6;
			kingRow = 7;
		} else {
			pawnRow = 1;
			kingRow = 0;
		}
		
		ArrayList<Piece> pieces = player.getPieces();

		int j = 8;
		for (int i = 0; i < 8; i++) {
			board[pawnRow][i].setPiece(pieces.get(i));
			board[kingRow][i].setPiece(pieces.get(j));
			j++;
		}	
	}
	
	/**
	 * Places one piece on the square with the designated coordinates
	 * 
	 * @param p the piece to be placed
	 * @param poisition the position of the square to place the piece
	 */
	public void placePiece(Piece p, Coordinate position) {
		board[position.getRow()][position.getColumn()].setPiece(p);
	}
}
