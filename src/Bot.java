import java.util.Random;

public class Bot {
    
    private static String[] AvailableChoices = {"left","right","turn","down","space"};

    private int[] pieceX = new int[5];
    private int[] pieceY = new int[5];
    private int BLOCK_SIZE = Tetris.BLOCK_SIZE;

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

    public String TakeAChoice(int[][] MESH){

        Tetris.SetStateOfBotDecision(1);

        int[][] temp = MakeMockGrid(MESH);

        //Place current piece in the Grid
        for(int i = 0 ; i < pieceX.length; i++){
            temp[pieceX[i]][pieceY[i]] = 2;
        }

        //Check the grid vertically
        for(int i = 0; i < temp[0].length ; i++)
            {
                for(int j = 0 ; j < temp.length; j++)
                {
                    System.out.print(temp[j][i] + " ");
                }
                System.out.println();
            }

        Tetris.SetStateOfBotDecision(2);
        return "";
    }

    public void sendPieceCoords(Form form){
        pieceX[0] = (int) form.a.getX() / BLOCK_SIZE;
        pieceX[1] = (int) form.b.getX() / BLOCK_SIZE;
        pieceX[2] = (int) form.c.getX() / BLOCK_SIZE;
        pieceX[3] = (int) form.d.getX() / BLOCK_SIZE;
        pieceX[4] = (int) form.e.getX() / BLOCK_SIZE;

        pieceY[0] = (int) form.a.getY() / BLOCK_SIZE;
        pieceY[1] = (int) form.b.getY() / BLOCK_SIZE;
        pieceY[2] = (int) form.c.getY() / BLOCK_SIZE;
        pieceY[3] = (int) form.d.getY() / BLOCK_SIZE;
        pieceY[4] = (int) form.e.getY() / BLOCK_SIZE;
    }
    
    private void StartSimulation(int[][] Grid, int[][] piece){
        
    }
}
