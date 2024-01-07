package knapsack;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class PentominoRender {

    //Visualisation variables
    private static final int boxSize = 20;
    private static final Color LShapeColor = Color.DARKGOLDENROD;
    private static final Color PShapeColor = Color.rgb(0,0,100);
    private static final Color TShapeColor = Color.CADETBLUE;
    
    public PentominoRender(){}

    /*
     *  X
     *  X
     *  X
     *  X X
     */

    public Box[] Draw_L_Pentomino(){
        Box[] L_Pentomino = new Box[5];

        Box cube = new Box(boxSize,boxSize,boxSize);

        cube.translateXProperty().set(-150 + boxSize);
        cube.translateYProperty().set(boxSize*3 - 100);
        cube.toFront();

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(LShapeColor);
        cube.setMaterial(material);

        L_Pentomino[4] = cube;

        for(int i = 0; i < 4; i++){
            cube = new Box(boxSize,boxSize,boxSize);
            cube.translateXProperty().set(-150);
            cube.translateYProperty().set(boxSize*i - 100);
            cube.setMaterial(material);
            L_Pentomino[i] = cube;
        }

        return L_Pentomino;
    }

    /*
     *  X X
     *  X X
     *  X
     */

    public Box[] Draw_P_Pentomino(){
        Box[] Pentomino = new Box[5];

        Box cube = new Box(boxSize,boxSize,boxSize);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(PShapeColor);
        cube.setMaterial(material);

        int downMovement = 0;
        for(int i = 0; i < 5; i++){
            if(i%2 == 0){
                cube.translateXProperty().set(-50);
                cube.translateYProperty().set(boxSize*downMovement - 90);
            }else{
                cube.translateXProperty().set(-50 + boxSize);
                cube.translateYProperty().set(boxSize*downMovement - 90);
                downMovement++;
            }

            cube.setMaterial(material);
            Pentomino[i] = cube;
            cube = new Box(boxSize,boxSize,boxSize);
        }

        return Pentomino;
    }
    /*
     * X X X
     *   X
     *   X
     */

    public Box[] Draw_T_Pentomino(){
        Box[] Pentomino = new Box[5];

        Box cube = new Box(boxSize,boxSize,boxSize);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(TShapeColor);
        cube.setMaterial(material);

        for(int i = 0; i < 3; i++){
            cube = new Box(boxSize,boxSize,boxSize);
            cube.translateXProperty().set(50 + boxSize*i);
            cube.translateYProperty().set(-90);
            cube.setMaterial(material);
            Pentomino[i] = cube;
        }

        for(int i = 0 ; i < 2;i++){
            cube = new Box(boxSize,boxSize,boxSize);
            cube.translateXProperty().set(50 + boxSize);
            cube.translateYProperty().set(boxSize*i - 110 + 2*boxSize);
            cube.setMaterial(material);
            Pentomino[i+3] = cube;
        }


        return Pentomino;
    }
    

}
