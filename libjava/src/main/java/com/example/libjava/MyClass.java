package com.example.libjava;

import org.w3c.dom.CharacterData;

import java.util.HashSet;
import java.util.PriorityQueue;

public class MyClass {
    public static void main(String[] args) {
/*        System.out.println("~0 ="+(~0));
        System.out.println("~8 ="+(~8));//8: 0000 1000 取反： 1111 0111 --->
        System.out.println("~8 ="+Integer.toBinaryString(~8));
        System.out.println("-9 ="+Integer.toBinaryString(-9));//9: 0000 1001  反码:1111 0110 -->补码:1111 0111
        System.out.println("8<<1 ="+(8<<1));*/


        char x = '\202';
        System.out.println("x ="+(int)x+"  514 ="+(char)514+"  130 ="+(char)130);
        String s = "1.0";
        char dot = s.charAt(1);
        int iDot = (int)dot; //. --> int_10: 46
        char offset = (char)iDot;
        System.out.println("iDot ="+iDot+"  offset ="+offset);//46



        int A[] = new int[256];
        String A_DATA =
                "\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800"+  //13
                        "\u100F\u4800\u100F\u4800\u100F\u5800\u400F\u5000\u400F\u5800\u400F\u6000\u400F"+ //13
                        "\u5000\u400F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800"+ //13
                        "\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F"+ //13
                        "\u4800\u100F\u4800\u100F\u5000\u400F\u5000\u400F\u5000\u400F\u5800\u400F\u6000"+ //13
                        "\u400C\u6800\030\u6800\030\u2800\030\u2800\u601A\u2800\030\u6800\030\u6800"+ //14
                        "\030\uE800\025\uE800\026\u6800\030\u2000\031\u3800\030\u2000\024\u3800\030"+ //15
                        "\u3800\030\u1800\u3609\u1800\u3609\u1800\u3609\u1800\u3609\u1800\u3609\u1800"+ //13
                        "\u3609\u1800\u3609\u1800\u3609\u1800\u3609\u1800\u3609\u3800\030\u6800\030"+  //13
                        "\uE800\031\u6800\031\uE800\031\u6800\030\u6800\030\202\u7FE1\202\u7FE1\202"+ //15
                        "\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1"+ //15
                        "\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202"+ //15
                        "\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1\202\u7FE1"+ //15
                        "\202\u7FE1\uE800\025\u6800\030\uE800\026\u6800\033\u6800\u5017\u6800\033\201"+ //15
                        "\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2"+ //15
                        "\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201"+ //15
                        "\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2\201\u7FE2"+ //15
                        "\201\u7FE2\201\u7FE2\201\u7FE2\uE800\025\u6800\031\uE800\026\u6800\031\u4800"+ //15
                        "\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u5000\u100F"+ //13
                        "\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800"+ //13
                        "\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F"+ //13
                        "\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800"+ //13
                        "\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F\u4800\u100F"+ //13
                        "\u3800\014\u6800\030\u2800\u601A\u2800\u601A\u2800\u601A\u2800\u601A\u6800"+ //13
                        "\034\u6800\030\u6800\033\u6800\034\000\u7005\uE800\035\u6800\031\u4800\u1010"+ //15
                        "\u6800\034\u6800\033\u2800\034\u2800\031\u1800\u060B\u1800\u060B\u6800\033"+ //14
                        "\u07FD\u7002\u6800\030\u6800\030\u6800\033\u1800\u050B\000\u7005\uE800\036"+ //14
                        "\u6800\u080B\u6800\u080B\u6800\u080B\u6800\030\202\u7001\202\u7001\202\u7001"+ //14
                        "\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001\202"+ //15
                        "\u7001\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001"+ //15
                        "\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001\u6800\031\202\u7001\202"+ //15
                        "\u7001\202\u7001\202\u7001\202\u7001\202\u7001\202\u7001\u07FD\u7002\201\u7002"+ //15
                        "\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201"+ //15
                        "\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002"+ //15
                        "\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\u6800"+ //15
                        "\031\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002\201\u7002"+ //15
                        "\u061D\u7002"; //2


        System.out.println("string_length ="+A_DATA.length());
        char[] data = A_DATA.toCharArray();
        System.out.println("data_size ="+data.length+" 0 ="+(int)A_DATA.charAt(0));
/*        for (int i = 0; i < data.length; i++) {
            System.out.println("data "+i+"  ="+data[i]);
        }*/
        int i = 0, j = 0;
        while (i < (256 * 2)) {
            char c = data[i++];
            int entry = c << 16;
            System.out.println("i ="+(i- 1)+"  c ="+Integer.toHexString((int)c)+"  c_2 ="+Integer.toBinaryString((int)c) +"  entry_2 ="+Integer.toBinaryString(entry));
            char c2 = data[i++];
            A[j++] = entry | c2;
            //System.out.println("i ="+i+"  c ="+c+" entry ="+entry+"  c_2 ="+Long.toBinaryString(c)+"  entry_2 ="+Long.toBinaryString(entry)+"  A ="+Long.toBinaryString(A[j]));
            System.out.println("i ="+(i-1)+"  c2 ="+Integer.toHexString((int)c2)+"  c_2 ="+Integer.toBinaryString((int)c2) + "  A["+(j-1)+"] ="+Integer.toBinaryString(A[j-1]));
        }


        int props = A[offset];
        System.out.println("props ="+props+"  props_2 ="+Integer.toHexString(props));

        int value = -1;
        int val = props;
        int kind = val & 0x1F;
        System.out.println("val ="+val+"  val_2 ="+Integer.toBinaryString(val)+"  0x1F ="+Integer.toBinaryString(0x1F)+"  kind ="+kind);
        System.out.println("val ="+Integer.toBinaryString(val)+"  0xC00 ="+Integer.toBinaryString(0xC00)+"  0x00000C00 ="+kind);
        if (kind == Character.DECIMAL_DIGIT_NUMBER) {
            value = dot + ((val & 0x3E0) >> 5) & 0x1F;
        }
        else if ((val & 0xC00) == 0x00000C00) {
            // Java supradecimal digit
            value = (dot + ((val & 0x3E0) >> 5) & 0x1F) + 10;
        }

        //value < 10 ? value : -1;


        if(true)return;

/*        digit = Character.digit(s.charAt(1),10);
        int a = Integer.parseInt(s, 10);
        //int ia = Integer.valueOf(a);
        System.out.println("a ="+a+"  ");*/

    }
}