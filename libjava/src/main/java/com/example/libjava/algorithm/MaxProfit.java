package com.example.libjava.algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：假设把某股票的价格按照时间先后顺序存储在数组中，请问卖该股票一次可能获得的最大利润是多少？
 * 例如，一只股票在某些时间节点的价格为{9,11,8,5,7,12,16,14},如果我们能在价格为5的时候买入并在16时卖出，则能收获最大的利润11.
 * 一、股票的最大利润（一次卖出）
 * 一个数组代表股票每天的价格，可以选择从某一天买入，然后之后的一天卖出，求能够获得的最大收益。
 * 例如，一只股票在某些时间节点的价格为{9,11,8,5,7,12,16,14}。在价格为5的时候买入并在价格为16时卖出，则能获得最大的利润为11.
 */
public class MaxProfit {

    public static void main(String[] args){
        int[] array = new int[]{9,11,8,5,7,12,16,14};
        //int[] array = new int[]{9,11,8,7,6,5,10};
        //getProfit(array);
        //getProfit2(array);

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        synchronized (MaxProfit.class) {
            try {
                condition.await();
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static int getProfit(int[] array) {
        int[] maxProfitArray = new int[array.length];
        for (int i = 0; i < array.length - 1; i++) {
            maxProfitArray[i] = array[i+1] - array[i];
            for (int j = i+2; j < array.length; j++) {
                int detal = array[j] - array[i];
                if (detal > maxProfitArray[i]) {
                    maxProfitArray[i] = detal;
                }
            }
        }

        //找出maxProfit中的最大值，即为收益最大的
        int maxProfit = 0;
        for (int i = 0; i < maxProfitArray.length; i++) {
            if (maxProfitArray[i] > maxProfit) {
                maxProfit = maxProfitArray[i];
            }
        }
        System.out.println("maxProfit ="+maxProfit);
        return maxProfit;
    }


    public static int getProfit2(int[] array) {
        int maxProfit = 0;
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                int detal = array[i] - array[j];
                if (detal > maxProfit) {
                    maxProfit = detal;
                }
            }
        }
        System.out.println("maxProfit ="+maxProfit);
        return maxProfit;
    }
}
