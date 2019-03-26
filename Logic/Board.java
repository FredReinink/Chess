package Logic;

import java.util.ArrayList;

import Pieces.*;
import Resources.Name;

public class Board {
	Square[][] board;
	Player white;
	Player black;
	
	/**
	 * @return returns the 2d array of squares that make up this board.
	 */
	public Square[][] getSquares() {
		return board;
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
				board[i][j] = new Square(i,j);
			}
		}
		placeOrderedPieces(white);
		placeOrderedPieces(black);
	}
	
	/**
	 * Places pieces of a specified player on the board. Warning: Assumes that the player's piece list adheres to the following encoding:
	 * [pawn, pawn, pawn, pawn, pawn, pawn, pawn, pawn, rook, knight, bishop, queen, king, bishop, knight, rook]
	 * 
	 * @param player the player whose pieces are to be placed
	 */
	public void placeOrderedPieces(Player player) {
		int pawnRow;
		int rookRow;
		
		if (player.getName() == Name.white) {
			pawnRow = 6;
			rookRow = 7;
		} else {
			pawnRow = 1;
			rookRow = 0;
		}
		
		ArrayList<Piece> pieces = player.getPieces();
		
		//place pawns (pawns are the first 8 elements of the list)
		for (int i = 0; i < 8; i++) {
			board[pawnRow][i].setPiece(pieces.get(i));
		}
		//places the other row (stored in the 8 remaining elements of the list)
		for (int i = 0; i < 8; i++) {
			board[rookRow][i].setPiece(pieces.get(i));
		}
		
		
		
	}
	
	/**
	 * Places one piece on the square with the designated coordinates
	 * Helper method for placeOrderedPieces
	 * 
	 * @param p the piece to be placed
	 * @param x the x coordinate of the square to place the piece
	 * @param y the y coordinate of the square to place the piece
	 */
	public void placePiece(Piece p, int x, int y) {
		board[x][y].setPiece(p);
	}
}
