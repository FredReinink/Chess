package TestCases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Logic.Board;
import Logic.Coordinate;
import Logic.Player;
import Pieces.King;
import Pieces.Rook;
import Resources.Name;
import Resources.Side;
import Runner.Controller;

public class CastlingTest {
	
	/**
	 * Tests for cases where left and right castling is forfeited by a player's king moving.
	 */
	@Test
	void castleKingForfeitTest() {
		Player white = new Player(Name.white, true);
		Player black = new Player(Name.black, true);
		
		Board board = new Board(white, black, null);
		
		King whiteKing = white.getKing();
		King blackKing = black.getKing();
		
		Coordinate whiteKingMove = new Coordinate(Board.WHITE_KING_ROW, Board.COL_3);
		Coordinate blackKingMove = new Coordinate(Board.BLACK_KING_ROW, Board.COL_3);
		
		assertTrue(blackKing.isLeftCastleAvailable(), "Left castling should be available for black initially");
		assertTrue(blackKing.isRightCastleAvailable(), "Right castling should be available for black initially");
		assertTrue(whiteKing.isLeftCastleAvailable(), "Left castling should be available for white initially");
		assertTrue(whiteKing.isRightCastleAvailable(), "Right castling should be available for white initially");
		
		whiteKing.move(board, whiteKingMove);
		blackKing.move(board, blackKingMove);
		
		assertFalse(blackKing.isLeftCastleAvailable(), "Left castling should be unavailable for black after black's king moves");
		assertFalse(blackKing.isRightCastleAvailable(), "Right castling should be unavailable for black after black's king moves");
		assertFalse(whiteKing.isLeftCastleAvailable(), "Left castling should be unavailable for white after white's left rook moves");
		assertFalse(whiteKing.isRightCastleAvailable(), "Right castling should be unavailable for white after white's left rook moves");
	}

	/**
	 * Tests for cases where left castling is forfeited by a player's left rook moving.
	 */
	@Test
	void leftCastleRookForfeitTest() {
		Player white = new Player(Name.white, true);
		Player black = new Player(Name.black, true);
		
		Board board = new Board(white, black, new Controller());
		
		Rook whiteLeftRook = white.getRookonSide(Side.left);
		King whiteKing = white.getKing();
		
		Rook blackLeftRook = black.getRookonSide(Side.left);
		King blackKing = black.getKing();
		
		Coordinate whiteRookMove = new Coordinate(Board.WHITE_ENPASSENT_ROW, Board.COL_1);
		Coordinate blackRookMove = new Coordinate(Board.BLACK_ENPASSENT_ROW, Board.COL_1);
		
		blackLeftRook.move(board, blackRookMove);
		whiteLeftRook.move(board, whiteRookMove);
		
		assertFalse(blackKing.isLeftCastleAvailable(), "Left castling should be unavailable for black after black's left rook moves");
		assertFalse(whiteKing.isLeftCastleAvailable(), "Left castling should be unavailable for white after white's left rook moves");
		assertTrue(blackKing.isRightCastleAvailable(), "Right castling should still be available for black after black's left rook moves");
		assertTrue(whiteKing.isRightCastleAvailable(), "Right castling should still be available for white after white's left rook moves");
	}
	
	/**
	 * Tests for cases where right castling is forfeited by a player's right rook moving.
	 */
	@Test
	void rightCastleRookForfeitTest() {
		Player white = new Player(Name.white, true);
		Player black = new Player(Name.black, true);
		
		Board board = new Board(white, black, null);
		
		Rook whiteRightRook = white.getRookonSide(Side.right);
		King whiteKing = white.getKing();
		
		Rook blackRightRook = black.getRookonSide(Side.right);
		King blackKing = black.getKing();
		
		Coordinate whiteRookMove = new Coordinate(Board.WHITE_ENPASSENT_ROW, Board.COL_7);
		Coordinate blackRookMove = new Coordinate(Board.BLACK_ENPASSENT_ROW, Board.COL_7);
		
		blackRightRook.move(board, blackRookMove);
		whiteRightRook.move(board, whiteRookMove);
		
		assertFalse(blackKing.isRightCastleAvailable(), "Right castling should be unavailable for black after black's right rook moves");
		assertFalse(whiteKing.isRightCastleAvailable(), "Right castling should be unavailable for white after white's right rook moves");
		assertTrue(blackKing.isLeftCastleAvailable(), "Left castling should still be available for black after black's right rook moves");
		assertTrue(whiteKing.isLeftCastleAvailable(), "Left castling should still be available for white after white's right rook moves");
	}
}
