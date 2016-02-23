import java.util.Comparator;

//Comparator for the bestMove
public class ScoreComparator implements Comparator<Node>{
	
	@Override
	public int compare(Node node, Node node2) {
		if(node.bestMove < node2.bestMove){
			return 1;
		}
		else if(node.bestMove > node2.bestMove){
			return -1;
		}
		return 0;
	}
}

