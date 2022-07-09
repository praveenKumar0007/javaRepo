package com.example;

import java.io.*;

import java.sql.*;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class MyThread extends Thread {

  public void thr(int strt, int end) throws Exception {
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

  public void run() {
    int var = TestThread.n;
    int s = var + 2;
    int e = var;
    int a = 117462 / s;
    try {
      thr(117462 / s - a, 117462 / e);
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    a = 0;
    s -= 1;
    e -= 1;
  }
}

class TestThread {
  static Scanner input = new Scanner(System.in);
  static int n = input.nextInt();
  public static MyThread temp;

  public static void main(String arg[]) {
    long startTime = System.nanoTime();

    System.out.println("Please input the number of Threads you want to create: ");
    System.out.println("You selected " + n + " Threads");

    for (int x = 0; x < n; x++) {
      temp = new MyThread();
      temp.start();
      System.out.println("Started Thread:" + x);
    }
    try {
      temp.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    long endTime = System.nanoTime();
    long totalTime = endTime - startTime;
    double totalTimeInSeconds = (double) totalTime / 1000000000;
    System.out.println("Total time : " + totalTimeInSeconds);
  }
}