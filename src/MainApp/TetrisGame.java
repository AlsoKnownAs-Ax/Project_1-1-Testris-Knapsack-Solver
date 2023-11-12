package MainApp;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class TetrisGame extends Application {

    public static Font titleFont = new Font("Rockwell", Font.PLAIN, 69);
    public static Font normalFont = new Font("Times New Roman", Font.PLAIN,26);
    public static final Font ChoiceFont = new Font("Times New Roman", Font.PLAIN,19);
    public static Font MainTextFont = new Font("Times New Roman", Font.PLAIN,30);
    public static Font PIXELED_NORMAL;

    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml\\main.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tetris: Such a fun game with Pentominos");

        //Disable fullscreen button --> code taken from: https://stackoverflow.com/questions/32282646/how-to-lock-javafx-fullscreen-mode
        
        primaryStage.fullScreenProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue != null && !newValue.booleanValue())
                primaryStage.setFullScreen(false);
        }
    });

    }

    
    public static void main(String[] args)
    {
        JSONHandler InitializeJson = new JSONHandler();

		try {
            //create the font to use. Specify the size!
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/TitleFont.ttf")).deriveFont(42f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/Pixels.ttf")).deriveFont(52f);
            Font Pixeled = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/Pixels.ttf")).deriveFont(34f);

            //register the font test
            ge.registerFont(customFont);
            ge.registerFont(customFont2);
            ge.registerFont(Pixeled);

            titleFont = customFont;
            MainTextFont = customFont2;
            PIXELED_NORMAL = Pixeled;
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        launch(args);
    }

}
