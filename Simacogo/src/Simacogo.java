import java.util.Scanner;

/*
 * Josh Clark
 * CSC 380 - Assignment 2 - Simacogo
 * 2/8/2016
 *  
 */

public class Simacogo {

	static int ply;	
	
	public static void main(String[] args) {
		
		// Initialize some things
		Node currentNode = new Node();
		Minimax minimax = new Minimax();
		Scanner input = new Scanner(System.in);
		boolean playersMove = true;
		
		// Choose a ply for the current game
		System.out.println("Ready to play Simacogo?");
		System.out.println("Choose a ply!");
		ply = input.nextInt();
		
		
		while(true){
			
			// If the game is over print the scores and check for the winner
			if(currentNode.allColumnsFull()){
				System.out.println("\nPlayer Score: " + currentNode.playerScore);
				System.out.println("Opponent Score: " + currentNode.opponentScore);
				if(currentNode.playerScore > currentNode.opponentScore){
					System.out.println("You Win!");
				}
				else
					System.out.println("You Lose! Maybe next time!");
					System.out.println("\nType 'r' to restart");
					if(input.next().equals('r')){
						currentNode.initializeBoard();
						continue;
					}
					else
						break;
			}
			
			// Player's move
			if(playersMove){
				System.out.println("\nPlayer Score: " + currentNode.playerScore);
				System.out.println("Opponent Score: " + currentNode.opponentScore);
				System.out.println("\nIt's your move!");
				System.out.println("Choose a number between 1 and 9,");
				System.out.println("or type 'quit' to exit the game!\n");
				
			}
			
			// A.I. move
			else{
				
				// If the game is over print the scores and check for the winner
				System.out.println();
				if(currentNode.allColumnsFull()){
					System.out.println("\nPlayer Score: " + currentNode.playerScore);
					System.out.println("Opponent Score: " + currentNode.opponentScore);
					if(currentNode.playerScore > currentNode.opponentScore){
						System.out.println("You Win!");
					}
					else
						System.out.println("You Lose! Maybe next time!");
						System.out.println("\nType 'r' to restart");
						if(input.next().equals('r')){
							currentNode.initializeBoard();
							continue;
						}
						else
							break;
				}
				
				System.out.println("\nOpponent is Thinking....\n");		
				// Add the opponent's move
				currentNode = currentNode.addOppMove(minimax.minimaxSolve(currentNode, ply));
				
				currentNode.displayBoard();
				System.out.println();
				currentNode.opponentScore = currentNode.addOppScore(currentNode.getBoard());
				playersMove = !playersMove;
				continue;
			}
			
			currentNode.displayBoard();
			System.out.println();
			
			String userInput = input.next();
			
			if(userInput.equals("quit")){
				break;
			}
			int column = Integer.parseInt(userInput);
			if(column < 1 || column > 9){
				continue;
			}
			
			// Check if the column is full. If it is try again.
			if(currentNode.ColumnIsFull(column-1)){
				System.out.println("This column is full! Pick a different column");
				continue;
			}
			
			else
				
				// User adds his/her move
				currentNode = currentNode.addMove(column-1);
				currentNode.displayBoard();
				playersMove = !playersMove;
				continue;
			
			
		}
		
		System.out.println("Goodbye!");
		input.close();
	}

}
