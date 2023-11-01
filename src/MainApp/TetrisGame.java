package MainApp;

import PentominosUtils.Search;

import java.io.File;
import java.io.IOException;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class TetrisGame {

    public static Container container;
    public static Font titleFont = new Font("Rockwell", Font.PLAIN, 69);
    public static Font normalFont = new Font("Times New Roman", Font.PLAIN,26);
    public static final Font ChoiceFont = new Font("Times New Roman", Font.PLAIN,19);
    public static Font MainTextFont = new Font("Times New Roman", Font.PLAIN,30);
    public static Font PIXELED_NORMAL;
    public static int ItemsAmount = 0;


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
    }
}
