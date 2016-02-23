import java.util.LinkedList;
import java.util.List;

public class Node{

		
	private char[][] board = new char[9][9];
	int depth;
	int playerScore;
    int opponentScore;
    int column;
    int bestMove;
    int score = 0;
	Node parent;
	List<Node> children = new LinkedList<Node>();
	
	
	public Node(){
		this.parent = null;
		this.board = initializeBoard();
		this.depth = 0;
		this.playerScore = 0;
		this.opponentScore = 0;
		
	}
	
	public Node(Node prev){
		this.board = deepCopy(prev.board);
		this.parent = prev;
		this.depth = prev.depth+1;
		this.column = getColumn();
		this.bestMove = prev.bestMove;
	}
	
	//Deep copy of the board
	public char[][] deepCopy(char[][] board){
		char[][] newBoard = new char[9][9];
		for(int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				newBoard[x][y] = board[x][y];
			}
		}
		return newBoard;
	}
	
	/*
	 *  addMove and addOppMove add the player's and AI's moves by
	 *  creating a new node and updating the attributes.
	 *  Not the best implementation and looks ugly but it works until
	 *  I have time to find a better solution. 
	 *  
	 */
	
	// Add the players move to the board
	public Node addMove(int column){
		Node newNode = new Node(this);
		
		//Make the move
		for(int x = 0; x < 9; x++){		
			if(x == 8){				
				
				//Update the new node's attributes
				newNode.board[x][column] = 'X';
				newNode.playerScore = newNode.addPlayerScore(newNode.getBoard());
				newNode.opponentScore = newNode.addOppScore(newNode.getBoard());
				return newNode;
				}
			
			if(newNode.board[x+1][column] == 'X' || newNode.board[x+1][column] == 'O' ){
				
				//Update the new node's attributes
				newNode.board[x][column] = 'X';
				newNode.playerScore = newNode.addPlayerScore(newNode.getBoard());
				newNode.opponentScore = newNode.addOppScore(newNode.getBoard());
				return newNode;	
			}
		}
		
		//Update the new node's attributes
		newNode.playerScore = newNode.addPlayerScore(newNode.getBoard());
		newNode.opponentScore = newNode.addOppScore(newNode.getBoard());
		return newNode;
	}
	
	// Add the A.I.'s move to the board
	public Node addOppMove(int column){
		Node newNode = new Node(this);
		
		//Make the move
		for(int x = 0; x < 9; x++){		
			if(x == 8){
				
				//Update the new node's attributes
				newNode.board[x][column] = 'O';
				newNode.playerScore = newNode.addPlayerScore(newNode.getBoard());
				newNode.opponentScore = newNode.addOppScore(newNode.getBoard());
				return newNode;
				}	
			
			if(newNode.board[x+1][column] == 'X' || newNode.board[x+1][column] == 'O' ){
				
				//Update the new node's attributes
				newNode.board[x][column] = 'O';
				newNode.playerScore = newNode.addPlayerScore(newNode.getBoard());
				newNode.opponentScore = newNode.addOppScore(newNode.getBoard());
				return newNode;	
			}
		}
		
		//Update the new node's attributes
		newNode.playerScore = newNode.addPlayerScore(newNode.getBoard());
		newNode.opponentScore = newNode.addOppScore(newNode.getBoard());
		return newNode;
	}
	
	// Get the next possible moves from
	// the current board state (Min)
	public List<Node> addMinSuccessors(){
		for(int y = 0; y < 9; y++){
			if(!ColumnIsFull(y)){
				children.add(addOppMove(y));
			}
		}
		return children;
	}
	
	// Get the next possible moves from
	// the current board state (Max)
	public List<Node> addMaxSuccessors(){
		for(int y = 0; y < 9; y++){
			if(!ColumnIsFull(y)){
				children.add(addMove(y));
				
			}
		}
		return children;
	}

	
	// Add up the total player score of the current board state
	public int addPlayerScore(char[][] board){
		for(int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				
				if(x != 8)
					if(board[x][y] == 'X' && board[x+1][y] == 'X'){
						score = score+2;
					}
				if(y != 8)
					if(board[x][y] == 'X' && board[x][y+1] == 'X'){
						score = score+2;
					}	
			}
		}
		
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				
				// Since we are going from left to right we don't check
				// [x-1][y-1] or else we would get duplicates
				if(y == 0){
					if(board[x][y] == 'X' && board[x+1][y+1] == 'X'){
						score = score + 1;
					}
				}
				else{
					if(board[x][y] == 'X' && board[x+1][y+1] == 'X'){
						score = score + 1;
					}
					if(board[x][y] == 'X' && board[x+1][y-1] == 'X'){
						score = score + 1;
					}
				}
			}
		}
		
		
		return score;
	}
	
	// Same as addPlayerScore but looks for O's
	public int addOppScore(char[][] board){
		int score = 0;
		for(int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				if(x != 8)
					if(board[x][y] == 'O' && board[x+1][y] == 'O'){
						score = score+2;
					}
				if(y != 8)
					if(board[x][y] == 'O' && board[x][y+1] == 'O'){
						score = score+2;
					}
				
			}
		}
		
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				
				// Since we are going from left to right we don't check
				// [x-1][y-1] or else we would get duplicates
				if(y == 0){
					if(board[x][y] == 'O' && board[x+1][y+1] == 'O'){
						score = score + 1;
					}
				}
				else{
					if(board[x][y] == 'O' && board[x+1][y+1] == 'O'){
						score = score + 1;
					}
					if(board[x][y] == 'O' && board[x+1][y-1] == 'O'){
						score = score + 1;
					}
				}
			}
		}
		return score;
	}
	
	// Initialize the board
	public  char[][] initializeBoard(){		
		for(int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				board[x][y] = '.';
			}
		}
		return board;
	}
	
	// Display the current board state
	public void displayBoard(){
	for(int x = 0; x < 9; x++){
		for(int y = 0; y < 9; y++){
			if(y == 0 && x != 0){
				System.out.println("");	
			}
			System.out.print(" " + board[x][y] + " ");
			}
		}
	}
	
	
	// Check if the column is full
	public boolean ColumnIsFull(int column){
			if(board[0][column] == 'X' || board[0][column] == 'O'){
				return true;
			}
			return false;
	}
		
	//Check if all of the columns are full
	public boolean allColumnsFull(){
		for(int i = 0; i < 9; i++)
			if(board[0][i] != 'X' && board[0][i] != 'O'){
				return false;
			}	
		return true;
	}
	
	//get the Column where the move was made
	public int getColumn(){
		for(int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){	
				if(board[x][y] == 'O' && parent.board[x][y] != 'O'){
					return y;
				}
			}
		}
		return 0;
	}
	
		
	//Get this node's current board
	public char[][] getBoard() {
		return board;
	}
	
	//Get this node's children
	public List<Node> getChildren(){
		return children;
	}

	
}
