package com.example;

import java.io.*;

import java.sql.*;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class log1 {
    public void thr1(int strt, int end) throws Exception {

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
            myConn.setAutoCommit(false);
            for (int i = strt; i < end; i++) {
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
                    String query = "call openFunc('" + id + "','" + geo_name + "','"
                            + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                            + "')";
                    st.executeUpdate(query);
                } else if (status.equalsIgnoreCase("Closed")) {
                    String query = "call closeFunc('" + id + "','" + geo_name + "','"
                            + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                            + "')";
                    st.executeUpdate(query);
                } else {
                    String query = "call unknownFunc('" + id + "','" + geo_name + "','"
                            + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                            + "')";
                    st.executeUpdate(query);
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

public class mysqlStoredProcedure {
    static Scanner sc = new Scanner(System.in);
    public static int inp = sc.nextInt();
    public static int s = inp + 2;
    public static int e = inp;
    public static int i;
    public static int a = 117462 / s;
    public static Thread t;

    public static void main(String args[]) throws Exception {
        long startTime = System.nanoTime();
        for (i = 0; i < inp; i++) {
            System.out.println("Thread" + i + 1);
            t = new Thread(new Runnable() {
                log1 op = new log1();

                public void run() {

                    try {
                        op.thr1(117462 / s - a, 117462 / e);

                        a = 0;
                        s -= 1;
                        e -= 1;
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            });
            t.start();
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double totalTimeInSeconds = (double) totalTime / 1000000000;
        System.out.println("Total time : " + totalTimeInSeconds);
    }
}