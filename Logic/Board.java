package Logic;

import java.util.ArrayList;

import Pieces.*;
import Resources.Name;

public class Board {
	private Square[][] board;
	private Player white;
	private Player black;
	
	/**
	 * @return returns the 2d array of squares that make up this board.
	 */
	public Square[][] getSquares() {
		return board;
	}
	
	public void update() {
		setPossibleMoves();
	}
	
	public void setCheck() {
		
	}
	
	public void setPossibleMoves() {
		white.setPossibleMoves(this);
		black.setPossibleMoves(this);
	}
	
	public void setValidMoves() {
		white.setValidMoves(this);
		black.setValidMoves(this);
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
	 * Sole constructor.
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
	 * @param row the row of the square to place the piece
	 * @param column the column coordinate of the square to place the piece
	 */
	public void placePiece(Piece p, Coordinate position) {
		board[position.getRow()][position.getColumn()].setPiece(p);
	}
}
