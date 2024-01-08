package knapsack;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class SmartGroup extends Group {

    private Rotate rotation;
    private Transform transform = new Rotate();
    
    public void rotateByY(double angle){
            this.rotation = new Rotate(angle ,Rotate.Y_AXIS);
            this.transform = this.transform.createConcatenation(this.rotation);
            this.getTransforms().clear();
            this.getTransforms().addAll(this.transform);
    }
}
