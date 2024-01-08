import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Bot {
    
    //ACTIVATES DEBUG MODE
    private boolean DEBUG = false;

    private static String[] AvailableChoices = {"left","right","turn","down","space"};

    private int[] pieceX = new int[5];
    private int[] pieceY = new int[5];
    private int BLOCK_SIZE = Tetris.BLOCK_SIZE;

    private int YMAX = Tetris.YMAX / BLOCK_SIZE;
    private int XMAX = Tetris.XMAX / BLOCK_SIZE;

    private Pentomino currentPentomino;

    public Bot(){}

    /**
     * Random choice bot
     * @return
     */

    public String TakeAChoice(){
        Random random = new Random();
        
        int numberOfChoice = random.nextInt(AvailableChoices.length);
        String choice = AvailableChoices[numberOfChoice];

        return choice;
    }

    /**
     * return a new copied grid from the grid provided
     * @param grid
     * @return
     */

    private static int[][] MakeMockGrid(int[][] grid)
	{
		int[][] CopiedGrid = new int[grid.length][grid[0].length];

		for(int i = 0 ; i < grid.length; i++)
			for(int j = 0 ; j < grid[i].length; j ++)
				CopiedGrid[i][j] = grid[i][j];

		return CopiedGrid;
	}

        //ACtions : drop, turn, left, right

    private ArrayList<String> BestActions = new ArrayList<>();
    private ArrayList<String> currentActions = new ArrayList<>();
    private int turns = 0;
    private int BestNumberOfTurns = 0;
    private int bestMoveScore = 0;

    /**
     * Goes trough every move possible with the piece and assigns a score to the move,
     * after going trough all of them return a set of actions that need to be done for
     * the optimal placemenet of the pentomino
     * @param MESH
     * @param CurrentPentomino
     * @return Set of actions that needs to be executed for the optimal placemenet
     */

    public ArrayList<String> TakeAChoice(int[][] MESH, Pentomino CurrentPentomino){

        Tetris.SetStateOfBotDecision(1);

        BestActions.clear();
        currentActions.clear();
        turns = 0;
        BestNumberOfTurns = 0;
        bestMoveScore = 0;
        
        int[][] temp = MakeMockGrid(MESH);
        temp = flipMatrixClockwise(temp);
        temp = mirrorMatrix(temp);

        //4 available mutations for the piece
        for(int i = 0; i < 4; i ++){
            int[][] resetedGrid = MakeMockGrid(temp);

            int possibleLeft = getAvailableLeft(resetedGrid, currentPentomino.getPieceMatrix());
            int possibleRight = getAvailableRight(resetedGrid, currentPentomino.getPieceMatrix());

            for(int left = 0 ; left < possibleLeft; left++){
                int col = firstPieceCoords[0][0];
                int row = firstPieceCoords[0][1];

                int[][] _resetedGrid = MakeMockGrid(temp);

                StartSimulation(_resetedGrid, CurrentPentomino, row, col - left);
                currentActions.add("left");
            }

            currentActions.clear();
            
            for(int right = 1 ; right < possibleRight; right++){
                int col = firstPieceCoords[0][0];
                int row = firstPieceCoords[0][1];
                
                int[][] _resetedGrid = MakeMockGrid(temp);

                StartSimulation(_resetedGrid, CurrentPentomino, row, col + right);
                currentActions.add("right");
            }
            currentActions.clear();

            CurrentPentomino.changeForm();
            turns++;
        }

        for(int turn = 0 ; turn < BestNumberOfTurns; turn++){
            BestActions.add(0,"turn");
        }

        if(DEBUG){
            System.out.println("BEST MOVE NEEDS: ");
            for(String action : BestActions){
                System.out.print(action + " ");
            }
            System.out.println("and turns: " + BestNumberOfTurns);
            System.out.println("===================");
        }

        Tetris.SetStateOfBotDecision(2);
        return BestActions;
    }

        // Method to mirror the given matrix
        public static int[][] mirrorMatrix(int[][] matrix) {
            int rows = matrix.length;
            int columns = matrix[0].length;
    
            // Create a new matrix to store the mirrored result
            int[][] mirroredMatrix = new int[rows][columns];
    
            // Mirror the matrix
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    // Mirror horizontally by reversing the order of elements in each row
                    mirroredMatrix[i][j] = matrix[i][columns - 1 - j];
                }
            }
    
            return mirroredMatrix;
        }


    public static int[][] flipMatrixClockwise(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return matrix; // Empty matrix or empty rows
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] resultMatrix = new int[cols][rows];
        int currentRow = 0;
        int currentCol = 0;

        for(int col = 0 ; col < cols; col++){
            for(int row = rows - 1; row >= 0; row--){
                resultMatrix[currentRow][currentCol] = matrix[row][col];
                currentCol++;
            }
            currentRow++;
            currentCol = 0;
        }

        return resultMatrix;

    }

    private int getAvailableLeft(int[][] grid,int[][] piece){
        int availableLeftMoves = 0;

        int col = firstPieceCoords[0][0];
        int row = firstPieceCoords[0][1];


        while(col >= 0 && canPieceBePlaced(grid, piece, row, col)){
            col--;
            availableLeftMoves++;
        }

        return availableLeftMoves;
    }

    private int getAvailableRight(int[][] grid,int[][] piece){
        int availableRightMoves = 0;

        int col = firstPieceCoords[0][0];
        int row = firstPieceCoords[0][1];


        while(col < XMAX && canPieceBePlaced(grid, piece, row, col)){
            col++;
            availableRightMoves++;
        }

        return availableRightMoves;
    }

    private int[][] firstPieceCoords = new int[1][2];

    /**
     * Receives the current piece coords and stores them in order to place the pentomino on the bot's grid
     * @param form
     */

    public void sendPieceCoords(Pentomino form){
        currentPentomino = form;

        pieceX[0] = (int) form.a.getX() / BLOCK_SIZE;
        pieceX[1] = (int) form.b.getX() / BLOCK_SIZE;
        pieceX[2] = (int) form.c.getX() / BLOCK_SIZE;
        pieceX[3] = (int) form.d.getX() / BLOCK_SIZE;
        pieceX[4] = (int) form.e.getX() / BLOCK_SIZE;

        Arrays.sort(pieceX);
        firstPieceCoords[0][0] = pieceX[0];

        pieceY[0] = (int) form.a.getY() / BLOCK_SIZE;
        pieceY[1] = (int) form.b.getY() / BLOCK_SIZE;
        pieceY[2] = (int) form.c.getY() / BLOCK_SIZE;
        pieceY[3] = (int) form.d.getY() / BLOCK_SIZE;
        pieceY[4] = (int) form.e.getY() / BLOCK_SIZE;

        Arrays.sort(pieceY);
        firstPieceCoords[0][1] = pieceY[0];

    }

    /**
     * Start the simulation for the current mutation of the pentomino and current coords , and
     * assign a certain score to the move
     * @param grid
     * @param pento
     * @param row
     * @param col
     */

    private void StartSimulation(int[][] grid, Pentomino pento,int row,int col){
        
        int[][] piece = pento.getPieceMatrix();

        printCurrentMatrix("PIECE MATRIX: ", piece);

        placePieceAtCoords(grid,piece,row+1,col);

        printCurrentMatrix("RESETED GRID: ", grid);

        int initialCol = col;
        int initialRow = row;

       while(canPieceBePlaced(grid, piece, row, col)){
            row++;
       } 

       clearPieceAtCoords(grid,piece,initialRow,initialCol);
       placePieceAtCoords(grid,piece,row,col);
       
       int currentMoveScore = 0;

       int clousters = countEmptyZones(MakeMockGrid(grid));

       currentMoveScore -= clousters*10;
       currentMoveScore += getMoveScore(grid);

       printCurrentMatrix("SIMULATED GRID", grid);

       System.out.println("MOVE SCORE: " + currentMoveScore);

       if(currentMoveScore > bestMoveScore){
            bestMoveScore = currentMoveScore;
            BestActions.clear();
            for(String action : currentActions){
                BestActions.add(action);
            }

            BestNumberOfTurns = turns;

            if(DEBUG){
                System.out.println("===================");
                System.out.println("BEST MOVE STORED");
                System.out.println("BEST MOVES: ");
                for(String action : currentActions){
                    System.out.print(action + " ");
                }
                System.out.println();
                System.out.println("TURNS: " + BestNumberOfTurns);
                System.out.println("===================");
            }

       }

       
    }

    public static int countEmptyZones(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0; // Empty matrix
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int emptyZoneCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    emptyZoneCount++;
                    markAdjacentAsVisited(matrix, i, j, rows, cols);
                }
            }
        }

        return emptyZoneCount;
    }

    private static void markAdjacentAsVisited(int[][] matrix, int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || matrix[i][j] != 0) {
            return;
        }

        // Mark the current cell as visited
        matrix[i][j] = -1;

        // Recursively mark adjacent cells as visited
        markAdjacentAsVisited(matrix, i + 1, j, rows, cols);
        markAdjacentAsVisited(matrix, i - 1, j, rows, cols);
        markAdjacentAsVisited(matrix, i, j + 1, rows, cols);
        markAdjacentAsVisited(matrix, i, j - 1, rows, cols);
    }


    private int getMoveScore(int[][] grid){
        int score = 0;

        for (int row=0; row < grid.length; row++){
            for (int col=0; col < grid[0].length; col++){
                if (grid[row][col] == 2){
                    score += row;
                    if((col > XMAX / 2) || (col < XMAX/2)) score += 1;
                }
            }
        }
    
        return score;
    }

    private void clearPieceAtCoords(int[][] grid, int[][] piece,int row ,int col){
        printCurrentMatrix("PIECE MATRIX: ", piece);

        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[0].length; j++){
                if(piece[i][j] != 0)
                    grid[row + i][col + j] = 0;
            }
        }
    }

    private void placePieceAtCoords(int[][] grid, int[][] piece, int x, int y){
        if(DEBUG){
            System.out.println("ROW: " + x);
            System.out.println("COL: " + y);
        }


        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[0].length; j++){
                if(piece[i][j] != 0)
                    grid[x + i - 1][y + j] = 2;
            }
        }
    }

    private boolean canPieceBePlaced(int[][] grid, int[][] piece, int row, int col){

        for(int i = 0 ; i < piece.length;i++){
            for(int j = 0 ; j < piece[0].length;j++){

                if(row + i < 0 || col + j < 0) return false;

                if(row + i >= YMAX || col + j >= XMAX || (piece[i][j] != 0 && grid[row + i][col + j] == 1)){
                    return false;
                }
            }
        }

        return true;
    }
    

    //// DEBUG TOOLS
    private void printCurrentMatrix(String msg,int[][] matrixToPrint){

        if(DEBUG){
            System.out.println(msg);
            System.out.println("========================");

            for(int i = 0 ; i < matrixToPrint.length; i++){
                for(int j = 0 ; j < matrixToPrint[0].length; j ++){
                    System.out.print(matrixToPrint[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("========================");
        }
    }
        
}

