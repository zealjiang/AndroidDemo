package com.example.libjava.algorithm;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 2
 * 输出：2
 * 解释：有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 * 示例 2：
 *
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 */
public class ClimbStairs {

    public static void main(String[] args) {
        System.out.println("---方法1----");
        //方法1：
        for (int i = 0; i < 30; i++) {
            System.out.println(i+"  ="+climbStairs(i));
        }
        System.out.println("---方法2----");
        //方法2：
        for (int i = 0; i < 30; i++) {
            System.out.println(i+"  ="+climbStairs(i));
        }
    }

    public static int climbStairs(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    public static int climbStairs2(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int p = 0, q = 1, r = 2;
        for (int i = 3; i <= n; ++i) {
            p = q;
            q = r;
            r = p + q;
        }

        return r;
    }
}
