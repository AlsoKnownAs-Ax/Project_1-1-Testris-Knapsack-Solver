public class MatrixClusterCounter {

    public static int countClusters(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;

        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0 && !visited[i][j]) {
                    // Found the start of a new cluster
                    count++;
                    dfs(matrix, visited, i, j);
                }
            }
        }

        return count;
    }

    private static void dfs(int[][] matrix, boolean[][] visited, int row, int col) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Check boundaries and if the cell is not visited
        if (row < 0 || row >= rows || col < 0 || col >= cols || visited[row][col] || matrix[row][col] == 0) {
            return;
        }

        // Mark the cell as visited
        visited[row][col] = true;

        // Explore neighbors in 8 directions (up, down, left, right, and diagonals)
        dfs(matrix, visited, row - 1, col); // Up
        dfs(matrix, visited, row + 1, col); // Down
        dfs(matrix, visited, row, col - 1); // Left
        dfs(matrix, visited, row, col + 1); // Right
        dfs(matrix, visited, row - 1, col - 1); // Diagonal up-left
        dfs(matrix, visited, row - 1, col + 1); // Diagonal up-right
        dfs(matrix, visited, row + 1, col - 1); // Diagonal down-left
        dfs(matrix, visited, row + 1, col + 1); // Diagonal down-right
    }

    public static void main(String[] args) {
        // Example usage
        int[][] matrix = {
            {1, 0, 1, 0},
            {1, 1, 0, 0},
            {0, 1, 2, 1},
            {0, 0, 0, 1}
        };

        int clusterCount = countClusters(matrix);
        System.out.println("Number of clusters: " + clusterCount);
    }
}
