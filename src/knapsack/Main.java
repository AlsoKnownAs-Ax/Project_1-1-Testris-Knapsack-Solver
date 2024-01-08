package knapsack;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class Main extends Application {

    //Visualisation config
    private static final Color LShapeColor = Color.DARKGOLDENROD;
    private static final Color PShapeColor = Color.rgb(0,0,100);
    private static final Color TShapeColor = Color.CADETBLUE;
    private static final int boxSize = 20;


    //Alghorithm's variables

    private static final int WIDTH = 1300;
    private static final int HEIGHT = 800;
    private int[] cargo = {165, 40, 25}; // {x,y,z}
    private static final int PARCEL_SIZE = 5;

    //Utils


    public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group group = new Group();

        SmartPentomino L_pento = new SmartPentomino("L", LShapeColor, -150, 100, boxSize);
        SmartPentomino P_pento = new SmartPentomino("P", PShapeColor, -20, 90, boxSize);
        SmartPentomino T_pento = new SmartPentomino("T", TShapeColor, 110, -90, boxSize);

        for(Box shape : L_pento.getCubes()){
            group.getChildren().add(shape);
        }

        for(Box shape : P_pento.getCubes()){
            group.getChildren().add(shape);
        }

        for(Box shape : T_pento.getCubes()){
            group.getChildren().add(shape);
        }

        Camera camera = new PerspectiveCamera(true);
        Scene scene = new Scene(group, WIDTH,HEIGHT);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-500);

        camera.setNearClip(1);
        camera.setFarClip(1000);

        //Disable fullscreen button --> code taken from: https://stackoverflow.com/questions/32282646/how-to-lock-javafx-fullscreen-mode
        
        primaryStage.fullScreenProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue != null && !newValue.booleanValue())
                    primaryStage.setFullScreen(false);
            }
        });

        primaryStage.setTitle("Knapsack problem - Group 29");
        primaryStage.setScene(scene);
        primaryStage.show();
	}


}
