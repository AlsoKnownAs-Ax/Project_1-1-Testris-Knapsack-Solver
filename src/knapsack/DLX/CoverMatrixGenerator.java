package knapsack.DLX;

import java.util.ArrayList;

import knapsack.Database.*;

public class CoverMatrixGenerator {
    private boolean[][][][][] shapes;

    private ArrayList<boolean[][][]> positions = new ArrayList<>();
    private ArrayList<Integer> rowTypes = new ArrayList<>();
    private boolean[][] coverMatrix;
    private static ArrayUtils arrayUtils = new ArrayUtils();

    int[] order;

    int width = 5;
    int height = 8;
    int depth = 33;

    public CoverMatrixGenerator(String type) { 
        selectInput(type);
        createPositions();
        generateCoverMatrix();
    }
    /** 
     *
     * Gets the exact cover matrix
     *
     * @return exact cover matrix
     */

    public boolean[][] getCoverMatrix() { 
        return coverMatrix;
    }


    /** 
     *
     * Gets the row types
     *
     * @return row type
     */

    public ArrayList<Integer> getRowTypes() { 

        return rowTypes;
    }


    /** 
     *
     * Select input between parcels and pentominoes
     *
     */

    public void selectInput(String type){ 

        if(type.equals("Parcels")) {
            boolean[][][][] A = ParcelDatabase.aRotBool;
            boolean[][][][] B = ParcelDatabase.bRotBool;
            boolean[][][][] C = ParcelDatabase.cRotBool;
            shapes = new boolean[][][][][]{A, C, B};
            order = new int[]{0, 2, 1};
        } else {
            boolean[][][][] L = PentominoesDatabase.Lpento;
            boolean[][][][] P = PentominoesDatabase.Ppento;
            boolean[][][][] T = PentominoesDatabase.Tpento;
            shapes = new boolean[][][][][]{T, P, L};
            order = new int[]{2, 1, 0};
        }
    }

    public void createPositions(){ 

        int typeNumber = 0;
        for(boolean[][][][] typeOfShape : shapes){
            for(boolean[][][] shape : typeOfShape){
                int shapeWidth = shape[0][0].length;
                int shapeHeight = shape[0].length;
                int shapeDepth = shape.length;

                for(int zPlacementStart=0; zPlacementStart < depth; zPlacementStart++){
                    for(int yPlacementStart=0; yPlacementStart < height; yPlacementStart++){
                        for(int xPlacementStart=0; xPlacementStart < width; xPlacementStart++){
                            if(isPlaceable(xPlacementStart, yPlacementStart, zPlacementStart, shape)){
                                boolean[][][] shapeInContainer = new boolean[depth][height][width];
                                for(int zShape=0; zShape < shapeDepth; zShape++){      
                                        for(int yShape=0; yShape < shapeHeight; yShape++) {        
                                                for (int xShape = 0; xShape < shapeWidth; xShape++) {   
                                                        if(shape[zShape][yShape][xShape]){
                                                            int zContainer = zPlacementStart + zShape;
                                                            int yContainer = yPlacementStart + yShape;
                                                            int xContainer = xPlacementStart + xShape;
                                                            shapeInContainer[zContainer][yContainer][xContainer] = true;
                                                        }
                                                }
                                        }
                                }
                                int rowType =  order[typeNumber]+1;
                                positions.add(shapeInContainer);
                                rowTypes.add(rowType);
                            }
                        }
                    }
                }
            }
            typeNumber++;
        }
    }
    
    /** 
     *
     * Check if shape is placeable
     *
     * @param startX  the start X point
     * @param startY  the start Y point
     * @param startZ  the start Z point
     * @param shape  the shape to place
     * @return true if placeable, false if not
     */
    
    public boolean isPlaceable(int startX, int startY, int startZ, boolean[][][] shape){ 

        int shapeWidth = shape[0][0].length;
        int shapeHeight = shape[0].length;
        int shapeDepth = shape.length;

        if(startX+shapeWidth > width){
            return false;
        }

        if(startY+shapeHeight > height){
            return false;
        }

        return startZ + shapeDepth <= depth;
    }
    
    public void generateCoverMatrix(){ 


        boolean[][] result = new boolean[positions.size()][width*height*depth];

        for(int i = 0; i < positions.size(); i++){
            boolean[][][] position = positions.get(i);
            
            boolean[] oneD = arrayUtils.ConvertTo1D(position, width, height, depth);
            result[i] = oneD;
            
        }

        coverMatrix = result;
    }



}