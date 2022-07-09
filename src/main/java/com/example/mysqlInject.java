package com.example;

import java.io.*;

import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class mysqlInject {
    public static void main(String args[]) throws Exception {
        String url = "jdbc:mysql://localhost:3306/kimber";
        String user = "root";
        String password = "almightypunch";
        JSONParser jsonP = new JSONParser();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection myConn = DriverManager.getConnection(url, user, password);
        Statement st = myConn.createStatement();
        int temp = 0;
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("globalrailways.json"));

            JSONArray array = (JSONArray) jsonO.get("features");
            myConn.setAutoCommit(false);
            for (int i = 0; i < array.size(); i++) {
                JSONObject address = (JSONObject) array.get(i);
                JSONObject add = (JSONObject) address.get("properties");
                String status = (String) add.get("status");
                String remarks = (String) add.get("remarks");
                String iso_3 = (String) add.get("iso3");
                String create_date = (String) add.get("createdate");
                String type = (String) address.get("type");
                String id = (String) address.get("id");
                String geo_name = (String) address.get("geometry_name");
                if (status.equalsIgnoreCase("Open")) {
                    String query = "insert into open_global values('" + id + "','" + geo_name + "','"
                            + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                            + "')";
                    st.executeUpdate(query);
                } else if (status.equalsIgnoreCase("Closed")) {
                    String query = "insert into close_global values('" + id + "','" + geo_name + "','"
                            + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                            + "')";
                    st.executeUpdate(query);
                } else {
                    String query = "insert into unknown_global values('" + id + "','" + geo_name + "','"
                            + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                            + "')";
                    st.executeUpdate(query);
                }
                temp++;
                if (temp > 500) {
                    myConn.commit();
                    temp = 0;
                }
            }
            myConn.commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myConn.close();
    }
}