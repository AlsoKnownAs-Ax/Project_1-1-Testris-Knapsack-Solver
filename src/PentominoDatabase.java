/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class takes care of reading all pentominoes and their permutations from a CSV
 * Information abpout the structure of the CSV file (also included in pentomino.csv.README)
 * Each line in the CSV file defines one permutation of a pentomino.
 *  - The first number is the ID for a pentomino, from 0 to 11.
 *  - The second number is the index of the permutation (rotation, flip, etc.), between 0 to 7.
 *  - The third and forth numbers are the X and Y sizes respectively.
 *  - The following X*Y numbers are a matrix showing which positions in the grid are occupied or empty (defined by the shape of the pentomino).
 * 
 * This file does not contain a header.
 * The pentominoes should be sorted by ID in increasing order
 * 
 * EXAMPLE:
 * 
 * 2,1,3,3,1,0,0,1,1,1,0,0,1
 * 
 * ID: 2
 * Permutation: 1
 * X: 3 squares
 * Y: 3 squares
 * Shape:
 * X 0 0
 * X X X
 * 0 0 X
 */
public class PentominoDatabase
{
    //Stores and loads the data on program initialization
    public static int[][][][] data = loadData("pentominos.csv");

    
    /** 
     * Loads and decodes the CSV file
     * @param fileName name of the CVS file to be used
     * @return list of pieces. Dimensions: 1-Piece ID; 2-Mutation; 3-X representation; 4-Y representation
     * @exception FileNotFoundException if the file is not found (probably, wrong location of the file and/or typo in the fileName parameter)
     */
    private static int[][][][] loadData(String fileName)
    {
        //Create a temporary dynamic object to store the data, later to be converted to a static 4D array
        ArrayList<ArrayList<int[][]>> dynamicList =  new ArrayList<>();

        //Open the CSV file
        File file = new File(fileName);

        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) // For each line in the CSV file
            {
                // Read the line, and convert the string to a list of numbers
                String[] values = scanner.nextLine().split(",");

                // If this piece has a new ID, increase the list
                if(Integer.valueOf(values[0]) > dynamicList.size() - 1)
                {
                    dynamicList.add(new ArrayList<>());
                }

                int xSize = Integer.valueOf(values[2]);
                int ySize = Integer.valueOf(values[3]);
                int[][] piece = new int[xSize][ySize];

                // Convert 1D list to 2D list
                for(int i = 0; i < xSize * ySize; i++)
                {
                    piece[i / ySize][i % ySize] = Integer.valueOf(values[4 + i]);
                }

                // Add piece to the dynamic list
                dynamicList.get(dynamicList.size() - 1).add(piece);
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        //Arrays index easier than ArrayLists, so convert dynamic list to static list
        int[][][][] staticList = new int[dynamicList.size()][][][];
        for(int i = 0; i < dynamicList.size(); i++)
        {
            staticList[i] = dynamicList.get(i).toArray(new int[0][0][0]);
        }
        return staticList;
    }

    /**
     * Main function used for visualizing and debugging reading the csv file with pieces
     * Should not be called while searching for a solution
     */
    public static void main(String[] args)
    {
        for(int i = 0; i < data.length; i++)
        {
            for(int j = 0; j<data[i].length; j++)
            {
                System.out.print(i + "," + j + "," + data[i][j].length + "," + data[i][j][0].length);

                for(int k = 0; k < data[i][j].length; k++)
                {
                    for(int l = 0; l < data[i][j][k].length; l++)
                    {
                        System.out.print("," + data[i][j][k][l]);
                    }
                }

                System.out.println();
            }
        }
    }
}
