package com.example;

import java.io.*;

import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class maker {
    public void make(JSONArray array, Statement st, int temp, Connection myConn, int op, int po) {
        for (int i = op; i < po; i++) {
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
                try {
                    st.executeUpdate(query);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (status.equalsIgnoreCase("Closed")) {
                String query = "call closeFunc('" + id + "','" + geo_name + "','"
                        + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                        + "')";
                try {
                    st.executeUpdate(query);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                String query = "call unknownFunc('" + id + "','" + geo_name + "','"
                        + type + "','" + status + "','" + remarks + "','" + iso_3 + "','" + create_date
                        + "')";
                try {
                    st.executeUpdate(query);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            temp++;
            if (temp > 1000) {
                try {
                    myConn.commit();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                temp = 0;
            }
        }
    }
}

class runner {
    public void rn(int flag) {
        try {
            JSONParser jsonP = new JSONParser();
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("globalrailways.json"));

            String url = "jdbc:mysql://localhost:3306/sporServer";
            String user = "root";
            String password = "almightypunch";
            Connection myConn = DriverManager.getConnection(url, user, password);
            JSONArray array = (JSONArray) jsonO.get("features");
            int temp = 0;
            myConn.setAutoCommit(false);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement st = myConn.createStatement();
            if (flag == 1) {
                maker mk = new maker();
                int op = 0;
                int po = array.size() / 5;
                mk.make(array, st, temp, myConn, op, po);
            } else if (flag == 2) {
                maker mk = new maker();
                int op = array.size() / 5;
                int po = array.size() / 4;
                mk.make(array, st, temp, myConn, op, po);
            } else if (flag == 3) {
                maker mk = new maker();
                int op = array.size() / 4;
                int po = array.size() / 3;
                mk.make(array, st, temp, myConn, op, po);
            } else if (flag == 4) {
                maker mk = new maker();
                int op = array.size() / 3;
                int po = array.size() / 2;
                mk.make(array, st, temp, myConn, op, po);
            } else if (flag == 5) {
                maker mk = new maker();
                int op = array.size() / 2;
                int po = array.size();
                mk.make(array, st, temp, myConn, op, po);
            }
            myConn.commit();
        } catch (FileNotFoundException lo) {
            lo.printStackTrace();
        } catch (IOException ey) {
            ey.printStackTrace();
        } catch (ParseException op) {
            op.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class T1 extends Thread {
    public void run() {
        int flag = 1;
        runner run = new runner();
        run.rn(flag);
    }
}

class T2 extends Thread {
    public void run() {
        int flag = 2;
        runner run = new runner();
        run.rn(flag);
    }
}

class T3 extends Thread {
    public void run() {
        int flag = 3;
        runner run = new runner();
        run.rn(flag);
    }
}

class T4 extends Thread {
    public void run() {
        int flag = 4;
        runner run = new runner();
        run.rn(flag);
    }
}

class T5 extends Thread {
    public void run() {
        int flag = 5;
        long startTime = System.nanoTime();
        runner run = new runner();
        run.rn(flag);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double totalTimeInSeconds = (double) totalTime / 1000000000;
        System.out.println("Total time : " + totalTimeInSeconds);
    }
}

public class javaMultithreading {
    public static void main(String args[]) throws Exception {
        T1 thrd1 = new T1();
        T2 thrd2 = new T2();
        T3 thrd3 = new T3();
        T4 thrd4 = new T4();
        T5 thrd5 = new T5();
        thrd1.start();
        try {
            Thread.sleep(5);
        } catch (Exception e) {
        }
        thrd2.start();
        try {
            Thread.sleep(5);
        } catch (Exception e) {
        }
        thrd3.start();
        try {
            Thread.sleep(5);
        } catch (Exception e) {
        }
        thrd4.start();
        try {
            Thread.sleep(5);
        } catch (Exception e) {
        }
        thrd5.start();
    }
}
