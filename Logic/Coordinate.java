package Logic;

public class Coordinate {
	private int row;
	private int column;
	
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	/**
	 * Method returns whether the coordinate is off the board
	 * @return true if off board, false otherwise
	 */
	public boolean offBoard() {
		if (row > 7 || row < 0 || column > 7 || column < 0) {
			return true;
		}
		return false;
	}
}
