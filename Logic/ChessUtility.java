package Logic;

import java.io.*;

import Pieces.*;
import Resources.Name;

/**
 * General Utility functions.
 * 
 * @author Fred Reinink
 */
public class ChessUtility {
	public static final String WHITE_PAWN_PATH = "src/Resources/PieceImages/WhitePawn.png";
	public static final String BLACK_PAWN_PATH = "src/Resources/PieceImages/BlackPawn.png";
	public static final String WHITE_BISHOP_PATH = "src/Resources/PieceImages/WhiteBishop.png";
	public static final String BLACK_BISHOP_PATH = "src/Resources/PieceImages/BlackBishop.png";
	public static final String BLACK_KING_PATH = "src/Resources/PieceImages/BlackKing.png";
	public static final String WHITE_KING_PATH = "src/Resources/PieceImages/WhiteKing.png";
	public static final String BLACK_KNIGHT_PATH = "src/Resources/PieceImages/BlackKnight.png";
	public static final String WHITE_KNIGHT_PATH = "src/Resources/PieceImages/WhiteKnight.png";
	public static final String WHITE_QUEEN_PATH = "src/Resources/PieceImages/WhiteQueen.png";
	public static final String BLACK_QUEEN_PATH = "src/Resources/PieceImages/BlackQueen.png";
	public static final String BLACK_ROOK_PATH = "src/Resources/PieceImages/BlackRook.png";
	public static final String WHITE_ROOK_PATH = "src/Resources/PieceImages/WhiteRook.png";
	
	/**
	 * Finds and returns the file containing the image of the specified piece.
	 * 
	 * @param piece The piece to find the image file of.
	 * @return the image File of the desired piece.
	 */
	public static File findFile(Piece piece) {
		File pieceFile = null;

		if (piece instanceof Pawn) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_PAWN_PATH);
			} else {
				pieceFile = new File(BLACK_PAWN_PATH);
			}
		} else if (piece instanceof Bishop) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_BISHOP_PATH);
			} else {
				pieceFile = new File(BLACK_BISHOP_PATH);
			}
		} else if (piece instanceof Knight) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_KNIGHT_PATH);
			} else {
				pieceFile = new File(BLACK_KNIGHT_PATH);
			}
		} else if (piece instanceof Rook) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_ROOK_PATH);
			} else {
				pieceFile = new File(BLACK_ROOK_PATH);
			}
		} else if (piece instanceof King) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_KING_PATH);
			} else {
				pieceFile = new File(BLACK_KING_PATH);
			}
		} else if (piece instanceof Queen) {
			if (piece.getOwner().getName() == Name.white) {
				pieceFile = new File(WHITE_QUEEN_PATH);
			} else {
				pieceFile = new File(BLACK_QUEEN_PATH);
			}
		}
		return pieceFile;
	}
	
	/**
	 * Creates and returns a deep copy of the specified object using serialization. 
	 * 
	 * @param objectToCopy
	 * @return Returns a deep copy of objectToCopy
	 */
	public static Object deepCopy(Object objectToCopy) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(objectToCopy);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException e) {
			System.out.println("Object Copying error: IO");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Object Copying error: ClassNotFound");
			e.printStackTrace();
			return null;
		}
	}
}
