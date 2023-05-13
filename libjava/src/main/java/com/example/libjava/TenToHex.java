package com.example.libjava;

public class TenToHex {

    public static void main(String[] args) {
        System.out.println("128 = "+dec2Hex(128));
        System.out.println("0 = "+dec2Hex(0));
        System.out.println("255 = "+dec2Hex(255));
        System.out.println("78 = "+dec2Hex(78));
    }

    public static String dec2Hex(int value) {
        String[] hexes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F"};
        if (value < 0) return "";
        return (value == 0) ? "00" : (dec2Hex(value / 16) + hexes[value % 16]);
    }
}
