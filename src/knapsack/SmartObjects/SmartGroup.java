package knapsack.SmartObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class SmartGroup extends Group {

    private Rotate rotation;
    private Transform transform = new Rotate();

    private double anchorX, anchorY;
    private double anchorAngleX;
    private double anchorAngleY;
    private final DoubleProperty angleX;
    private final DoubleProperty angleY;

    public SmartGroup(){
        this.anchorAngleX = 0;
        this.anchorAngleY = 0;
        this.angleX = new SimpleDoubleProperty(0);
        this.angleY = new SimpleDoubleProperty(0);
    }
    
    public void rotateByY(double angle){
        this.rotation = new Rotate(angle ,Rotate.Y_AXIS);
        this.transform = this.transform.createConcatenation(this.rotation);
        this.getTransforms().clear();
        this.getTransforms().addAll(this.transform);
    }

    public void rotateByX(double angle){
        this.rotation = new Rotate(angle ,Rotate.X_AXIS);
        this.transform = this.transform.createConcatenation(this.rotation);
        this.getTransforms().clear();
        this.getTransforms().addAll(this.transform);
    }

    public Rotate rotateX = new Rotate();
    { rotateX.setAxis(Rotate.X_AXIS); }
    public Rotate rotateY = new Rotate();
    { rotateY.setAxis(Rotate.Y_AXIS); }
    public Rotate rotateZ = new Rotate();
    { rotateZ.setAxis(Rotate.Z_AXIS); }

    public void initMouseControl( Scene scene ){
        Rotate xRotate;
        Rotate yRotate;
        this.getTransforms().addAll(
            xRotate = new Rotate(0 , Rotate.X_AXIS),
            yRotate = new Rotate(0 , Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            this.anchorX = event.getSceneX();
            this.anchorY = event.getSceneY();
            this.anchorAngleX = angleX.get();
            this.anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(this.anchorAngleX - (this.anchorY - event.getSceneY()));
            angleY.set(this.anchorAngleY + this.anchorX - event.getSceneX());
        });
    }
    
    public void setPivot(SmartGroup group, int xCoord, int yCoord, int zCoord) {
        group.rotateX.setPivotX(xCoord);
        group.rotateX.setPivotY(yCoord);
        group.rotateX.setPivotZ(zCoord);
        group.rotateY.setPivotX(xCoord);
        group.rotateY.setPivotY(yCoord);
        group.rotateY.setPivotZ(zCoord);
        group.rotateZ.setPivotX(xCoord);
        group.rotateZ.setPivotY(yCoord);
        group.rotateZ.setPivotZ(zCoord);
    }
}
