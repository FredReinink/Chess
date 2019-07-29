package Logic;

import java.io.Serializable;

public class Coordinate implements Serializable{
	private int row;
	private int column;
	
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
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
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	/**
	 * Determines if the coordinate is off the board
	 * @return true if off board, false otherwise
	 */
	public boolean isOffBoard() {
		if (row > 7 || row < 0 || column > 7 || column < 0) {
			return true;
		}
		return false;
	}
}
