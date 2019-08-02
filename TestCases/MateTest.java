package TestCases;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Pieces.*;
import Resources.Name;
import Logic.*;

public class MateTest {
	
	/**
	 * Tests if checkmate is detected correctly.
	 * Board configuration: https://i.imgur.com/wyxsCMR.png
	 */
	@Test
	void checkmate1Test() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		
		Board board = new Board(white, black);
		
		Coordinate whiteKingPosition = new Coordinate(Board.WHITE_KING_ROW, Board.COL_6);
		Coordinate whitePawn1Position = new Coordinate(Board.WHITE_PAWN_ROW, Board.COL_5);
		Coordinate whitePawn2Position = new Coordinate(Board.WHITE_PAWN_ROW, Board.COL_6);
		Coordinate whitePawn3Position = new Coordinate(Board.WHITE_PAWN_ROW, Board.COL_7);
		
		Pawn whitePawn1 = new Pawn(white);
		Pawn whitePawn2 = new Pawn(white);
		Pawn whitePawn3 = new Pawn(white);
		white.addPiece(whitePawn1);
		white.addPiece(whitePawn2);
		white.addPiece(whitePawn3);
		
		Coordinate blackRookPosition = new Coordinate(Board.WHITE_KING_ROW, Board.COL_3);
		Rook blackRook = new Rook(black);
		black.addPiece(blackRook);
		
		white.getKing().move(board, whiteKingPosition);
		board.placePiece(whitePawn1, whitePawn1Position);
		board.placePiece(whitePawn2, whitePawn2Position);
		board.placePiece(whitePawn3, whitePawn3Position);
		board.placePiece(blackRook, blackRookPosition);
		
		board.update();
		
		assertFalse(white.hasAValidMove(), "White should not have a valid move in a back-rank checkmate");
		assertTrue(white.isInCheck(), "White should be in check in a back-rank checkmate");
	}
	
	/**
	 * Tests if checkmate is detected correctly.
	 * Board configuration: https://i.imgur.com/KEePSPb.png
	 */
	@Test 
	void checkmate2Test() {
		Player white = new Player(Name.white, true);
		Player black = new Player(Name.black, true);
		
		Board board = new Board(white, black, null);
		
		Coordinate whitePawn1Move = new Coordinate(Board.ROW_4, Board.COL_5);
		Coordinate whitePawn2Move = new Coordinate(Board.ROW_4, Board.COL_6);
		white.getPieces().get(Board.COL_5).move(board, whitePawn1Move);
		white.getPieces().get(Board.COL_6).move(board, whitePawn2Move);
		
		Coordinate blackPawnMove = new Coordinate(Board.BLACK_ENPASSENT_ROW, Board.COL_4);
		Coordinate blackQueenMove = new Coordinate(Board.ROW_4, Board.COL_7);
		black.getPieces().get(Board.COL_4).move(board, blackPawnMove);
		black.getPieces().get(Board.COL_3+Board.NUM_COLS).move(board, blackQueenMove);
		
		board.update();
		
		assertFalse(white.hasAValidMove(), "White should not have a valid move in a fool's mate");
		assertTrue(white.isInCheck(), "White should be in check in a fool's mate");
	}
	
	/**
	 * Tests if checkmate is detected correctly.
	 * Board configuration: https://i.imgur.com/xMUKRBN.png
	 */
	@Test
	void checkMate3Test() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		
		Board board = new Board(white, black);
		
		Bishop whiteBishop1 = new Bishop(white);
		Bishop whiteBishop2 = new Bishop(white);
		
		white.addPiece(whiteBishop1);
		white.addPiece(whiteBishop2);
		
		Coordinate blackKingPosition = new Coordinate(Board.BLACK_KING_ROW, Board.COL_7);
		black.getKing().move(board, blackKingPosition);
		
		Coordinate whiteBishop1Position = new Coordinate(Board.WHITE_ENPASSENT_ROW, Board.COL_1);
		Coordinate whiteBishop2Position = new Coordinate(Board.BLACK_PAWN_ROW, Board.COL_6);
		Coordinate whiteKingPosition = new Coordinate(Board.BLACK_ENPASSENT_ROW, Board.COL_2);
		board.placePiece(whiteBishop1, whiteBishop1Position);
		board.placePiece(whiteBishop2, whiteBishop2Position);
		white.getKing().move(board, whiteKingPosition);
		
		board.update();
		
		assertFalse(black.hasAValidMove(), "Black should not have a valid move in a double-bishop checkmate");
		assertTrue(black.isInCheck(), "Black should be in check in a double-bishop checkmate");
	}
	
	/**
	 * Tests if stalemate is detected correctly.
	 * Board configuration: https://i.imgur.com/Mcf6Xp0.png
	 */
	@Test
	void staleMateTest() {
		Player white = new Player(Name.white, false);
		Player black = new Player(Name.black, false);
		
		Board board = new Board(white, black);
		
		Rook blackRook = new Rook(black);
		Pawn blackPawn = new Pawn(black);
		
		black.addPiece(blackRook);
		black.addPiece(blackPawn);
		
		Coordinate whiteKingPosition = new Coordinate(Board.WHITE_ENPASSENT_ROW, Board.COL_5);
		white.getKing().move(board, whiteKingPosition);
		
		Coordinate blackRookPosition = new Coordinate(Board.WHITE_PAWN_ROW, Board.COL_1);
		Coordinate blackPawnPosition = new Coordinate(Board.ROW_4, Board.COL_5);
		Coordinate blackKingPosition = new Coordinate(Board.ROW_3, Board.COL_5);
		board.placePiece(blackRook, blackRookPosition);
		board.placePiece(blackPawn, blackPawnPosition);
		black.getKing().move(board, blackKingPosition);
		
		board.update();
		
		assertFalse(white.hasAValidMove(), "White should not have a valid move in a stalemate");
		assertFalse(white.isInCheck(), "White should not be in check in a stalemate");
	}
}
