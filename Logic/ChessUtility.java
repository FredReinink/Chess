package Logic;

import Resources.Name;

public class ChessUtility {

	/**
	 * Determines if there is a piece on the square
	 * @param square the square to check
	 * @return
	 */
	public static boolean containsPiece(Square square) {
		if (square.getPiece() != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if there is an enemy piece on the square
	 * @param square the square to check
	 * @return
	 */
	public static boolean containsEnemyPiece(Square square, Name name) {
		if ((square.getPiece() != null) && (!(square.getPiece().getOwner().getName() == name))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if there is a friendly piece on the square
	 * @param square the square to check
	 * @return
	 */
	public static boolean containsFriendlyPiece(Square square, Name name) {
		if ((square.getPiece() != null) && (square.getPiece().getOwner().getName() == name)) {
			return true;
		}
		return false;
	}
}
