import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class SwitchBoardNode extends Node{
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	public static final int CROSS_PENALTY = 1;
	
	private ArrayList<Node> neighbours;
	
	private int[] wire;
	private int wireLength;
	
	public SwitchBoardNode(Board board) {
		super(board);
		this.wireLength = board.getRows() * board.getColumns() - 1;
		this.wire = new int[wireLength];

	}
	
	public SwitchBoardNode(Board board, int[] wire) {
		this(board);
		this.wire = wire;

	}

	public ArrayList<Node> generateNeighbours() {
		ArrayList<Node> neighbourList = new ArrayList<Node>();
		for (int i = 0; i < wireLength; i++) {
			for (int j = 1; j < 4; j++) {
				int[] copyWire = Arrays.copyOf(wire, wireLength);
				copyWire[i] = (wire[i] + j) % 4;
				
				if (isWiringLegal(copyWire)) {
					neighbourList.add(new SwitchBoardNode(board, copyWire));
					//System.out.println("last element: " + neighbourList.get(neighbourList.size() - 1));
				}		
			}
		}
		return neighbourList;
	}

	public double evaluate() {
		//System.out.println("eval: " + this);
		
		boolean[][] crossCheck = new boolean[board.getRows()][board.getColumns()];
		double heuristic = 0;
		//int directionShifts = 0;
		
		Point cP = board.getStart();
		
		//check if wire is crossed
		for (int i = 0; i < wireLength; i++) {
			
			/*
			if (i > 0 && wire[i] != wire[i-1]) {
				directionShifts++;
			}
			*/
			
			crossCheck[cP.y][cP.x] = true;
			cP = nextPointOnBoard(wire[i], cP);	
		}
		
		int trueCount = 0;
		for (int i = 0; i < crossCheck.length; i++) {
			for (int j = 0; j < crossCheck[0].length; j++) {
				if (crossCheck[i][j]) {
					trueCount++;
				}
			}
		}
		
		//check endpoint, calc manhattan using final cP
		//System.out.println("cpy: " + cP.y + "cpx: " + cP.x );
		//int manhattanDistance = Math.abs(cP.x-board.getEnd().x) + Math.abs(cP.y-board.getEnd().y);
		heuristic = (double)trueCount/(board.getColumns() * board.getRows());
		
		return heuristic;
	}

	public void makeThisTheInititalNode() {
		Random rand = new Random();
		Point currentP = board.getStart();
		for (int i = 0; i < wireLength; i++) {
			int direction = 0;
			do {
				direction = rand.nextInt(4);
			} while(!isOnBoard(direction, currentP));
			
			wire[i] = direction;
			currentP = nextPointOnBoard(direction, currentP);
		}
	}
	
	private boolean isWiringLegal(int[] wire) {
		Point currentPoint = board.getStart();
		for (int i = 0; i < wireLength; i++) {
			if (isOnBoard(wire[i], currentPoint)) {
				currentPoint = nextPointOnBoard(wire[i], currentPoint);
			}
			else {
				
				SwitchBoardNode n = new SwitchBoardNode(board, wire);
				//System.out.println("failed: " + n);
				
				return false;
			}
		}
		return true;
	}
	
	private Point nextPointOnBoard(int dir, Point p) {
		switch(dir) {
		case 0: return new Point(p.x, p.y - 1);
		case 1: return new Point(p.x + 1, p.y);
		case 2: return new Point(p.x, p.y + 1);
		case 3: return new Point(p.x - 1, p.y);
		default: return null;
		}
	}
	
	private boolean isOnBoard(int dir, Point p) {
		switch(dir) {
		case 0: return p.y > 0;
		case 1: return p.x < board.getColumns() - 1;
		case 2: return p.y < board.getRows() - 1;
		case 3: return p.x > 0;
		default: return false;
		}
	}
	
	public Node getRandomNeighbour() {
		if (neighbours == null) {
			neighbours = generateNeighbours();
		}
		//System.out.println("");
		//for (int i = 0; i < neighbours.size(); i++) {
		//	System.out.println("neigh: " + neighbours.get(i));
		//}
		
		Random r = new Random();
		return neighbours.get(r.nextInt(neighbours.size()));
	}
	
	public String toString() {	
		String ret = "";
		for (int i = 0; i < wireLength; i ++) {
			ret += intToString(wire[i]) + " ";
		}
		
		return ret;
				
				
	}
	
	private String intToString(int i) {
		switch(i) {
		case 0: return "u";
		case 1: return "r";
		case 2: return "d";
		case 3: return "l";
		default:return "#";
		}
	}
}
