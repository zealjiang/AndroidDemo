package com.example.libjava.search;

public class MergeTwoArray {

    public static void main(String[] args) {
        testMergeNum2IntoNums1();
    }

    public static void testMerge() {
        int[] num1 = new int[]{1,2,3};
        int[] num2 = new int[]{2,5,6};
        merge(num1, 3, num2, 3);

        num1 = new int[]{1};
        num2 = new int[]{};
        merge(num1, 1, num2, 0);

        num1 = new int[]{};
        num2 = new int[]{1};
        merge(num1, 0, num2, 1);
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] newArray = new int[m + n];
        int a = 0,b = 0;
        for (int i = 0; i < m + n; i++) {
            if (a >= m) {
                newArray[i] = nums2[b];
                b++;
                continue;
            }
            if (b >= n) {
                newArray[i] = nums1[a];
                a++;
                continue;
            }

            if (nums1[a] <= nums2[b]) {
                newArray[i] = nums1[a];
                a++;
            } else {
                newArray[i] = nums2[b];
                b++;
            }
        }

        //打印排序好的newArray
        for (int i = 0; i < newArray.length; i++) {
            System.out.println(newArray[i]);
        }
        System.out.println("====================");
    }


    //----------------------------------------------------

    public static void testMergeNum2IntoNums1() {
        int[] num1 = new int[]{2,3,4,0,0,0};
        int[] num2 = new int[]{1,5,6};
        merge(num1, 3, num2, 3);

        num1 = new int[]{1};
        num2 = new int[]{};
        merge(num1, 1, num2, 0);

        num1 = new int[]{};
        num2 = new int[]{1};
        merge(num1, 0, num2, 1);
    }

    public static void mergeNum2IntoNums1(int[] nums1, int m, int[] nums2, int n) {
        int a = 0;
        for (int i = 0; i < n; i++) {
            a = 0;
            while (a < m + n) {
                if (nums1[a] <= nums2[i]) {
                    if (nums1[a] == 0) {
                        nums1[a] = nums2[i];
                        break;
                    }
                    a++;
                } else {
                    System.arraycopy(nums1,a,nums2,a+1, nums1.length - a - 1);
                    nums1[a] = nums2[i];
                    break;
                }
            }

        }

        //打印排序好的newArray
        for (int i = 0; i < nums1.length; i++) {
            System.out.println(nums1[i]);
        }
        System.out.println("====================");
    }
}
