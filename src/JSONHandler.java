
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * Concept: We will try to keep track of the current High Scores in 2 arrays and then store them in HighScoreData.json
 * Functions Docs:
 * 
 * -> SetHighScoreData()
 *      > Reads the JSON Object and stores the time and scores in 2 arrays
 * 
 * -> TryToInsertHighScore
 *      > We will use this to check if the player did a High Score or not and then store it while keeping the array sorted
 * 
 * -> SortHighScoreDataDescending
 *       > Sorts the array desceding while keeping track of their corresponding time
 * 
 * -> SaveHighScoreData
 *       > Saves the data in the JSON File
 */

public class JSONHandler {
    private static JSONObject HighScoreObject;
    
    private static int[] scores = new int[5];
    private static String[] times = new String[5];

    /**
     * Constructor which reads the JSON file
     */

    public JSONHandler()
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src\\json\\HighScoreData.json")) {

            Object JsonData = jsonParser.parse(reader);
            JSONArray HighScoreArray = (JSONArray) JsonData;
            HighScoreObject = (JSONObject) HighScoreArray.get(0);
            HighScoreObject = (JSONObject) HighScoreObject.get("scores_data");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SetHighScoreData();
    }

    /**
     * Initializes the 2 arrays with the data from the JSON file
     */

    public void SetHighScoreData()
    {
        int index = 0;
        for(Iterator<?> iterator = HighScoreObject.keySet().iterator(); iterator.hasNext();)
        {
            String key = (String) iterator.next();

            JSONObject CurrentData = (JSONObject) HighScoreObject.get(key);

            String time = (String) CurrentData.get("time");
            int score = ((Long) CurrentData.get("score")).intValue();

            scores[index] = score;
            times[index] = time;

            index++;
        }

        SortHighScoreDataDescending(scores,times);
    }

    public static int[] GetScoresArray()
    {
        return scores;
    }

    public static String[] GetTimesArray()
    {
        return times;
    }

    /**
     * Checks if the player reached a High Score and stores it in the right place in the array depending if it
     * should be top1/top2/top3 while keeping the array sorted
     * @param lapScore
     * @param lapTime
     */

    public void TryToInsertHighScore(int lapScore,int lapTime)
    {
        String time = ConvertTimeFormat(lapTime);

        //Check if the score can be placed in top
        if(lapScore > scores[scores.length-1])
        {
            for(int i = scores.length-1; i > 0; i--)
            {
                if(lapScore > scores[i] && lapScore < scores[i - 1] || (i == 1 && lapScore > scores[i]))
                {
                    scores[i] = lapScore;
                    times[i] = time;
                    SaveHighScoreData();
                    System.out.println(":HIGH SCORE SAVED:");
                    return;
                }
            }

        }
    }

    /**
     * Convert Integer time format ( 125sec ) into String format ( 2:05 )
     * @param lapTime
     * @return
     */

    private String ConvertTimeFormat(int lapTime){
        int minutes = lapTime/60;
        int seconds = lapTime%60;

        if(seconds < 10)
            return "" + minutes + ":" + "0"+seconds;

        return "" + minutes + ":" + seconds;
    }

    /**
     * Sort the scores array descendent while keeping track of their corresponding time
     * @param scores
     * @param times
     */

    private static void SortHighScoreDataDescending(int[] scores, String[] times)
    {
        for(int i = 0 ; i < scores.length-1; i++)
            for(int j = 0; j < scores.length-i-1; j++)
            {
                if(scores[j] < scores[j+1])
                {
                    int aux = scores[j];
                    scores[j] = scores[j+1];
                    scores[j+1] = aux;

                    String temp = times[j];
                    times[j] = times[j+1];
                    times[j+1] = temp;
                }
            }
    }

    /**
     * Saves the data into the JSON file
     */

    public static void SaveHighScoreData()
    {

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src\\json\\HighScoreData.json")) {

            Object JsonData = jsonParser.parse(reader);
            JSONArray HighScoreArray = (JSONArray) JsonData;
            HighScoreObject = (JSONObject) HighScoreArray.get(0);
            HighScoreObject = (JSONObject) HighScoreObject.get("scores_data");

            //Build JSON Object to be saved
            for(int i = 0; i < scores.length; i++)
            {
                JSONObject top = (JSONObject) HighScoreObject.get("top"+(i+1));
                top.put("time",times[i]);
                top.put("score", scores[i]);
                HighScoreObject.put("top"+(i+1),top);
            }


            @SuppressWarnings("resource")
            FileWriter file = new FileWriter("src\\json\\HighScoreData.json");
            file.write(HighScoreArray.toJSONString());
            file.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * DEBUG
     */

    // public static void main(String[] args)
    // {
    //     JSONHandler InitializeJson = new JSONHandler();

    //     SaveHighScoreData();
    // }
    

}