package com.example.libjava.algorithm;

import java.util.Arrays;

/**
 * 将两个有序的数组合并成一个有序的数组
 *  数组中的元素 0 表示无效数，无效元素出现在所有有效元素之后
 */
public class TwoOrderArrayMergeOneArray {

    public static void main(String[] args) {
        //int[] arrayOne = new int[]{1,2,6,0,0};
        //int[] arrayOne = new int[]{1,2,3,0,0};
        int[] arrayOne = new int[]{1,7,8,0,0};
        int m = 3;
        int[] arrayTwo = new int[]{4,5};
        int n = 2;

        TwoOrderArrayMergeOneArray bean = new TwoOrderArrayMergeOneArray();
        //bean.twoOrderArrayMergeOneArray1(arrayOne, m, arrayTwo, n);
        bean.twoOrderArrayMergeOneArray2(arrayOne, m, arrayTwo, n);
    }

    /**
     * 创建一个新数组，每次从两个数组中取一个较小的放到新的数组，至到其中一个数组的有效元素取完为止
     * 然后将另一个数组中剩余的所有元素放入新的数组中
     * @param arrayOne
     * @param m
     * @param arrayTwo
     * @param n
     */
    private void twoOrderArrayMergeOneArray1(int[] arrayOne, int m, int[] arrayTwo, int n) {
        int[] newArray = new int[m+n];
        int k1 = 0;
        int k2 = 0;
        int i = 0;

        for(;;) {
            if (k1 >= m || k2 >= n) break;
            if (arrayOne[k1] < arrayTwo[k2]) {
                newArray[i] = arrayOne[k1];
                k1++;
            } else {
                newArray[i] = arrayTwo[k2];
                k2++;
            }
            i++;
        }

        //剩余
        if (k1 < m) {
            System.arraycopy(arrayOne, k1, newArray, i, m - k1);
        } else {
            System.arraycopy(arrayTwo, k2, newArray, i, n - k2);
        }
        System.out.println(Arrays.toString(newArray));
    }

    private void twoOrderArrayMergeOneArray2(int[] arrayOne, int m, int[] arrayTwo, int n) {
        //找出较长的数组，即为待返回的数组
        int[] returnArray;
        int[] pollArray;
        int validNum;
        if (arrayOne.length > m) {
            returnArray = arrayOne;
            pollArray = arrayTwo;
            validNum = m;
        } else {
            returnArray = arrayTwo;
            pollArray = arrayOne;
            validNum = n;
        }

        int findStartPos = 0;
        int findEndPos = validNum - 1;
        for (int i = 0; i < pollArray.length; i++) {
            int poll = pollArray[i];

            System.out.println("i = "+i+" findStartPos ="+findStartPos+" findEndPos ="+findEndPos+", poll ="+poll);
            //找出合适的插入位置
            int insertPos = getHalfPos(returnArray, findStartPos, findEndPos, poll);
            System.out.println(poll+" inserted pos ="+insertPos);
            System.arraycopy(returnArray, insertPos, returnArray, insertPos + 1, validNum - insertPos);
            returnArray[insertPos] = poll;
            validNum++;
            findEndPos = validNum - 1;
        }
        System.out.println(Arrays.toString(returnArray));
    }

    /**
     * 二分查找法找出合适的插入位置
     * @param arrayOne
     * @param findStartPos
     * @param findEndPos
     * @param insertedNum
     * @return
     */
    private int getHalfPos(int[] arrayOne, int findStartPos, int findEndPos, int insertedNum) {


        //找出合适的插入位置
        if (findStartPos > findEndPos) {
            return findEndPos+1;
        }

        int halfPos = (findStartPos + findEndPos) / 2;
        if (arrayOne[halfPos] < insertedNum) {
            findStartPos = halfPos + 1;
            return getHalfPos(arrayOne, findStartPos, findEndPos, insertedNum);
        } else if(arrayOne[halfPos] > insertedNum) {
            findEndPos = halfPos - 1;
            return getHalfPos(arrayOne, findStartPos, findEndPos, insertedNum);
        } else {
            //相等
            return halfPos;
        }
    }
}
