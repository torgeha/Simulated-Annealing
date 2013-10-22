import java.util.ArrayList;


public class Search {
	
	//variable just to track the objective functions evaluation
	public static double maxEvaluatedEver = 0;
	
	public static Node SimulatedAnnealing(Node initialNode, Schedule schedule) {
		
		System.out.println("init " + "\n" + initialNode);
		Node currentNode = initialNode;
		double temperature = 0;
		
		do {
			ArrayList<Node> neighbours = new ArrayList<Node>();
			//get the temperature used to determine the greediness of the algorithm
			temperature = schedule.scheduleTemp();			
			
			double evCurrent = currentNode.evaluate();
			
			//If the objective function return 1.0, an optimal solution has been found
			if (evCurrent >= 1) {
				return currentNode;
			}
			
			//get the neighbours to currentnode
			neighbours = currentNode.generateNeighbours();
			
			double maxEv = 0;
			Node nextNode = null;
			
			//evaluates all neighbours and sets nextNode to the neighbour with highest evaluation
			for (int i = 0; i < neighbours.size(); i++) {
				double temp = neighbours.get(i).evaluate();
				if (temp > maxEv) {
					maxEv = temp;
					nextNode = neighbours.get(i);
				}
			}
			
			//some numbers used to determine the probability of making a greedy choice or to explore
			double q = ((maxEv - evCurrent) / evCurrent);
			double p = Math.min(1.0, Math.exp((-q) / temperature));
			double x = Math.random();
			
			
			//just to keep track of what the highest evaluation is if the algorithm does not find an optimal solution
			if (maxEvaluatedEver < maxEv) {
				maxEvaluatedEver = maxEv;
			}
			if (maxEvaluatedEver == 1) {
				System.out.println(currentNode);
			}
			
			//make a greedy choice
			if (x > p) {
				
				currentNode = nextNode;
			}
			//choose to explore
			else {
				currentNode = currentNode.getRandomNeighbour(neighbours);
			}
		} while (temperature > 0);
		
		//If no node has gotten an evaluation of 1.0 (i.e optimal solution), a non-optimal solution is returned
		return currentNode;
	}

}
