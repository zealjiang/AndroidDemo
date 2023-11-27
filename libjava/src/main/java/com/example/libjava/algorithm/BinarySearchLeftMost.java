package com.example.libjava.algorithm;

public class BinarySearchLeftMost {

    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,3,5,5,6};
        int target = 3;
        int findPos = binarySearchLeftMost(array, target);
        System.out.println("target ="+target+" findPos ="+findPos);

        target = 4;
        findPos = binarySearchLeftMost(array, target);
        System.out.println("target ="+target+" findPos ="+findPos);
    }

    /**
     *
     * @param a 待查找的升序数组
     * @param target 待查找的目标值
     * @return 返回>= target的最靠左索引
     */
    public static int binarySearchLeftMost(int[] a, int target) {
        int i = 0, j = a.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target <= a[m]) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }

        return i;
    }
}
