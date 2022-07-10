// package com.example;

// import java.util.Scanner;

// public class opopo {
//     ;

//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         int n = sc.nextInt();
//         int data = 1000;
//         int iteration = data / n;
//         int low_range = 0, high_range = 0;
//         for (int x = 1; x <= n; x++) {
//             if (x == 1) {
//                 low_range = 1;
//                 high_range = iteration;
//             } else {
//                 low_range = high_range + 1;
//                 high_range += iteration;
//             }
//             System.out.println(n + " " + low_range + " " + high_range);
//             sc.close();
//         }
//     }

// }
package com.example;

import java.util.Scanner;

public class opopo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int x = 0;
        int y = 1000 / n;
        int op = 1000 / n;
        for (int i = 0; i < n; i++) {
            System.out.println(x + " " + y);
            x += op;
            y += op;
        }
    }
}