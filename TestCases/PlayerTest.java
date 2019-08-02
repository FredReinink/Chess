package TestCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import Logic.Player;
import Pieces.*;
import Resources.Name;

public class PlayerTest {
	
	/**
	 * Tests if the piece list is initialized with the right number of pieces.
	 */
	@Test
	void constructorTest_PiecesLength() {
		Player player = new Player(Name.white, true);
		ArrayList<Piece> pieces = player.getPieces();
		assertEquals(16,pieces.size(), "A player's piece list should have 16 pieces");
	}
	
	/**
	 * Tests if the piece list is initialized with the correct order.
	 */
	@Test
	void constructorTest_Order() {
		Player player = new Player(Name.white, true);
		ArrayList<Piece> pieces = player.getPieces();
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
