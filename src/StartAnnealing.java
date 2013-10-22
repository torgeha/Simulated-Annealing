import java.awt.Point;


public class StartAnnealing {
	public static void main(String[] args) {
		
		//variables used for the algorithm
		//temperature: rate of exploration/greediness
		int temperature = 1000;
		//size of the quadratic board
		int size = 10;
		//number of allowed eggs in row/columns/diagonals
		//for k=1: N-Queen-problem
		int k = 3;
		
		//set up the serach parameters
		Schedule s = new Schedule(temperature);
		Board board = new Board(size, k);
		EggCartonNode node = new EggCartonNode(board);
		//sets the initial node
		node.makeThisTheInititalNode();
		
		EggCartonNode result = (EggCartonNode)Search.SimulatedAnnealing(node, s);
		System.out.println("Result: " + "\n" + result);
		System.out.println("MaxEvaluatedEver: " + Search.maxEvaluatedEver);
		
		
		
	}

}
