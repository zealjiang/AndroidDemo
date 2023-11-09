package com.example.libjava;

import java.util.HashSet;

public class BreakContinueTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        retry:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 3 && i == 0) {
                    continue retry;
                }

                if (j == 4 && i == 1) {
                    break retry;
                }

                System.out.println("i ="+i+" j ="+j);
            }

        }
        System.out.println("out for");
    }
}
