package com.example;

import java.util.Scanner;

public class opopo {
    static Scanner sc = new Scanner(System.in);
    public static int inp = sc.nextInt();
    public static int s = inp;
    public static int e = inp;
    public static int i;
    public static int a = s;

    public static void main(String[] args) {
        for (int i = 0; i < inp; i++) {

            int op = s - a;
            int po = e;
            a = 1;
            s -= 1;
            e -= 1;
            System.out.println(op + " " + po);
        }
    }

}
