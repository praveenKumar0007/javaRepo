package com.example;

import java.io.*;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class retreivingID {
    public static void main(String args[]) {
        JSONParser jsonP = new JSONParser();
        Scanner sc = new Scanner(System.in);
        int flag = 0;
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("globalrailways.json"));

            JSONArray array = (JSONArray) jsonO.get("features");
            String check = sc.next();
            for (int i = 0; i < array.size(); i++) {
                JSONObject address = (JSONObject) array.get(i);
                String add = (String) address.get("id");
                StringBuilder op = new StringBuilder(add.substring(21));
                String h = op.toString();

                if (h.equals(check)) {
                    System.out.println(address);
                    flag = 1;
                }
            }
            if (flag == 0) {
                System.out.println("No elements found");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sc.close();
    }
}