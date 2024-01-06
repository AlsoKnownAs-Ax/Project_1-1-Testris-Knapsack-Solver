package PentominosUtils;


import java.util.ArrayList;
import java.util.Collections;

/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
	public static int verticalGridSize = 5;
	public static int horizontalGridSize = 6;

	//5x6 
	public static final char[] input = { 'T', 'W', 'Z', 'L', 'I', 'Y'};
	// 6x10 
	//public static final char[] input = { 'I', 'P', 'T', 'W', 'X', 'Y','L','U','N','Z','F','V'};
	//public static final char[] input = { 'W', 'T', 'Z', 'L', 'I', 'Y','X','F','P','U','F'};
    //public static final char[] input = { 'W', 'Y', 'I', 'T', 'Z', 'L','P','X','U','F'};
	//public static final char[] input = { 'P', 'X', 'F', 'V', 'W', 'Y', 'T', 'Z', 'U', 'N', 'L', 'I'};
	public static int[][] GLOBAL_grid = new int[horizontalGridSize][verticalGridSize];
    
    //Static UI class to display the board
    public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);
 
    
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
			ImprovedSearch(input);
		}else{
			System.out.println("No possible solution");
		}
    }
	
	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    public static int characterToID(char character) {
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

	/**
	 * Transfrom an Array into an ArrayList
	 * @param array
	 * @return ArrayList
	 */

	private static ArrayList<Character> Array2ArrayList(char[] array)
	{
		ArrayList<Character> new_ArrayList = new ArrayList<Character>(array.length);
		for(char character : array)
			new_ArrayList.add(character);
		
		return new_ArrayList;
	}

	/**
	 * Method to check if there are enough pentomino squares to fit the grid squares
	 * @param field	--> The grid
	 * @return	true/false
	 */
	private static boolean CanGridBeFilled(int[][] field)
	{
		int total_field_squares = verticalGridSize*horizontalGridSize;
		int pentominos_squares = input.length*5;

		if(total_field_squares % 5 != 0)
			return false;

		if(total_field_squares != pentominos_squares && pentominos_squares < total_field_squares)
			return false;

		return true;
	}

	/**
	 * Method to check if the piece can fit into the grid ar certain cords
	 * @param pieceToPlace	matrix of the Piece
	 * @param row	the row we start the check from
	 * @param col	the collum we start the check from
	 * @return	true/false if the piece fits or not
	 */

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

	private static char[] ArrayList2Array(ArrayList<Character> List,char[] array)
	{
		char[] pentominos = new char[List.size()];

		for(int i=0; i < array.length; i++)
			pentominos[i] = List.get(i);

		return pentominos;
	}
	
	/**
	 * Method to clear the Global Grid
	 */

	private static void ClearGrid()
	{
		for(int i=0; i < horizontalGridSize;i++)
			for(int j=0;j < verticalGridSize;j++)
				GLOBAL_grid[i][j] = -1;
	}

	/**
	 * Method to check the empty areas in the grid and the empty squares from every area. If there area areas with less
	 * than 5 empty squares the area can not be filled with a pentomino.
	 * @param grid	Global grid to check
	 * @return	true/false if the Algorithm can/can't continue
	 */

    public static boolean CanAlgorithmContinue(int[][] grid) {

		int[][] mock_grid = MakeMockGrid(grid);

        for (int i = 0; i < mock_grid.length; i++) {
            for (int j = 0; j < mock_grid[0].length; j++) {
                if (mock_grid[i][j] == -1) {
                    // Start DFS from an empty cell and count empty spaces
                    int emptySpacesCount = dfs(mock_grid, i, j);

					if(emptySpacesCount % 5 != 0){
						return false;
					}
                }
            }
        }

        return true;
    }

	/**
	 * Make a mock grid from one provided.
	 * @param grid	--> Grid to duplicate
	 * @return	--> Mock grid
	 */

	private static int[][] MakeMockGrid(int[][] grid)
	{
		int[][] CopiedGrid = new int[grid.length][grid[0].length];

		for(int i = 0 ; i < grid.length; i++)
			for(int j = 0 ; j < grid[i].length; j ++)
				CopiedGrid[i][j] = grid[i][j];

		return CopiedGrid;
	}

	/**
	 * DFS to check the empty areas and count the empty squares.
	 * @param grid	--> grid to check
	 * @param i	--> x cord to start the check
	 * @param j	--> y coord to start the check
	 * @return	--> The empty squares
	 */

    private static int dfs(int[][] grid, int i, int j) {
		int[][] mock_grid = grid;

        if (i >= 0 && i < mock_grid.length && j >= 0 && j < mock_grid[0].length && mock_grid[i][j] == -1) {
            mock_grid[i][j] = 0;  // Mark the cell as visited
            int count = 1;
            // Explore adjacent cells
            count += dfs(mock_grid, i + 1, j);
            count += dfs(mock_grid, i - 1, j);
            count += dfs(mock_grid, i, j + 1);
            count += dfs(mock_grid, i, j - 1);
            return count;
        }
        return 0;
    }

	/**
	 * Recursive Algorithm for fitting the pentominoes into the grid.
	 * @param pentominos
	 */


	private static void ImprovedSearch(char[] pentominos)
	{

		ArrayList<Character> TemporaryInputs = Array2ArrayList(pentominos);


		boolean solutionFound = false;
	
		while(!solutionFound){
			//Iterate over every pentomino
			for(int index=0; index < pentominos.length; index++)
			{
				int pentID = characterToID(TemporaryInputs.get(index));
				boolean piecePlaced = false;

				//Iterate over grid squares
				for(int i = 0; i < GLOBAL_grid.length && !piecePlaced; i++)
					for(int j = 0; j < GLOBAL_grid[i].length && !piecePlaced; j++)
					{
						//Get every mutation of the piece
						for(int mutation = 0 ; mutation < PentominoDatabase.data[pentID].length && !piecePlaced; mutation++)
						{
							int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

							//Can piece fit ?
							if(canPieceBePlaced(pieceToPlace,i,j)){
								addPiece(pieceToPlace, pentID, i, j);

								if(isGridFilled(GLOBAL_grid)){
									System.out.println("GRID FILLED");
									ui.setState(GLOBAL_grid);
									System.out.println("TemporaryInputs : " + TemporaryInputs);
									solutionFound = true; 
									return;
								}

								int[][] mock_grid = MakeMockGrid(GLOBAL_grid);

								//If the piece leaves no Dead Areas then exit from the loops
								if(CanAlgorithmContinue(mock_grid))
								{									
									piecePlaced = true;
								}
							}
						}
					}
			}

			if(!isGridFilled(GLOBAL_grid)){
				Collections.shuffle(TemporaryInputs);
				pentominos = ArrayList2Array(TemporaryInputs,pentominos);
			//	Solution_Array.clear();
				ClearGrid();
			}
		}
	}

	/**
	 * Method to check if the grid is fully filled.
	 * @param field	--> Grid to check
	 * @return	--> true/false
	 */

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
		int[][] mock_grid = MakeMockGrid(GLOBAL_grid);

		//If there is an empty area with empty squares that are not divisible by 5 then eliminate the pentomino
		if(!CanAlgorithmContinue(mock_grid))
		{
			for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
			{
				for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
				{
					if (piece[i][j] == 1)
					{
						//Eliminate the pentomino from the grid
						GLOBAL_grid[x + i][y + j] = -1;
					}
				}
			}
		}

		ui.setState(GLOBAL_grid);


		// Scanner scanner = new Scanner(System.in);
		// System.out.println("Press ENTER to generate next combination");
		// scanner.nextLine();
    }

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */

	 public static void main(String[] args) {
		search();
	 }


}