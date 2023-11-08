package MainApp;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button showHIghScores;

    @FXML
    private Button startGamebtn;

    @FXML
    void ShowHighScores(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("fxml\\HighScoreScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
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
    void StartGame(ActionEvent event) {

    }

}
