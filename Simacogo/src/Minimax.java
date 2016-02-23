import java.util.Collections;

public class Minimax {
	
	public Minimax(){
	}
	
	// This is a function that returns the column rather than the expected value from the node
	// minimax is called inside of here
	public int minimaxSolve(Node currentNode, int ply){
		Node bestNode = currentNode;
		minimax(currentNode, ply, false);
		Collections.sort(currentNode.children, new ScoreComparator());
		bestNode = currentNode.children.get(0);
		return bestNode.getColumn();
	
	}
	
	// Here is where most of the work for the A.I. is done
	public int minimax(Node node, int depth, boolean max){
		
		// If we are at a leaf node we evaluate the the node and
		// return that value
		if(depth == 0){
			node.bestMove = evaluate(node);
		    return node.bestMove;
		}
		
		// If it is max's turn initialize Min Value and recursively
		// call minimax on each child. Updating the depth and boolean value
		// save the max value
		if(max){
			node.addMaxSuccessors();
			int bestMove = Integer.MIN_VALUE;
			for(Node n : node.children){
				n.bestMove = max(bestMove, minimax(n, depth - 1, false));
				node.bestMove = node.bestMove + n.bestMove;
			}
			return node.bestMove;
		}
		
		// If it is min's turn initialize Max Value and recursively
		// call minimax on each child. Updating the depth and boolean value
		// save the min value
		else{
			node.addMinSuccessors();
			int bestMove = Integer.MAX_VALUE;
			for(Node n : node.children){
				n.bestMove = min(bestMove, minimax(n, depth - 1, true));
				node.bestMove = node.bestMove + n.bestMove;
			}
			return node.bestMove;
		}
	}
	
	
	// Get the minimum
	private int min(int bestMove, int nextMove){
		return Math.min(bestMove, nextMove);
	}
	
	// Get the maximum
	private int max(int bestMove, int nextMove){
		return Math.max(bestMove, nextMove);
	}
	
	// Evaluate the current board position
	private int evaluate(Node n){
		return n.opponentScore - n.playerScore;
	}
	
}
