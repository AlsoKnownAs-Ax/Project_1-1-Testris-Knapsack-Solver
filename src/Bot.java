import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Bot {
    
    private static String[] AvailableChoices = {"left","right","turn","down","space"};

    private int[] pieceX = new int[5];
    private int[] pieceY = new int[5];
    private int BLOCK_SIZE = Tetris.BLOCK_SIZE;

    private int YMAX = Tetris.YMAX / BLOCK_SIZE;
    private int XMAX = Tetris.XMAX / BLOCK_SIZE;

    private Pentomino currentPentomino;

    public Bot(){}

    public String TakeAChoice(){
        Random random = new Random();
        
        int numberOfChoice = random.nextInt(AvailableChoices.length);
        String choice = AvailableChoices[numberOfChoice];

        return choice;
    }

    private static int[][] MakeMockGrid(int[][] grid)
	{
		int[][] CopiedGrid = new int[grid.length][grid[0].length];

		for(int i = 0 ; i < grid.length; i++)
			for(int j = 0 ; j < grid[i].length; j ++)
				CopiedGrid[i][j] = grid[i][j];

		return CopiedGrid;
	}

    public String TakeAChoice(int[][] MESH, Pentomino CurrentPentomino){

        Tetris.SetStateOfBotDecision(1);

        int[][] temp = MakeMockGrid(MESH);

        //Place current piece in the Grid
        // for(int i = 0 ; i < pieceX.length; i++){
        //     temp[pieceX[i]][pieceY[i]] = 2;
        // }

        //4 available mutations for the piece
        for(int i = 0; i < 4; i ++){
            int[][] resetedGrid = MakeMockGrid(temp);

            //We have to flip the matrix to access it easier
            resetedGrid = flipMatrixHorizontal(resetedGrid);

            int possibleLeft = getAvailableLeft(resetedGrid, currentPentomino.getPieceMatrix());
            int possibleRight = getAvailableRight(resetedGrid, currentPentomino.getPieceMatrix());

            for(int left = 0 ; left < possibleLeft; left++){
                int col = firstPieceCoords[0][0];
                int row = firstPieceCoords[0][1];
                System.out.println("MOVED LEFT");

                int[][] _resetedGrid = MakeMockGrid(temp);

                //We have to flip the matrix to access it easier
                _resetedGrid = flipMatrixHorizontal(_resetedGrid);

                StartSimulation(_resetedGrid, CurrentPentomino, row, col - left);
                currentActions.add("left");
            }

            currentActions.clear();
            
            for(int right = 1 ; right < possibleRight; right++){
                int col = firstPieceCoords[0][0];
                int row = firstPieceCoords[0][1];
                
                System.out.println("MOVED RIGHT");

                int[][] _resetedGrid = MakeMockGrid(temp);

                //We have to flip the matrix to access it easier
                _resetedGrid = flipMatrixHorizontal(_resetedGrid);

                StartSimulation(_resetedGrid, CurrentPentomino, row, col + right);
                currentActions.add("right");
            }

            CurrentPentomino.changeForm();
            turns++;
        }

        System.out.println("BEST MOVE NEEDS: ");
        for(String action : BestActions){
            System.out.print(action + " ");
        }
        System.out.println("and tunrs: " + BestNumberOfTurns);
        System.out.println("===================");
        
        Tetris.SetStateOfBotDecision(2);
        return "";
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

    //ACtions : drop, turn, left, right

    private ArrayList<String> BestActions = new ArrayList<>();
    private ArrayList<String> currentActions = new ArrayList<>();
    private int turns = 0;
    private int BestNumberOfTurns = 0;
    private int bestMoveScore = 0;

    private void StartSimulation(int[][] grid, Pentomino pento,int row,int col){
        
        int[][] piece = pento.getPieceMatrix();

        placePieceAtCoords(grid,piece,row+1,col);

        printCurrentMatrix("RESETED GRID: ", grid);

        int initialCol = col;
        int initialRow = row;

       while(canPieceBePlaced(grid, piece, row, col)){
            row++;
       } 

       clearPieceAtCoords(grid,piece,initialRow,initialCol);
       placePieceAtCoords(grid,piece,row,col);
       
       printCurrentMatrix("SIMULATED GRID: ",grid);

       int currentMoveScore = 0;

       int clousters = dfs(grid, 0, XMAX/2);

       currentMoveScore -= clousters*3;
       currentMoveScore += getMoveScore(grid);

       System.out.println("MOVE SCORE: " + currentMoveScore);

       if(currentMoveScore > bestMoveScore){
            bestMoveScore = currentMoveScore;
            //TODO: ADD ACTIONS TO ARRAY
            currentActions.add("drop");
            BestActions = (ArrayList<String>) currentActions.clone();
            BestNumberOfTurns = turns;
            System.out.println("===================");
            System.out.println("BEST MOVE STORED");
            System.out.println("===================");
       }

       
    }

    private int dfs(int[][] grid, int i, int j) {
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

    private int getMoveScore(int[][] grid){
        int score = 0;

        for (int row=0; row < grid.length; row++){
            for (int col=0; col < grid[0].length; col++){
                if (grid[row][col] == 2){
                    score += row;
                }
            }
        }
    
        score = score/5;

        return score;
    }

    private int[][] flipMatrixHorizontal(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] resultMatrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][cols - 1 - j] = matrix[i][j];
            }
        }

        resultMatrix = flipMatrixCounterclockwise(resultMatrix);
        resultMatrix = fixUpsideDown(resultMatrix);

        return resultMatrix;
    }

    private static int[][] fixUpsideDown(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] resultMatrix = new int[rows][cols];

        // Reverse the order of rows
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][j] = matrix[rows - 1 - i][j];
            }
        }

        return resultMatrix;
    }

    private static int[][] flipMatrixCounterclockwise(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] resultMatrix = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[j][i] = matrix[i][j];
            }
        }

        // Reverse the order of rows
        for (int i = 0; i < rows / 2; i++) {
            int[] temp = resultMatrix[i];
            resultMatrix[i] = resultMatrix[rows - 1 - i];
            resultMatrix[rows - 1 - i] = temp;
        }

        return resultMatrix;
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
        System.out.println("ROW: " + x);
        System.out.println("COL: " + y);

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
                if(row + i >= YMAX || col + j >= XMAX || (piece[i][j] != 0 && grid[row + i][col + j] == 1)){
                    return false;
                }
            }
        }

        return true;
    }
    

    //// DEBUG TOOLS

    private void printCurrentMatrix(String msg,int[][] matrixToPrint){
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

