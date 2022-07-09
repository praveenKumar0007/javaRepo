package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class pract {
    public static void main(String[] args) {
        Map<String, Integer> maper = new HashMap<>();
        maper.put("Praveen", 90);
        maper.put("rooop", 26);
        Set<String> keys = maper.keySet();
        for (String i : keys) {
            System.out.println(i + " : " + maper.get(i));
        }
    }
}