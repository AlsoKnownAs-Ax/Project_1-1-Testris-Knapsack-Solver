package knapsack.UI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import knapsack.Main;

public class CargoRender {
    
    private int x;
    private int y;

    private int z_offset;
    private static int[][][] lastMatrix;

    //Visualisation config
    private static final Color LShapeColor = Color.DARKGOLDENROD;
    private static final Color PShapeColor = Color.rgb(0,0,100);
    private static final Color TShapeColor = Color.CADETBLUE;

    private int boxSize;

    public CargoRender(int x, int y,int boxSize){
        this.boxSize = boxSize;
        this.x = x;
        this.y = y;
    }

    public CargoRender(int x, int y,int boxSize, int z_offset){
        this.boxSize = boxSize;
        this.x = x;
        this.y = y;
        this.z_offset = z_offset;
    }

    /**
     * Renders the Matrix in 3D Space and adds it to the Screen's Group
     * @param group
     * @param MatrixToRender (0 - Empty, 1 - L Cube, 2 = P cube, 3 = T cube)
     */

     public void RenderCargo(Group group, int[][][] MatrixToRender){
        group.getChildren().clear();
        lastMatrix = new int[MatrixToRender.length][MatrixToRender[0].length][MatrixToRender[0][0].length];
        boolean isfull = true;

        //Iterate over the x-dimension
        for (int x = 0; x < MatrixToRender.length; x++) {
            // Iterate over the z-dimension
            for (int z = 0; z < MatrixToRender[x][0].length; z++) {
                // Iterate over the y-dimension from top to bottom
                for (int y = 0; y < MatrixToRender[x].length; y++) {
                    // Access the element at matrix[x][y][z]
                    int element = MatrixToRender[x][y][z];

                    // Make sure the element 
                    if(element > 0){
                        Box cube = new Box(this.boxSize,this.boxSize,this.boxSize);
    
                        cube.translateXProperty().set(this.x + this.boxSize*x);
                        cube.translateYProperty().set(this.y + this.boxSize*(MatrixToRender[x].length - 1 - y)); // Reverse y here
                        cube.translateZProperty().set(this.boxSize*z + this.z_offset);
    
                        cube.setCullFace(CullFace.NONE);
                        setColorForCube(element, cube);
                        group.getChildren().add(cube);
                    }else{
                        isfull = false;
                    }

                    lastMatrix[x][y][z] = element;
                    // } else {
                    //     Box cube = new Box(this.boxSize,this.boxSize,this.boxSize);
    
                    //     cube.translateXProperty().set(this.x + this.boxSize*x);
                    //     cube.translateYProperty().set(this.y + this.boxSize*(MatrixToRender[x].length - 1 - y)); // Reverse y here
                    //     cube.translateZProperty().set(this.boxSize*z);
    
                    //     cube.setCullFace(CullFace.NONE);
                    //     setColorForCube(2, cube);
                    //     group.getChildren().add(cube);
                    // }
                }
            }
        }
        long elapsedTime = System.nanoTime() - Main.startTime;

        if(!isfull){
            System.out.println("Cover: not full");
        }else{
            System.out.println("Cover: Full");
        }
        System.out.println("Run Time: " + elapsedTime/1000000 + "ms");
    }

    public static class Center{
        private int x;
        private int y;
        private int z;

        public Center(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {return this.x;}
        public int getY() {return this.y;}
        public int getZ() {return this.z;}
    }

    public Center getCenter(){
        int centerX = (lastMatrix.length)/2;
        int centerY = (lastMatrix[0].length)/2;
        int centerZ = (lastMatrix[0][0].length)/2;

        Center center = new Center(centerX, centerY, centerZ);

        return center;
    }

    private void setColorForCube(int id,Box cube){
        cube.setCullFace(CullFace.NONE);
        cube.setDrawMode(DrawMode.FILL);
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(Color.BLACK);

        switch (id) {
            case 1:
                material.setDiffuseColor(LShapeColor);
                break;
            case 2:
                material.setDiffuseColor(PShapeColor);
                break;
            case 3:
                material.setDiffuseColor(TShapeColor);
                break;
            default:
                break;
        }

        cube.setMaterial(material);
    }

    // private void ClearField(){
    //     //Iterate over the x-dimension
    //     for (int x = 0; x < lastMatrix.length; x++) {
    //         // Iterate over the z-dimension
    //         for (int z = 0; z < lastMatrix[x][0].length; z++) {
    //             // Iterate over the y-dimension from top to bottom (reversed)
    //             for (int y = 0; y < lastMatrix[x].length; y++) {
    //                 lastMatrix[x][y][z] = 0
    //             }
    //         }
    //     }

         
    // }

}
