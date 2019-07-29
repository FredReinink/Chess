package TestCases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Pieces.*;
import Resources.Name;
import Logic.*;

public class PieceTest {
	
	/**
	 * Tests equalsTest for 
	 * 
	 */
	@Test
	void equalsTest() {
		Player white = new Player(Name.white); 
		Player black = new Player(Name.black);
		
		Queen whiteQueen = new Queen(white);
		Queen blackQueen = new Queen(black);
		Rook blackRook = new Rook(black);
		
		
		assertFalse(whiteQueen.equals(blackQueen), "Black queen and white queen should not be equal");
		assertTrue(whiteQueen.equals(whiteQueen), "White queen and white queen should be equal");
		assertFalse(blackQueen.equals(blackRook), "Black rook and black queen should not be equal");

	}

}
