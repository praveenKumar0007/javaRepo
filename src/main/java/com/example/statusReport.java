package com.example;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class statusReport {

    public static void main(String args[]) throws Exception {
        try {
            JSONParser jsonP = new JSONParser();

            Gson gson = new Gson();
            Writer writer = new FileWriter("statusReport.json");

            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("globalrailways.json"));

            JSONArray array = (JSONArray) jsonO.get("features");
            writer.write("{\"type\":\"FeatureCollection\",\"features\":[");
            for (int i = 0; i < array.size(); i++) {
                JSONObject address = (JSONObject) array.get(i);
                JSONObject jsonProp = (JSONObject) address.get("properties");
                String strStatus = (String) jsonProp.get("status");

                if (strStatus.equals("Closed")) {
                    gson.toJson(address, writer);
                    writer.write(",");
                    writer.flush();
                }
            }
            writer.write("{\"end\":\"end of json data\"}]}");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}