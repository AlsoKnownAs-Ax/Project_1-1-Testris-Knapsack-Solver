package knapsack;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    //Alghorithm's variables

    private static final int WIDTH = 1300;
    private static final int HEIGHT = 800;

    private int[] cargo = {165, 40, 25}; // {x,y,z}

    private static final int PARCEL_SIZE = 5;

    //Utils
    private PentominoRender pentominoRender = new PentominoRender();


    public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group group = new Group();
        Box[] L_pento = pentominoRender.Draw_L_Pentomino();
        Box[] P_pento = pentominoRender.Draw_P_Pentomino();
        Box[] T_pento = pentominoRender.Draw_T_Pentomino();

        for(Box shape : L_pento){
            group.getChildren().add(shape);
        }

        for(Box shape : P_pento){
            group.getChildren().add(shape);
        }

        for(Box shape : T_pento){
            group.getChildren().add(shape);
        }

        // group.getChildren().addAll(Draw_L_Pentomino());
        Camera camera = new PerspectiveCamera(true);
        Scene scene = new Scene(group, WIDTH,HEIGHT);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        // cube.translateXProperty().set(-150);
        // cube.translateYProperty().set(0);

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
