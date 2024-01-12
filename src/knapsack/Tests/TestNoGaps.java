package knapsack.Tests;

import java.util.ArrayList;
import java.util.List;


public class TestNoGaps {

class Cube {
    int x, y, z;

    Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class Pentomino {
    int id;
    List<List<Cube>> orientations;

    Pentomino(int id) {
        this.id = id;
        this.orientations = new ArrayList<>();
        // Initialize with all possible orientations
    }

    List<Cube> getOrientation(int index) {
        return orientations.get(index);
    }
}

class Container {
    int length, height, width;
    int[][][] matrix;

    Container(int length, int height, int width) {
        this.length = length;
        this.height = height;
        this.width = width;
        matrix = new int[length][height][width];
    }

    boolean canPlacePentomino(Pentomino p, int orientationIndex, int x, int y, int z) {
        for (Cube cube : p.getOrientation(orientationIndex)) {
            int newX = x + cube.x;
            int newY = y + cube.y;
            int newZ = z + cube.z;
            if (newX < 0 || newX >= length || newY < 0 || newY >= height || newZ < 0 || newZ >= width || matrix[newX][newY][newZ] != 0) {
                return false;
            }
        }
        return true;
    }

    void placePentomino(Pentomino p, int orientationIndex, int x, int y, int z) {
        for (Cube cube : p.getOrientation(orientationIndex)) {
            matrix[x + cube.x][y + cube.y][z + cube.z] = p.id;
        }
    }

    void removePentomino(Pentomino p, int orientationIndex, int x, int y, int z) {
        for (Cube cube : p.getOrientation(orientationIndex)) {
            matrix[x + cube.x][y + cube.y][z + cube.z] = 0;
        }
    }
}

class PentominoPackingSolver {
    Container container;
    Pentomino[] pentominoTypes;

    PentominoPackingSolver(Container container, Pentomino[] pentominoTypes) {
        this.container = container;
        this.pentominoTypes = pentominoTypes;
    }

    boolean solve() {
        return placeNextPentomino(0, 0, 0);
    }

    boolean placeNextPentomino(int x, int y, int z) {
        if (x >= container.length) {
            return true; // Successfully filled the container
        }

        int nextX = (y + 1 < container.height) ? x : x + 1;
        int nextY = (y + 1 < container.height) ? y + 1 : 0;
        int nextZ = (z + 1 < container.width) ? z + 1 : 0;

        for (Pentomino p : pentominoTypes) {
            for (int orientation = 0; orientation < p.orientations.size(); orientation++) {
                if (container.canPlacePentomino(p, orientation, x, y, z)) {
                    container.placePentomino(p, orientation, x, y, z);
                    if (placeNextPentomino(nextX, nextY, nextZ)) {
                        return true;
                    }
                    container.removePentomino(p, orientation, x, y, z);
                }
            }
        }

        return false;
    }

    int[][][] getSolution() {
        if (solve()) {
            return container.matrix;
        } else {
            return null; // No solution found
        }
    }
}

public int[][][] getSolutionsMatrix(){
    // Usage
    Container container = new Container(165, 40, 25);
    Pentomino[] pentominoTypes = { new Pentomino(1 /* L */), new Pentomino(2 /* P */), new Pentomino(3 /* T */) };
    // Define the orientations for each pentomino here
    PentominoPackingSolver solver = new PentominoPackingSolver(container, pentominoTypes);
    int[][][] solution = solver.getSolution();

    if (solution != null) {
        return solution;
    }

    return null;
}


}
