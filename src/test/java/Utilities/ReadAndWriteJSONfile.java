package Utilities;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class ReadAndWriteJSONfile {

    public static void main(String[] args) {
        createJSONFile();

    }

    public static void createJSONFile() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Test1");
        jsonObject.put("age", "30");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("java");
        jsonArray.add("python");
        jsonArray.add("javascript");

        jsonObject.put("courses", jsonArray);

        try(FileWriter file = new FileWriter("simpleJsonFile.json"))
        {
            file.write(jsonObject.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);

    }

}
