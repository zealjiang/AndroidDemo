package com.example.main;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class BitOperator {
    public static void main(String[] args) {
        System.out.println("20 = "+ Integer.toBinaryString(20));
        System.out.println("7 = "+ Integer.toBinaryString(7));
        System.out.println("-8 ="+ Integer.toBinaryString(-8));
        System.out.println("(7 & -8) = "+(7 & -8));
        System.out.println("3 + 7 & -8 = "+(3 + 7 & -8));


        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);

        int M = 5;
        int n = 0;
        for (int i = 0; i < 5; i++) {
            if(n >= M)break;
            n++;
            if(list.get(i) == 3 || list.get(i) == 5){
                list.remove(i);
                i--;
            }
        }
/*        list.remove(3);
        list.remove(5);*/
        System.out.println("list="+list.toString());
    }
}
