import java.util.ArrayList;
import java.util.Random;

//An abstract general class, should be extended by a specialized node-class to use with this search.
public abstract class Node {
	
	protected Board board;
	
	public Node(Board board) {
		this.board = board;
	}
	
	public abstract ArrayList<Node> generateNeighbours();
	public abstract double evaluate();
	public abstract void makeThisTheInititalNode();
	
	public static Node getRandomNeighbour(ArrayList<Node> randomList) {
		Random r = new Random();
		return randomList.get(r.nextInt(randomList.size()));
	}
	
	
	
	
}
