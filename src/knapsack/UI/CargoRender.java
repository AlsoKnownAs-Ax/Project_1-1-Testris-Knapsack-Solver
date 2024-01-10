package knapsack.UI;

import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;

public class CargoRender {
    
    private int x;
    private int y;

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

    /**
     * Renders the Matrix in 3D Space and adds it to the Screen's Group
     * @param group
     * @param MatrixToRender (0 - Empty, 1 - L Cube, 2 = P cube, 3 = T cube)
     */

    public void RenderCargo(Group group, int[][][] MatrixToRender){
        // Iterate over the x-dimension
        for (int x = 0; x < MatrixToRender.length; x++) {
            // Iterate over the y-dimension
            for (int y = 0; y < MatrixToRender[x].length; y++) {
                // Iterate over the z-dimension
                for (int z = 0; z < MatrixToRender[x][y].length; z++) {
                    // Access the element at matrix[x][y][z]
                    int element = MatrixToRender[x][y][z];

                    //Make sure the element 
                    if(element > 0){
                        Box cube = new Box(this.boxSize,this.boxSize,this.boxSize);

                        cube.translateXProperty().set(this.x + this.boxSize*x);
                        cube.translateYProperty().set(this.y + this.boxSize*y);
                        cube.translateZProperty().set(this.boxSize*z);

                        cube.setCullFace(CullFace.NONE);
                        setColorForCube(element, cube);
                        group.getChildren().add(cube);
                    }
                }
            }
        }
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

}
