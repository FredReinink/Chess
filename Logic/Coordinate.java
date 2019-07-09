package Logic;

import java.io.Serializable;

public class Coordinate implements Serializable {
	private int row;
	private int column;
	
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Coordinate(Coordinate coordinateToCopy) {
		this.row = coordinateToCopy.getRow();
		this.column = coordinateToCopy.getColumn();
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
	public boolean offBoard() {
		if (row > 7 || row < 0 || column > 7 || column < 0) {
			return true;
		}
		return false;
	}
}
