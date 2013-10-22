import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Specialized class for the Egg Carton Puzzle
 * @author Torgeir, Olav
 *
 */
public class EggCartonNode extends Node{
	
	private static final int UP = 0;
	private static final int DOWN = 2;
	
	//representation of the eggs in this node
	private boolean[][] eggs;
	
	
	public EggCartonNode(Board board) {
		super(board);
		eggs = new boolean[board.getRows()][board.getColumns()];
	}
	
	public EggCartonNode(Board board, boolean[][] eggs) {
		this(board);
		this.eggs = eggs;

	}
	
	/**
	 * generates all neighbours to this node by moving one egg up or down one coordinate.
	 * One movement = one new neighbour
	 */
	public ArrayList<Node> generateNeighbours() {
		ArrayList<Node> neighbours = new ArrayList<Node>();;
		
		//loops through the board and makes a neighbour of every legal egg-move
		for (int i = 0; i < board.getColumns(); i++) {
			for (int j = 0; j < board.getRows(); j++) {
				if (eggs[j][i]) {
					if (isLegalMove(i, j, UP)) {
						neighbours.add(new EggCartonNode(board, cloneArrayAndMakeNeighbour(eggs, i, j, UP)));
					}
					if (isLegalMove(i, j, DOWN)) {
						neighbours.add(new EggCartonNode(board, cloneArrayAndMakeNeighbour(eggs, i, j, DOWN)));
					}
				}
			}
		}
		return neighbours;
	}
	
	/**
	 * Makes a copy of the eggs-array that reflects the movement of an egg
	 * @param src Old array
	 * @param oldX Position of the egg to be moved
	 * @param oldY Position of the egg to be moved
	 * @param direction The direction the egg should be moved
	 * @return Updated Array
	 */
	private static boolean[][] cloneArrayAndMakeNeighbour(boolean[][] src, int oldX, int oldY, int direction) {
	    int length = src.length;
	    boolean[][] target = new boolean[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    target[oldY][oldX] = false;
	    if (direction == UP) {
	    	target[oldY - 1][oldX] = true;
	    }
	    else if (direction == DOWN) {
	    	target[oldY + 1][oldX] = true;
	    }
	    return target;
	}
	
	/**
	 * Objective function. Each egg that satisfies the constraints gets a point
	 * @return double from 0.0 - 1.0 based on the correctness of this node
	 */
	public double evaluate() {
		int r = board.getRows();
		int c = board.getColumns();
		int k = board.getK();
		
		//Arrays used to check each directional constraint
		int[] rowK = new int[c];
		int[] colK = new int[r];
		int[] diagLeftK = new int[r + c - 1];
		int[] diagRightK = new int[r + c - 1];
		double heuristic = 0;
		
		//Sets up the constraints by accumulating eggs in each row/column/diagonals
		for (int y = 0; y < r; y ++) {
			for (int x = 0; x < c; x ++) {
				if (eggs[y][x]) {
					rowK[y] ++;
					colK[x] ++;
					int indexDiagRight = c - x + y - 1;
					int indexDiagLeft = x + y;
					diagRightK[indexDiagRight] ++;
					diagLeftK[indexDiagLeft] ++;
				}
			}
		}
		
		//each egg is tested against the constraints. If the constraints are met, the egg generates a point
		for (int y = 0; y < r; y++) {
			for (int x = 0; x < c; x++) {
				if (eggs[y][x]) {
					int indexDiagRight = c - x + y - 1;
					int indexDiagLeft = x + y;
					if ((rowK[y] <= k) && (colK[x] <= k) && (diagRightK[indexDiagRight] <= k) && 
							(diagLeftK[indexDiagLeft] <= k)) {
						heuristic++;
					}
				}
			}
		}
		//Accumulated points is divided by boardsize*allowed eggs in each row/col/diagonal to get a double ranging from 0.0 - 1.0 
		return heuristic / (r * k);
	}
	
	/**
	 * Creates a random starting state by adding k eggs in each column
	 */
	public void makeThisTheInititalNode() {
		Random r = new Random();
		for (int i = 0; i < board.getColumns(); i++) {
			int counter = 0;
			while(counter < board.getK()) {
				int row = r.nextInt(board.getRows());
				if (!eggs[row][i]) {
					eggs[row][i] = true;
					counter++;
				}
			}
		}
	}
	
	/**
	 * Checks if a move is legal. I.e an egg should not be moved out of the board or on top of another egg
	 * @param x Position of egg
	 * @param y Position of egg
	 * @param direction Direction it wants to move
	 * @return If the move is legal
	 */
	private boolean isLegalMove(int x, int y, int direction) {
		if ((direction == UP) && (y-1 >= 0) && (!eggs[y-1][x])) return true;
		else if ((direction == DOWN) && (y + 1 < board.getRows()) && (!eggs[y+1][x])) return true;
		return false;
	}
	
	public String toString() {
		String res = "";
		
		for (int y = 0; y < board.getRows(); y++) {
			for (int x = 0; x < board.getRows(); x++) {
				if (eggs[y][x]) {
					res += "#  ";
				}
				else {
					res += "-  ";
				}
			}
			res += "\n";
		}
		return res;
	}
}
