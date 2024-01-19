package knapsack.Algorithms;

import java.util.List;

import knapsack.Main;
import knapsack.Database.ParcelDatabase;
import knapsack.UI.CargoRender;
import knapsack.UI.CargoRender.Center;

// import gui.ArrayVisualization;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Platform;

public class DancingLinks {

    boolean[][] coverMatrix;
    List<Integer> rowTypes;
    DancingNode root;

    int score = 0;
    int bestScore = 0;
    Object[] bestSolution;

    public DancingLinks(boolean[][] coverMatrix, List<Integer> rowTypes) {
        this.coverMatrix = coverMatrix;
        this.rowTypes = rowTypes;
        this.root = createDLXList(coverMatrix);
    }

    /**
     * start the creation of the DLX structure
     * @param grid DLX 2D grid
     * @return header node
     */

    private DancingColumn createDLXList(boolean[][] grid) {
        final int nbColumns = grid[0].length;
        DancingColumn headerNode = new DancingColumn();
        List<DancingColumn> DancingColumns = new ArrayList<>();

        for (int i = 0; i < nbColumns; i++) {
            DancingColumn n = new DancingColumn();
            DancingColumns.add(n);
            headerNode = (DancingColumn) headerNode.linkRight(n);
        }

        headerNode = headerNode.r.header;

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

                    col.u.linkDown(newNode);
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

        if (root.r == root) {
            //System.out.println("Exact cover found");
        }

        int solutionScore = getScore(branchSolution);

        if (solutionScore > bestScore) {
            bestSolution = branchSolution.toArray();
            bestScore = solutionScore;
            reconvert();
        }

        smallestColumn.unlink();

        for (DancingNode row = smallestColumn.d; row != smallestColumn; row = row.d) {

            branchSolution.add(row.inputRow);

            for (DancingNode column = row.r; column != row; column = column.r) {
                column.header.unlink();
            }

            solve(branchSolution);

            branchSolution.remove(branchSolution.size() - 1);

            smallestColumn = row.header;

            for (DancingNode j = row.l; j != row; j = j.l) {
                j.header.link();
            }
        }

        smallestColumn.link();
    }

 
    /**
     * get smallest column
     * @return smallest column
     */

    public DancingColumn getSmallestColumn() {

        DancingColumn smallestColumn = (DancingColumn) root.r;

        for (DancingColumn col = (DancingColumn) root.r; col != root; col = (DancingColumn) col.r) {
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
            score += ParcelDatabase.getValue(rowTypes.get(rowNumber));
        }
        return score;
    }

    /**
     * convert the 1D array to 3D and display it
     */
    private Boolean cameraUpdated = false;
    private int[][][] matrix;

    public void reconvert() {

        List<boolean[]> inputRows = new ArrayList<>();
        List<Integer> inputTypes = new ArrayList<>();

        for (Object row : bestSolution) {
            int rowNumber = (Integer) row;

            boolean[] inputRow = coverMatrix[rowNumber];
            int inputType = rowTypes.get(rowNumber);

            inputRows.add(inputRow);
            inputTypes.add(inputType);
        }

      
        //TODO: De schimbat

        int width = 5;
        int height = 8;
        int depth = 33;

        int[][][] finalField = new int[depth][height][width];

        int shapeNumber = 0;
        for (boolean[] shape : inputRows) {
            boolean[][][] booleanShapeOutput = ArrConverter.OneDto3D(shape, width, height, depth);
           
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
                CargoRender cargoRender = new CargoRender(-250, 15, 10, 400);
                
                cargoRender.RenderCargo(Main.cargoGroup, finalField);
            }
        });
    }

}