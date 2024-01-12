package knapsack.Algorithms;

public class Matrix3D {
    public class ThreeDMatrixExample implements QuestionA{
        public static void main(String[] args) {
            // Define the dimensions of the 3D matrix
            int xSize = 3;  // Number of rows
            int ySize = 3;  // Number of columns
            int zSize = 3;  // Number of layers
    
            // Create a 3D matrix
            int[][][] matrix = new int[xSize][ySize][zSize];
    
            // Initialize the 3D matrix with some values
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    for (int z = 0; z < zSize; z++) {
                        matrix[x][y][z] = x * 100 + y * 10 + z;
                    }
                }
            }
    
            // Print the 3D matrix
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    for (int z = 0; z < zSize; z++) {
                        System.out.print(matrix[x][y][z] + " ");
                    }
                    System.out.println(); // Move to the next line after each row
                }
                System.out.println(); // Separate layers with an empty line
            }
        }
    }
    
}
