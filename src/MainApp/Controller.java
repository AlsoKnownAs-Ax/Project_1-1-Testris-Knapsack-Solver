package MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button showHIghScores;

    @FXML
    private Button startGamebtn;

    GameController gameController = new GameController();


    @FXML
    void ShowHighScores(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("fxml\\HighScoreScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

       setHighScoreTexts();

    }

    private void setHighScoreTexts(){
        int[] scores = JSONHandler.GetScoresArray();
        String[] times = JSONHandler.GetTimesArray();

        for(int i = 1; i <= scores.length; i++){
            Text CurrentText = (Text) root.lookup("#top"+i+"Text");
            if(CurrentText != null) CurrentText.setText("Top "+i+": " + " Score: " + scores[i-1] + " Time: " + times[i-1]);
        }
    }

    @FXML
    void ShowTitleScreen(ActionEvent event) throws IOException {
        
        root = FXMLLoader.load(getClass().getResource("fxml\\main.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void StartGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml\\GameScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        gameController.StartGame();

    }

}
