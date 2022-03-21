package input;

import model.FLOModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataReader {

    public static ArrayList<FLOModel> readFLO(String flowFile, String costFile) {
        JSONParser jsonParser = new JSONParser();

        try(FileReader flowReader = new FileReader(flowFile); FileReader costReader = new FileReader(costFile)) {
            // read the flow file
            Object flow = jsonParser.parse(flowReader);
            JSONArray flowList = (JSONArray)flow;

            // read the cost file
            Object cost = jsonParser.parse(costReader);
            JSONArray costList = (JSONArray)cost;

            //parse to FLOModel
            ArrayList<FLOModel> floModels = new ArrayList<>();
            for (int i = 0; i < flowList.size(); i++) {
                floModels.add(parseJsonToModel((JSONObject)flowList.get(i), (JSONObject)costList.get(i)));
            }
            return floModels;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static FLOModel parseJsonToModel(JSONObject flowObject, JSONObject costObject) {
         int source = ((Long)flowObject.get("source")).intValue();
         int dest = ((Long)flowObject.get("dest")).intValue();
         int amount = ((Long)flowObject.get("amount")).intValue();
         int cost = ((Long)costObject.get("cost")).intValue();

        return new FLOModel(source, dest, amount, cost);
    }
}
