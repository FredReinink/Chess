package TestCases;
import GUI.*;

import Logic.*;
import Pieces.*;
import Resources.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class InitializeBoardTest {

	@Test
	void initBoardTest() {
		Player white = new Player(Name.white);
		Player black = new Player(Name.black);
		
		ArrayList<Piece> pieces = white.getPieces();
		Board board = new Board(white,black);
		Square[][] squares = board.getSquares();
		
		Piece piece00 = squares[0][0].getPiece();
		assertTrue(piece00 instanceof Rook && piece00.getOwner() == black);
		Piece piece01 = squares[0][1].getPiece();													
		assertTrue(piece01 instanceof Knight && piece01.getOwner() == black);	
		Piece piece02 = squares[0][2].getPiece();
		assertTrue(piece02 instanceof Bishop && piece02.getOwner() == black);
		Piece piece03 = squares[0][3].getPiece();
		assertTrue(piece03 instanceof Queen && piece03.getOwner() == black);
		Piece piece04 = squares[0][4].getPiece();
		assertTrue(piece04 instanceof King && piece04.getOwner() == black);
		Piece piece05 = squares[0][5].getPiece();
		assertTrue(piece05 instanceof Bishop && piece05.getOwner() == black);
		Piece piece06 = squares[0][6].getPiece();
		assertTrue(piece06 instanceof Knight && piece06.getOwner() == black);
		Piece piece07 = squares[0][7].getPiece();
		assertTrue(piece07 instanceof Rook && piece07.getOwner() == black);
		
		Piece piece10 = squares[1][0].getPiece();
		assertTrue(piece10 instanceof Pawn && piece10.getOwner() == black);
		Piece piece11 = squares[1][1].getPiece();
		assertTrue(piece11 instanceof Pawn && piece11.getOwner() == black);
		Piece piece12 = squares[1][2].getPiece();
		assertTrue(piece12 instanceof Pawn && piece12.getOwner() == black);
		Piece piece13 = squares[1][3].getPiece();
		assertTrue(piece13 instanceof Pawn && piece13.getOwner() == black);
		Piece piece14 = squares[1][4].getPiece();
		assertTrue(piece14 instanceof Pawn && piece14.getOwner() == black);
		Piece piece15 = squares[1][5].getPiece();
		assertTrue(piece15 instanceof Pawn && piece15.getOwner() == black);
		Piece piece16 = squares[1][6].getPiece();
		assertTrue(piece16 instanceof Pawn && piece16.getOwner() == black);
		Piece piece17 = squares[1][7].getPiece();
		assertTrue(piece17 instanceof Pawn && piece17.getOwner() == black);
		
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				assertTrue(squares[i][j].getPiece() == null);
			}
		}
		
		Piece piece60 = squares[6][0].getPiece();
		assertTrue(piece60 instanceof Pawn && piece60.getOwner() == white);
		Piece piece61 = squares[6][1].getPiece();
		assertTrue(piece61 instanceof Pawn && piece61.getOwner() == white);
		Piece piece62 = squares[6][2].getPiece();
		assertTrue(piece62 instanceof Pawn && piece62.getOwner() == white);
		Piece piece63 = squares[6][3].getPiece();
		assertTrue(piece63 instanceof Pawn && piece63.getOwner() == white);
		Piece piece64 = squares[6][4].getPiece();
		assertTrue(piece64 instanceof Pawn && piece64.getOwner() == white);
		Piece piece65 = squares[6][5].getPiece();
		assertTrue(piece65 instanceof Pawn && piece65.getOwner() == white);
		Piece piece66 = squares[6][6].getPiece();
		assertTrue(piece66 instanceof Pawn && piece66.getOwner() == white);
		Piece piece67 = squares[6][7].getPiece();
		assertTrue(piece67 instanceof Pawn && piece67.getOwner() == white);
		
		Piece piece70 = squares[7][0].getPiece();
		assertTrue(piece70 instanceof Rook && piece70.getOwner() == white);
		Piece piece71 = squares[7][1].getPiece();													
		assertTrue(piece71 instanceof Knight && piece71.getOwner() == white);	
		Piece piece72 = squares[7][2].getPiece();
		assertTrue(piece72 instanceof Bishop && piece72.getOwner() == white);
		Piece piece73 = squares[7][3].getPiece();
		assertTrue(piece73 instanceof Queen && piece73.getOwner() == white);
		Piece piece74 = squares[7][4].getPiece();
		assertTrue(piece74 instanceof King && piece74.getOwner() == white);
		Piece piece75 = squares[7][5].getPiece();
		assertTrue(piece75 instanceof Bishop && piece75.getOwner() == white);
		Piece piece76 = squares[7][6].getPiece();
		assertTrue(piece76 instanceof Knight && piece76.getOwner() == white);
		Piece piece77 = squares[7][7].getPiece();
		assertTrue(piece77 instanceof Rook && piece77.getOwner() == white);
		

	}

}
