package com.example.libjava.algorithm;

/**
 * 泰波那契序列 Tn 定义如下：
 *
 * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
 *
 * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 4
 * 输出：4
 * 解释：
 * T_3 = 0 + 1 + 1 = 2
 * T_4 = 1 + 1 + 2 = 4
 * 示例 2：
 *
 * 输入：n = 25
 * 输出：1389537
 *
 *
 * 提示：
 *
 * 0 <= n <= 37
 * 答案保证是一个 32 位整数，即 answer <= 2^31 - 1。
 */
public class Tribonacci {

    public static void main(String[] args) {
        System.out.println("---方法1----"+System.currentTimeMillis());
        //方法1：
        for (int i = 0; i < 30; i++) {
            System.out.println(i+"  ="+tribonacci(i));
        }
        System.out.println("---方法2----"+System.currentTimeMillis());
        //方法2：
        for (int i = 0; i < 30; i++) {
            System.out.println(i+"  ="+tribonacci2(i));
        }
        System.out.println("---end----"+System.currentTimeMillis());
    }


    public static int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 1;


        return tribonacci(n - 1) + tribonacci(n - 2) + + tribonacci(n - 3);
    }


    public static int tribonacci2(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 1;
        int a = 0, b = 1, c = 1, r = 2;
        for (int i = 3; i <= n; ++i) {
            r = a + b + c;
            a = b;
            b = c;
            c = r;
        }

        return r;
    }
}
