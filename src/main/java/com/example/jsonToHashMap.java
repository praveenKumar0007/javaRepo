package com.example;

import java.io.*;

import java.sql.*;
import java.util.HashMap;
// import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class jsonToHashMap {
    public static void main(String args[]) throws Exception {
        long startTime = System.nanoTime();
        String url = "jdbc:mysql://localhost:3306/sporServer";
        String user = "root";
        String password = "almightypunch";
        JSONParser jsonP = new JSONParser();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection myConn = DriverManager.getConnection(url, user, password);
        Statement st = myConn.createStatement();
        // int temp = 0;
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("globalrailways.json"));
            JSONArray array = (JSONArray) jsonO.get("features");
            // myConn.setAutoCommit(false);
            int op = 0;
            int po = array.size();
            for (int i = op; i < po; i++) {
                JSONObject address = (JSONObject) array.get(i);
                JSONObject add = (JSONObject) address.get("properties");
                HashMap<String, String> propertiesMap = new Gson().fromJson(add.toString(), HashMap.class);
                HashMap<String, String> StringMap = new Gson().fromJson(address.toString(), HashMap.class);
                // Set<String> keys = jsonToMap.keySet();
                String id = StringMap.get("id");
                String geo_name = StringMap.get("geometry_name");
                String type = StringMap.get("type");
                String prop_status = propertiesMap.get("status");
                String prop_remarks = propertiesMap.get("remarks");
                String prop_iso_3 = propertiesMap.get("iso3");
                String prop_C_D = propertiesMap.get("createdate");
                if (prop_status.equals("Open")) {
                    String query = "call openFunc('" + id + "','" + geo_name + "','"
                            + type + "','" + prop_status + "','" + prop_remarks + "','" + prop_iso_3 + "','" + prop_C_D
                            + "')";
                    st.executeUpdate(query);
                } else if (prop_status.equals("Closed")) {
                    String query = "call closeFunc('" + id + "','" + geo_name + "','"
                            + type + "','" + prop_status + "','" + prop_remarks + "','" + prop_iso_3 + "','" + prop_C_D
                            + "')";
                    st.executeUpdate(query);
                } else {
                    String query = "call unknownFunc('" + id + "','" + geo_name + "','"
                            + type + "','" + prop_status + "','" + prop_remarks + "','" + prop_iso_3 + "','" + prop_C_D
                            + "')";
                    st.executeUpdate(query);
                }

            }
            // myConn.commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // myConn.close();
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double totalTimeInSeconds = (double) totalTime / 1000000000;
        System.out.println("Total time : " + totalTimeInSeconds);
    }
}