import java.awt.Point;

//Specialized class to represent the board for the egg carton puzzle
public class Board {
	
	private int rows;
	private int columns;
	private int k;
	
	public Board(int size, int k) {
		this.rows = size;
		this.columns = size;
		this.k = k;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getK() {
		return k;
	}

}
