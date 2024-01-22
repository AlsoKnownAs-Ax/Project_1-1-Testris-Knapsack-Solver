package knapsack.DLX;

import java.util.List;

import knapsack.Main;
import knapsack.Database.ParcelDatabase;
import knapsack.UI.CargoRender;
import java.util.ArrayList;

import javafx.application.Platform;

public class DancingLinks {

    private boolean useValues = false;

    private boolean[][] coverMatrix;
    private List<Integer> rowTypes;
    private DancingNode root;

    int score = 0;
    private int bestScore = 0;
    private Object[] bestSolution;

    private ArrayUtils arrayUtils = new ArrayUtils();

    public DancingLinks(boolean[][] coverMatrix, List<Integer> rowTypes) {
        this.coverMatrix = coverMatrix;
        this.rowTypes = rowTypes;
        this.root = initDLXList(coverMatrix);
    }

    /**
     * Turns the useValue Mode ON/OFF
     * @param toggle
     */

    public void toggleValues(boolean toggle){
        useValues = toggle;
        System.out.println("Using values: " + useValues);
    }

    /**
     * Initializes the DLX List
     * @param grid DLX 2D grid
     * @return header node
     */

    private DancingColumn initDLXList(boolean[][] grid) {
        final int nbColumns = grid[0].length;
        DancingColumn headerNode = new DancingColumn();
        List<DancingColumn> DancingColumns = new ArrayList<>();

        for (int i = 0; i < nbColumns; i++) {
            DancingColumn n = new DancingColumn();
            DancingColumns.add(n);
            headerNode = (DancingColumn) headerNode.linkRight(n);
        }

        headerNode = headerNode.right.header;

        for (int i = 0; i < grid.length; i++) {
            boolean[] row = grid[i];
            DancingNode prev = null;

            for (int j = 0; j < nbColumns; j++) {
                if (row[j] == true) {
                    DancingColumn col = DancingColumns.get(j);

                    DancingNode newNode = new DancingNode(i);
                    newNode.header = col;
                    if (prev == null)
                        prev = newNode;

                    col.upper.linkDown(newNode);
                    prev = prev.linkRight(newNode);
                    col.size++;
                }
            }
        }

        headerNode.size = nbColumns;

        return headerNode;
    }

    /**
     * start the solution search
     */

    public void startSolve() {
        solve(new ArrayList<Integer>());
    }

    /**
     * solve the exact / partial cover problem
     * @param branchSolution ArrayList containing the branch solutions
     */

    public void solve(ArrayList<Integer> branchSolution) {

        DancingColumn smallestColumn = getSmallestColumn();

        int solutionScore = getScore(branchSolution);

        if (solutionScore > bestScore) {
            System.out.println("new solution found");
            System.out.println("new score: " + solutionScore);
            bestSolution = branchSolution.toArray();
            bestScore = solutionScore;
            redraw();
        }

        smallestColumn.unlink();

        for (DancingNode row = smallestColumn.down; row != smallestColumn; row = row.down) {

            branchSolution.add(row.inputRow);

            for (DancingNode column = row.right; column != row; column = column.right) {
                column.header.unlink();
            }

            solve(branchSolution);

            branchSolution.remove(branchSolution.size() - 1);

            smallestColumn = row.header;

            for (DancingNode j = row.left; j != row; j = j.left) {
                j.header.link();
            }
        }

        smallestColumn.link();
    }

    public void redraw() {

        List<boolean[]> inputRows = new ArrayList<>();
        List<Integer> inputTypes = new ArrayList<>();

        for (Object row : bestSolution) {
            int rowNumber = (Integer) row;

            boolean[] inputRow = coverMatrix[rowNumber];
            int inputType = rowTypes.get(rowNumber);

            inputRows.add(inputRow);
            inputTypes.add(inputType);
        }

        //We are using the values *2 so we can work with integers
        int width = 5;
        int height = 8;
        int depth = 33;

        int[][][] finalField = new int[depth][height][width];

        int shapeNumber = 0;
        for (boolean[] shape : inputRows) {
            boolean[][][] booleanShapeOutput = arrayUtils.ConvertTo3D(shape, width, height, depth);
           
            int type = inputTypes.get(shapeNumber);
            for (int z = 0; z < depth; z++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (booleanShapeOutput[z][y][x]) {
                            finalField[z][y][x] = type;
                        }
                    }
                }
            }
            shapeNumber++;
        }

        score = bestScore;
        
        Platform.runLater(new Runnable() {            
            @Override
            public void run() {
                CargoRender cargoRender = new CargoRender(-70, 0, 5, 40);
                
                cargoRender.RenderCargo(Main.cargoGroup, finalField);
                Main.score_label.setText("Score: "+ bestScore);
            }
        });
    }
 
    /**
     * get smallest column
     * @return smallest column
     */

    public DancingColumn getSmallestColumn() {

        DancingColumn smallestColumn = (DancingColumn) root.right;

        for (DancingColumn col = (DancingColumn) root.right; col != root; col = (DancingColumn) col.right) {
            if (col.size < smallestColumn.size) {
                smallestColumn = col;
            }
        }

        return smallestColumn;
    }

    /**
     * get current solution score
     * @return score
     */

    public int getScore(ArrayList<Integer> solution) {

        int score = 0;

        for (int rowNumber : solution) {

            if(!useValues){
                score += 1;
            }else{
                score += ParcelDatabase.getValue(rowTypes.get(rowNumber));
            }

        }

        return score;
    }

}