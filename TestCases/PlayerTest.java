package TestCases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import Logic.Player;
import Pieces.*;
import Resources.Name;

class PlayerTest {

	@Test
	void constructorTest_Name() {
		Player p = new Player(Name.white);
		ArrayList<Piece> pieces = p.getPieces();
		assertEquals(Name.white, p.getName(), "Player Name failure");
		p = new Player(Name.black);
		pieces = p.getPieces();
		assertEquals(Name.black, p.getName(), "Player Name failure");
	}
	
	@Test
	void constructorTest_PiecesLength() {
		Player p = new Player(Name.white);
		ArrayList<Piece> pieces = p.getPieces();
		assertEquals(16,pieces.size(), "Pieces length error");
	}
	
	@Test
	void constructorTest_Pieces() {
		Player p = new Player(Name.white);
		ArrayList<Piece> pieces = p.getPieces();
		assertTrue(pieces.get(0) instanceof Pawn);
		assertTrue(pieces.get(1) instanceof Pawn);
		assertTrue(pieces.get(2) instanceof Pawn);
		assertTrue(pieces.get(3) instanceof Pawn);
		assertTrue(pieces.get(4) instanceof Pawn);
		assertTrue(pieces.get(5) instanceof Pawn);
		assertTrue(pieces.get(6) instanceof Pawn);
		assertTrue(pieces.get(7) instanceof Pawn);
		assertTrue(pieces.get(8) instanceof Rook);
		assertTrue(pieces.get(9) instanceof Knight);
		assertTrue(pieces.get(10) instanceof Bishop);
		assertTrue(pieces.get(11) instanceof Queen);
		assertTrue(pieces.get(12) instanceof King);
		assertTrue(pieces.get(13) instanceof Bishop);
		assertTrue(pieces.get(14) instanceof Knight);
		assertTrue(pieces.get(15) instanceof Rook);
	}

}
