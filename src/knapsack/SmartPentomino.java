package knapsack;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class SmartPentomino extends Group {

    private Box[] cubes;
    private Color color;
    private double x;
    private double y;

    public SmartPentomino(String shape, Color color,double x,double y, double boxSize){
        shape = shape.toUpperCase();
        this.color = color;
        this.x = x;
        this.y = y;

        switch (shape) {
            case "L":
                CreateLPentomino(boxSize);
                break;
            case "P":
                CreatePPentomino(boxSize);
                break;
            case "T":
                CreateTPentomino(boxSize);
                break;
            default:
                System.out.println("SmartPentomino.java: Attempt to create a non-existent pentomino");
                break;
        }
    }

    // Getters & Setters

    public Box[] getCubes(){
        return this.cubes;
    }

    /*
     * Returns the x / y coords of the first cube in the shape
     */

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    private Rotate rotation;
    private Transform transform = new Rotate();
    
    public void rotateByY(double angle){
            this.rotation = new Rotate(angle ,Rotate.Y_AXIS);
            this.transform = this.transform.createConcatenation(this.rotation);
            this.getTransforms().clear();
            this.getTransforms().addAll(this.transform);
    }

    public void setColor(Color color){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);

        for(Box cube : this.cubes){
            cube.setMaterial(material);
        }

        this.color = color;
    }

    public void DisplayPentomino( Group group){
        for(Box shape : this.cubes){
            group.getChildren().add(shape);
        }
    }


    /*======== Create pentos functions ============ */

    /*
     *  X
     *  X
     *  X
     *  X X
     */

    private void CreateLPentomino(double boxSize){
        this.cubes = new Box[5];

        Box cube = new Box(boxSize,boxSize,boxSize);

        cube.translateXProperty().set(this.x + boxSize);
        cube.translateYProperty().set(boxSize*3 - this.y);
        cube.toFront();

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(this.color);
        cube.setMaterial(material);

        this.cubes[4] = cube;

        for(int i = 0; i < 4; i++){
            cube = new Box(boxSize,boxSize,boxSize);
            cube.translateXProperty().set(this.x);
            cube.translateYProperty().set(boxSize*i - this.y);
            cube.setMaterial(material);
            this.cubes[i] = cube;
        }
    }

    /*
     *  X X
     *  X X
     *  X
     */

    private void CreatePPentomino(double boxSize){
        this.cubes = new Box[5];

        Box cube = new Box(boxSize,boxSize,boxSize);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(this.color);
        cube.setMaterial(material);

        int downMovement = 0;
        for(int i = 0; i < 5; i++){
            if(i%2 == 0){
                cube.translateXProperty().set(this.x);
                cube.translateYProperty().set(boxSize*downMovement - this.y);
            }else{
                cube.translateXProperty().set(this.x + boxSize);
                cube.translateYProperty().set(boxSize*downMovement - this.y);
                downMovement++;
            }

            cube.setMaterial(material);
            this.cubes[i] = cube;
            cube = new Box(boxSize,boxSize,boxSize);
        }
    }

    /*
     * X X X
     *   X
     *   X
     */

    private void CreateTPentomino(double boxSize){
        this.cubes = new Box[5];

        Box cube = new Box(boxSize,boxSize,boxSize);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(this.color);
        cube.setMaterial(material);

        int index = 0;

        for(int i = 2; i >= 0; i--){
            cube = new Box(boxSize,boxSize,boxSize);
            cube.translateXProperty().set(this.x + boxSize*i);
            cube.translateYProperty().set(this.y);
            cube.setMaterial(material);
            this.cubes[index] = cube;
            index++;
        }   

        for(int i = 0 ; i < 2;i++){
            cube = new Box(boxSize,boxSize,boxSize);
            cube.translateXProperty().set(this.x + boxSize);
            cube.translateYProperty().set(boxSize*i + (this.y - 20) + 2*boxSize);
            cube.setMaterial(material);
            this.cubes[i+3] = cube;
        }
    }
    
}
