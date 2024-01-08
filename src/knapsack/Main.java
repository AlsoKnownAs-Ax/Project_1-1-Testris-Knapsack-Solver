package knapsack;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception{
        SmartGroup group = new SmartGroup();
        Pane pane = new Pane();

        SmartPentomino L_pento = new SmartPentomino("L", LShapeColor, -150, 100, boxSize);
        SmartPentomino P_pento = new SmartPentomino("P", PShapeColor, -20, 90, boxSize);
        SmartPentomino T_pento = new SmartPentomino("T", TShapeColor, 110, -90, boxSize);

        L_pento.DisplayPentomino(group);
        P_pento.DisplayPentomino(group);
        T_pento.DisplayPentomino(group);

        
        //Creating the Start button
        Button button = new Button();
        button.setText("Start Visulation");
        button.setTranslateX(-50);
        button.setTranslateY(60);
        button.setTranslateZ(20);
        pane.getChildren().addAll(group,button);

        //This will start the Alghorithm
        button.setOnAction(event -> {
            System.out.println("Clicked");
            System.out.println("Alghortihm running...");
            button.setVisible(false);
        });

        BackgroundFill backgroundFill = new BackgroundFill( Color.SILVER, new CornerRadii(10), new Insets(10) );
        Background background = new Background(backgroundFill);
        pane.setBackground(background);

        Camera camera = new PerspectiveCamera(true);
        Scene scene = new Scene(pane, WIDTH,HEIGHT);
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

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            System.out.println("Key Code: " + event.getCode());

            switch(event.getCode()) {
                case A:
                    group.rotateByY(2);
                    break;
                case D:
                    group.rotateByY(-2);
                    break;
                default:
                    break;
            }
        });

        primaryStage.setTitle("Knapsack problem - Group 29");
        primaryStage.setScene(scene);
        primaryStage.show();
	}


}
