/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

 import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
    public static int horizontalGridSize = 5;
    public static int verticalGridSize = 8;
    
    public static final char[] input = { 'W', 'Y', 'I', 'T', 'Z', 'L','P','X'};
	//public static final char[] input = { 'P', 'X', 'F', 'V', 'W', 'Y', 'T', 'Z', 'U', 'N', 'L', 'I'};
	public static int[][] GLOBAL_grid = new int[horizontalGridSize][verticalGridSize];
    
    //Static UI class to display the board
    public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

	/**
	 * Helper function which starts a basic search algorithm
	 */


	private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	//private static int[][]

    private static int countClusters(int[][] field) {
		int count = 0;
		boolean[][] visited = new boolean[field.length][field[0].length];
	
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				if (field[i][j] == 1 && !visited[i][j]) {
					count++;
					dfs(field, visited, i, j);
				}
			}
		}
	
		return count;
	}
	

    private static void dfs(int[][] field, boolean[][] visited, int row, int col) {
        visited[row][col] = true;

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isValid(field, visited, newRow, newCol)) {
                dfs(field, visited, newRow, newCol);
            }
        }
    }

    private static boolean isValid(int[][] field, boolean[][] visited, int row, int col) {
        int numRows = field.length;
        int numCols = field[0].length;

        return row >= 0 && row < numRows && col >= 0 && col < numCols
                && field[row][col] == 1 && !visited[row][col];
    }

   
    
    
	 public static void search()
    {

        for(int i = 0; i < GLOBAL_grid.length; i++)
        {
            for(int j = 0; j < GLOBAL_grid[i].length; j++)
            {
                // -1 in the state matrix corresponds to empty square
                // Any positive number identifies the ID of the pentomino
				GLOBAL_grid[i][j] = -1;
            }
        }
        //Start the basic search
		if(CanGridBeFilled(GLOBAL_grid)){
			ImrpovedSearch(input);
		}else{
			System.out.println("No possible solution");
		}
    }
	
	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    private static int characterToID(char character) {
    	int pentID = -1; 
    	if (character == 'X') {
    		pentID = 0;
    	} else if (character == 'I') {
    		pentID = 1;
    	} else if (character == 'Z') {
    		pentID = 2;
    	} else if (character == 'T') {
    		pentID = 3;
    	} else if (character == 'U') {
    		pentID = 4;
     	} else if (character == 'V') {
     		pentID = 5;
     	} else if (character == 'W') {
     		pentID = 6;
     	} else if (character == 'Y') {
     		pentID = 7;
    	} else if (character == 'L') {
    		pentID = 8;
    	} else if (character == 'P') {
    		pentID = 9;
    	} else if (character == 'N') {
    		pentID = 10;
    	} else if (character == 'F') {
    		pentID = 11;
    	} 
    	return pentID;
    }

	private static ArrayList<Character> Array2ArrayList(char[] array)
	{
		ArrayList<Character> new_ArrayList = new ArrayList<Character>(array.length);
		for(char character : array)
			new_ArrayList.add(character);
		
		return new_ArrayList;
	}

	private static boolean CanGridBeFilled(int[][] field)
	{
		int total_field_squares = verticalGridSize*horizontalGridSize;
		int pentominos_squares = input.length*5;

		if(total_field_squares != pentominos_squares)
			return false;

		return true;
	}

	private static boolean canPieceBePlaced(int[][] pieceToPlace, int row, int col)
	{
		if(GLOBAL_grid[row][col] == -1 && pieceToPlace[0][0] == 0)
			return false;

		for(int i = 0; i < pieceToPlace.length; i++)
			for(int j = 0; j < pieceToPlace[i].length; j++){
				if(row + i >= horizontalGridSize || col + j >= verticalGridSize || (pieceToPlace[i][j] == 1 && GLOBAL_grid[row + i][col + j] != -1))
					return false;
				
			}

		return true;
	}

	private static void remove(int[][] pentomino, int row, int col) {
        for (int i = 0; i < pentomino.length; i++) {
            for (int j = 0; j < pentomino[0].length; j++) {
                if (pentomino[i][j] == 1) {
                    GLOBAL_grid[row + i][col + j] = -1;
                }
            }
        }
		ui.setState(GLOBAL_grid);
    }

	private static char[] ArrayList2Array(ArrayList<Character> List,char[] array)
	{
		char[] pentominos = new char[List.size()];

		for(int i=0; i < array.length; i++)
			pentominos[i] = List.get(i);

		return pentominos;
	}

	private static void ClearGrid()
	{
		for(int i=0; i < horizontalGridSize;i++)
			for(int j=0;j < verticalGridSize;j++)
				GLOBAL_grid[i][j] = -1;
	}

	private static void ImrpovedSearch(char[] pentominos)
	{
		ArrayList<Character> TemporaryInputs = Array2ArrayList(pentominos);

		for(int index=0; index < pentominos.length; index++)
			{
				int pentID = characterToID(TemporaryInputs.get(index));
				boolean piecePlaced = false;

				for(int mutation = 0 ; mutation < PentominoDatabase.data[pentID].length && !piecePlaced; mutation++)
				{
					int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

					for(int i = 0; i < GLOBAL_grid.length && !piecePlaced; i++)
						for(int j = 0; j < GLOBAL_grid[i].length && !piecePlaced; j++)
						{
							if(canPieceBePlaced(pieceToPlace,i,j)){
								addPiece(pieceToPlace, pentID, i, j);


								if(isGridFilled(GLOBAL_grid)){
									System.out.println("GRID FILLED");
									ui.setState(GLOBAL_grid); 
									return;
								}
								piecePlaced = true;
							}
						}
				}
				// if(piecePlaced){
				// 	System.out.println("PIECE REMOVED: " + TemporaryInputs.get(index));
				// 	TemporaryInputs.remove(TemporaryInputs.get(index));
				// }
			}
		if(!isGridFilled(GLOBAL_grid)){
			Collections.shuffle(TemporaryInputs);
			pentominos = ArrayList2Array(TemporaryInputs,pentominos);

			System.out.println("NEXT PENTOS: ");
			for(int k = 0 ; k < pentominos.length; k++)
				System.out.print(pentominos[k] + " ");
			ClearGrid();
			ImrpovedSearch(pentominos);
		}
	}
	
	/**
	 * Basic implementation of a search algorithm. It is not a bruto force algorithms (it does not check all the posssible combinations)
	 * but randomly takes possible combinations and positions to find a possible solution.
	 * The solution is not necessarily the most efficient one
	 * This algorithm can be very time-consuming
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
    private static void basicSearch(int[][] field){
    	Random random = new Random();
    	boolean solutionFound = false;
    	
    	while (!solutionFound) {
    		solutionFound = true;
    		
    		//Empty board again to find a solution
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					field[i][j] = -1;
				}
			}
    		
    		//Put all pentominoes with random rotation/flipping on a random position on the board
    		for (int i = 0; i < input.length; i++) {
    			
    			//Choose a pentomino and randomly rotate/flip it
    			int pentID = characterToID(input[i]);
    			int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
    			int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
    		
    			//Randomly generate a position to put the pentomino on the board
    			int x;
    			int y;
    			if (horizontalGridSize < pieceToPlace.length) {
    				//this particular rotation of the piece is too long for the field
    				x=-1;
    			} else if (horizontalGridSize == pieceToPlace.length) {
    				//this particular rotation of the piece fits perfectly into the width of the field
    				x = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				x = random.nextInt(horizontalGridSize-pieceToPlace.length+1);
    			}

    			if (verticalGridSize < pieceToPlace[0].length) {
    				//this particular rotation of the piece is too high for the field
    				y=-1;
    			} else if (verticalGridSize == pieceToPlace[0].length) {
    				//this particular rotation of the piece fits perfectly into the height of the field
    				y = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				y = random.nextInt(verticalGridSize-pieceToPlace[0].length+1);
    			}
    		
    			//If there is a possibility to place the piece on the field, do it
    			if (x >= 0 && y >= 0) {
	    			addPiece(pieceToPlace, pentID, x, y);
	    		} 
    		}
    		//Check whether complete field is filled
			solutionFound = isGridFilled(field);
    		

    		
    		if (solutionFound) {
    			//display the field
    			ui.setState(field); 
    			System.out.println("Solution found");
    			break;
    		}
    	}
    }

	private static boolean isGridFilled(int[][] field)
	{
		for(int i = 0 ; i < field.length; i++)
			for(int j = 0; j < field[i].length; j++)
				if(field[i][j] == -1)
					return false;
		return true;
	}

    
	/**
	 * Adds a pentomino to the position on the field (overriding current board at that position)
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 * @param piece a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x x position of the pentomino
	 * @param y y position of the pentomino
	 */
    public static void addPiece(int[][] piece, int pieceID, int x, int y)
    {
		Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
					GLOBAL_grid[x + i][y + j] = pieceID;
                }
            }
        }
		ui.setState(GLOBAL_grid);
		//System.out.println("Press ENTER to generate next combination");
		//scanner.nextLine();
    }

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
    public static void main(String[] args)
    {
		long startTime = System.currentTimeMillis();
        search();
		long endTime = System.currentTimeMillis();

		long millis = endTime - startTime;
		System.out.println("EXECUTION TIME: " + millis + "ms");
    }
}