package TestCases;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

import Pieces.*;
import Resources.Name;
import Logic.*;

public class EnPassentTest {
	
	/**
	 * Tests if a black pawn can take a white pawn using en passent.
	 */
	@Test
	void blackEnPassentAvailable() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		Board board = new Board(white,black);
		
		Piece whitePawn = new Pawn(white);
		Piece blackPawn = new Pawn(black);
		white.addPiece(whitePawn);
		black.addPiece(blackPawn);
		
		Coordinate whitePawnPosition = new Coordinate(Board.WHITE_PAWN_ROW, Board.COL_1);
		Coordinate blackPawnPosition = new Coordinate(Board.ROW_4, Board.COL_2);
		
		board.placePiece(whitePawn, whitePawnPosition);
		board.placePiece(blackPawn, blackPawnPosition);
		board.update();
		
		Coordinate whiteTwoStepMove = new Coordinate(Board.ROW_4, Board.COL_1);
		whitePawn.move(board, whiteTwoStepMove);
		board.update();
		
		Square enPassentSquare = board.getSquares()[Board.WHITE_ENPASSENT_ROW][Board.COL_1];
		Boolean enPassentAvailable = enPassentSquare.getEnPassentAvailable();
		
		assertTrue(enPassentAvailable, "The square at position 5, 1 should be enPassenAvailable after a white pawn two step move to position 4, 1");
		assertTrue(blackPawn.getValidMoves().contains(enPassentSquare), "The square at position 4, 1 should be a valid move for the a black pawn at position 4, 2 after a white pawn two-step move to 4, 1");
	}
	
	/**
	 * Tests if a black pawn using it's right to en passent capture removes the corresponding white pawn correctly.
	 */
	@Test
	void blackEnPassentSuccess() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		Board board = new Board(white,black);
		
		Piece whitePawn = new Pawn(white);
		Piece blackPawn = new Pawn(black);
		white.addPiece(whitePawn);
		black.addPiece(blackPawn);
		
		Coordinate whitePawnPosition = new Coordinate(Board.WHITE_PAWN_ROW, Board.COL_1);
		Coordinate blackPawnPosition = new Coordinate(Board.ROW_4, Board.COL_2);
		
		board.placePiece(whitePawn, whitePawnPosition);
		board.placePiece(blackPawn, blackPawnPosition);
		board.update();
		
		Coordinate whiteTwoStepMove = new Coordinate(Board.ROW_4, Board.COL_1);
		whitePawn.move(board, whiteTwoStepMove);
		board.update();
		
		Square enPassentSquare = board.getSquares()[Board.WHITE_ENPASSENT_ROW][Board.COL_1];
		Boolean enPassentAvailable = enPassentSquare.getEnPassentAvailable();
		
		assertTrue(enPassentAvailable, "The square at position 5, 1 should be enPassenAvailable after a white pawn two step move to position 4, 1");
		assertTrue(blackPawn.getValidMoves().contains(enPassentSquare), "The square at position 4, 1 should be a valid move for the a black pawn at position 4, 2 after a white pawn two-step move to 4, 1");
		
		blackPawn.move(board, enPassentSquare.getPosition());
		board.update();
		
		assertTrue(whitePawn.isDead(), "The white pawn at position 4, 1 should be dead after a black en passent capture to 5, 1");
		assertTrue(board.getSquares()[whiteTwoStepMove.getRow()][whiteTwoStepMove.getColumn()].getPiece() == null, "The white pawn at position 4, 1 should not exist on the board after a black en passent capture to 5, 1");
	}
	
	/**
	 * Tests if a white pawn can take a black pawn using en passent.
	 */
	@Test
	void whiteEnPassentAvailable() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		Board board = new Board(white,black);
		
		Piece whitePawn = new Pawn(white);
		Piece blackPawn = new Pawn(black);
		white.addPiece(whitePawn);
		black.addPiece(blackPawn);
		
		Coordinate whitePawnPosition = new Coordinate(Board.ROW_3, Board.COL_1);
		Coordinate blackPawnPosition = new Coordinate(Board.BLACK_PAWN_ROW, Board.COL_2);
		
		board.placePiece(whitePawn, whitePawnPosition);
		board.placePiece(blackPawn, blackPawnPosition);
		board.update();
		
		Coordinate blackTwoStepMove = new Coordinate(Board.ROW_3, Board.COL_2);
		blackPawn.move(board, blackTwoStepMove);
		board.update();
		
		Square enPassentSquare = board.getSquares()[Board.BLACK_ENPASSENT_ROW][Board.COL_2];
		Boolean enPassentAvailable = enPassentSquare.getEnPassentAvailable();
		
		assertTrue(enPassentAvailable, "The square at position 2, 2 should be enPassenAvailable after a black pawn two step move to position 3, 2");
		assertTrue(whitePawn.getValidMoves().contains(enPassentSquare), "The square at position 2, 2 should be a valid move for the white pawn at position 3, 1 after a black pawn two-step move to 3, 2");
	}
	
	/**
	 * Tests if a white pawn using it's right to en passent capture removes the corresponding black pawn correctly.
	 */
	@Test
	void whiteEnPassentSuccess() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		Board board = new Board(white,black);
		
		Piece whitePawn = new Pawn(white);
		Piece blackPawn = new Pawn(black);
		white.addPiece(whitePawn);
		black.addPiece(blackPawn);
		
		Coordinate whitePawnPosition = new Coordinate(Board.ROW_3, Board.COL_1);
		Coordinate blackPawnPosition = new Coordinate(Board.BLACK_PAWN_ROW, Board.COL_2);
		
		board.placePiece(whitePawn, whitePawnPosition);
		board.placePiece(blackPawn, blackPawnPosition);
		board.update();
		
		Coordinate blackTwoStepMove = new Coordinate(Board.ROW_3, Board.COL_2);
		blackPawn.move(board, blackTwoStepMove);
		board.update();
		
		Square enPassentSquare = board.getSquares()[Board.BLACK_ENPASSENT_ROW][Board.COL_2];
		Boolean enPassentAvailable = enPassentSquare.getEnPassentAvailable();
		
		assertTrue(enPassentAvailable, "The square at position 2, 2 should be enPassenAvailable after a black pawn two step move to position 3, 2");
		assertTrue(whitePawn.getValidMoves().contains(enPassentSquare), "The square at position 2, 2 should be a valid move for the a white pawn at position 3, 1 after a black pawn two-step move to 3, 2");
		
		whitePawn.move(board, enPassentSquare.getPosition());
		board.update();
		
		assertTrue(blackPawn.isDead(), "The black pawn at position 3, 2 should be dead after a white en passent capture to 2, 2");
		assertTrue(board.getSquares()[blackTwoStepMove.getRow()][blackTwoStepMove.getColumn()].getPiece() == null, "The black pawn at position 3, 2 should not exist on the board after a white en passent capture to 2, 2");
	}
	
	/**
	 * Tests that en passent is no longer available after one move.
	 */
	@Test
	void enPassentTimedOut() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		Board board = new Board(white,black);
		
		Piece whitePawn = new Pawn(white);
		Piece blackPawn = new Pawn(black);
		Piece whiteKnight = new Knight(white);
		white.addPiece(whitePawn);
		black.addPiece(blackPawn);
		white.addPiece(whiteKnight);
		
		Coordinate whitePawnPosition = new Coordinate(Board.ROW_3, Board.COL_1);
		Coordinate blackPawnPosition = new Coordinate(Board.BLACK_PAWN_ROW, Board.COL_2);
		Coordinate whiteKnightPosition = new Coordinate(Board.ROW_4,Board.COL_5);
		
		board.placePiece(whitePawn, whitePawnPosition);
		board.placePiece(blackPawn, blackPawnPosition);
		board.placePiece(whiteKnight, whiteKnightPosition);
		board.update();
		
		Coordinate blackTwoStepMove = new Coordinate(Board.ROW_3, Board.COL_2);
		blackPawn.move(board, blackTwoStepMove);
		board.update();
		
		whiteKnight.move(board, whiteKnightPosition);
		Square enPassentSquare = board.getSquares()[Board.BLACK_ENPASSENT_ROW][Board.COL_2];
		Boolean enPassentAvailable = enPassentSquare.getEnPassentAvailable();
		
		assertFalse(enPassentAvailable, "The right to capture using en passent should be forfeitted after one turn");
	}
	
	/**
	 * Tests that en passent capture is only available for pawns.
	 */
	@Test
	void enPassentOnlyAvailableToPawns() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		Board board = new Board(white,black);
		
		Piece whitePawn = new Pawn(white);
		Piece blackPawn = new Pawn(black);
		Piece whiteKnight = new Knight(white);
		white.addPiece(whitePawn);
		black.addPiece(blackPawn);
		white.addPiece(whiteKnight);
		
		Coordinate whitePawnPosition = new Coordinate(Board.ROW_3, Board.COL_1);
		Coordinate blackPawnPosition = new Coordinate(Board.BLACK_PAWN_ROW, Board.COL_2);
		Coordinate whiteKnightPosition = new Coordinate(Board.ROW_4,Board.COL_1);
		
		board.placePiece(whitePawn, whitePawnPosition);
		board.placePiece(blackPawn, blackPawnPosition);
		board.placePiece(whiteKnight, whiteKnightPosition);
		board.update();
		
		Coordinate blackTwoStepMove = new Coordinate(Board.ROW_3, Board.COL_2);
		blackPawn.move(board, blackTwoStepMove);
		
		board.update();
		
		Square enPassentSquare = board.getSquares()[Board.BLACK_ENPASSENT_ROW][Board.COL_2];
		Boolean enPassentAvailable = enPassentSquare.getEnPassentAvailable();
		
		assertTrue(enPassentAvailable, "The square at position 2, 2 should be enPassenAvailable after a black pawn two step move to position 3, 2");
		assertTrue(whitePawn.getValidMoves().contains(enPassentSquare), "The square at position 2, 2 should be a valid move for the white pawn at 3, 1 after a black pawn two step move to 3, 2");
		
		whiteKnight.move(board, new Coordinate(Board.BLACK_ENPASSENT_ROW,Board.COL_2));
		board.update();
		
		assertFalse(blackPawn.isDead(), "A white knight moving on to a square that is eligible for en passent should not take the corresponding black pawn");
	}
}
