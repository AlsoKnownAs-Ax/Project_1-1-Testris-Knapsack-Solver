package knapsack.DLX;


public class ArrayUtils {

    public ArrayUtils(){}

    /**
    * convert the field from 1D to 3D
    * @param array field
    * @param width width of 3D
    * @param height height of 3D
    * @param depth depth of 3D
    * @return 3D field
    */

    public boolean[][][] ConvertTo3D (boolean[] array, int width, int height, int depth)
    {

        boolean[][][] array3D = new boolean[depth][height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    array3D[z][y][x] = array[height * depth * x + depth * y + z];
                }
            }
        }
        return array3D;
    }

    /**
    * convert the field from 3D to 1D
    * @param array3D 3D array
    * @param width width of 3D
    * @param height height of 3D
    * @param depth depth of 3D
    * @return 1D field
    */

    public boolean[] ConvertTo1D (boolean[][][] array3D, int width, int height, int depth) {
        boolean[] result = new boolean[width * height * depth];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    result[height * depth * x + depth * y + z] = array3D[z][y][x];
                }
            }
        }

        return result;
    }
}