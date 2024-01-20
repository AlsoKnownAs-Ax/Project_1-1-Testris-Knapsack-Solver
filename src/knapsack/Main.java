package knapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.DirectionalLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import knapsack.Algorithms.DancingLinks;
import knapsack.Algorithms.DancingLinksRunnable;
import knapsack.Questions.QuestionA;
import knapsack.Questions.QuestionB;
import knapsack.SmartObjects.SmartGroup;
import knapsack.SmartObjects.SmartPentomino;
//import knapsack.Tests.*;
import knapsack.UI.CargoRender;

public class Main extends Application {

    //Tests
    private static int[][][] TestMatrix = {
        {
            {0, 0, 0},
            {0, 0, 0},
            {1, 0, 0},
        },
        {
            {1, 0, 0},
            {1 ,0, 0},
            {1, 0, 0},
        },
        {
            {0, 0, 0},
            {0 ,0, 0},
            {1, 0, 0},
        },
    };

    private static int[][][] TestMatrix2 = {
        {
            {1, 0, 2},
            {0, 3, 3}
        },
        {
            {1, 0, 2},
            {0, 1, 3}
        },
        {
            {1, 0, 2},
            {0, 3, 1}
        },
        {
            {1, 2, 2},
            {0, 3, 3}
        },
        {
            {1, 0, 2},
            {0, 3, 2}
        },
        {
            {1, 0, 2},
            {0, 3, 3}
        },
        {
            {0, 2, 2},
            {1, 0, 0}
        }
    };

    //

    //Visualisation config ( Also change it in knapsack/UI/CargoRender.java )
    private static final Color LShapeColor = Color.DARKGOLDENROD;
    private static final Color PShapeColor = Color.rgb(0,0,100);
    private static final Color TShapeColor = Color.CADETBLUE;
    private static final int boxSize = 20;

    //Alghorithm's variables

    private static final int WIDTH = 1300;
    private static final int HEIGHT = 800;
    private int[] cargo = {165, 40, 25}; // {x,y,z}

    public int cargoHeight = 40;
    public int cargoWidth = 25;
    public int cargoDepth = 8;

    private static final int PARCEL_SIZE = 5;

    public static void main(String[] args) {
		launch(args);
	}

    //Test purposes
    public static SmartGroup cargoGroup = new SmartGroup();
    public static Label score_label = new Label("Score: 0");
    private static Camera camera = new PerspectiveCamera(true);


    @Override
    public void start(Stage primaryStage) throws Exception{
        SmartGroup showcaseGroup = new SmartGroup();

        Group root = new SmartGroup();

        SmartPentomino L_pento = new SmartPentomino("L", LShapeColor, -150, 100, boxSize);
        SmartPentomino P_pento = new SmartPentomino("P", PShapeColor, -20, 90, boxSize);
        SmartPentomino T_pento = new SmartPentomino("T", TShapeColor, 110, -90, boxSize);

        score_label.setTranslateX(-40);
        score_label.setTranslateY(-220);
        score_label.setTranslateZ(380);

        L_pento.DisplayPentomino(showcaseGroup);
        P_pento.DisplayPentomino(showcaseGroup);
        T_pento.DisplayPentomino(showcaseGroup);

        //Creating the Start button
        Button buttonA = new Button("Question A");
        Button buttonB = new Button("Question B");
        Button buttonC = new Button("Question C");
        Button buttonD = new Button("Question D");

        buttonA.setTranslateX(-200);
        buttonA.setTranslateY(200);
        buttonA.setTranslateZ(380);

        buttonB.setTranslateX(-100);
        buttonB.setTranslateY(200);
        buttonB.setTranslateZ(380);

        buttonC.setTranslateX(0);
        buttonC.setTranslateY(200);
        buttonC.setTranslateZ(380);

        buttonD.setTranslateX(100);
        buttonD.setTranslateY(200);
        buttonD.setTranslateZ(380);

        root.getChildren().addAll(buttonA,buttonB,buttonC,buttonD);

        

        //This will start the Alghorithm
        buttonA.setOnAction(event -> {
            Thread thread = new Thread(new DancingLinksRunnable("Parcels",false));

            thread.start();
        });
        buttonB.setOnAction(event -> {
            Thread thread = new Thread(new DancingLinksRunnable("Parcels",true));

            thread.start();
        });
        buttonC.setOnAction(event -> {
            Thread thread = new Thread(new DancingLinksRunnable("Pentomino",false));

            thread.start();
        });
        buttonD.setOnAction(event -> {
            Thread thread = new Thread(new DancingLinksRunnable("Pentomino",true));

            thread.start();
            
        });

        root.getChildren().add(showcaseGroup);
        root.getChildren().add(cargoGroup);
        root.getChildren().add(score_label);

        Scene scene = new Scene(root, WIDTH,HEIGHT,true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        cargoGroup.initMouseControl(scene);

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
            switch(event.getCode()) {
                case A:
                    showcaseGroup.rotateByY(2);
                    break;
                case D:
                    showcaseGroup.rotateByY(-2);
                    break;
                default:
                    break;
            }
        });

        // Ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.setOpacity(0.3);
        showcaseGroup.getChildren().add(ambientLight);

        // Directional light
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setColor(Color.WHITE);
        directionalLight.setDirection(new Point3D(-1, -1, -1));
        showcaseGroup.getChildren().add(directionalLight);

        // Point light for specific highlights
        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE);
        pointLight.getTransforms().add(new Translate(50, -50, 50));
        showcaseGroup.getChildren().add(pointLight);

        primaryStage.setTitle("Knapsack problem - Group 29");
        primaryStage.setScene(scene);
        primaryStage.show();
	}


}
