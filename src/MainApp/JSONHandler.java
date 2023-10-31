package MainApp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {
    private static JSONObject HighScoreObject;
    
    int[] scores = new int[3];
    String[] times = new String[3];

    public JSONHandler()
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src\\MainApp\\json\\HighScoreData.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray HighScoreArray = (JSONArray) obj;
            HighScoreObject = (JSONObject) HighScoreArray.get(0);
            HighScoreObject = (JSONObject) HighScoreObject.get("scores_data");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void SetHighScoreData()
    {

        for(Iterator<String> iterator = HighScoreObject.keySet().iterator(); iterator.hasNext();)
        {
            String key = (String) iterator.next();

            JSONObject CurrentData = (JSONObject) HighScoreObject.get(key);

            String time = (String) CurrentData.get("time");
            // int score = (Long) CurrentData.get("score").intB;

            //TODO: Store times and scores in arrays
        }
    }


}
