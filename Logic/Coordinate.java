package Logic;

import java.io.Serializable;

/**
 * @author Fred Reinink
 */
public class Coordinate implements Serializable{
	private int row;
	private int column;
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Determines if the coordinate is off the board.
	 * 
	 * @return true if off board, false otherwise.
	 */
	public boolean isOffBoard() {
		if (row > Board.NUM_ROWS - 1 || row < 0 || column > Board.NUM_COLS - 1 || column < 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if this and a specified coordinate have the same row and column values.
	 * 
	 * @param coordToCompare
	 */
	public boolean equals(Coordinate coordToCompare) {
		if (row == coordToCompare.getRow()) {
			if (column == coordToCompare.getColumn()) {
				return true;
			}
		}
		return false;
	}
}
