package com.example.libjava.search;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 *
 *
 *
 * 示例 1:
 *
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 示例 2:
 *
 * 输入: nums = [0]
 * 输出: [0]
 */
public class ZeroMove {

    public static void main(String[] args) {
        int[] array = new int[]{1,0,1,0,0,3,0,12,0};
        moveZeroes2(array);
    }

    public static void moveZeroes(int[] nums) {
        //0的最小位置
        int minPosZero = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                if (minPosZero == -1) {
                    minPosZero = i;
                } else {
                    minPosZero = Math.min(i, minPosZero);
                }
            } else {
                if (i > minPosZero && minPosZero >= 0) {
                    nums[minPosZero] = nums[i];
                    nums[i] = 0;

                    if (nums.length > minPosZero + 1 && nums[minPosZero + 1] == 0) {
                        minPosZero = Math.min(i, minPosZero + 1);
                    } else {
                        minPosZero = i;
                    }

                }
            }
        }

        //打印将0移到末尾
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }

    /**
     * 思路及解法
     *
     * 使用双指针，左指针指向当前已经处理好的序列的尾部，右指针指向待处理序列的头部。
     *
     * 右指针不断向右移动，每次右指针指向非零数，则将左右指针对应的数交换，同时左指针右移。
     *
     * 注意到以下性质：
     *
     * 左指针左边均为非零数；
     *
     * 右指针左边直到左指针处均为零。
     *
     * 因此每次交换，都是将左指针的零与右指针的非零数交换，且非零数的相对顺序并未改变。
     *
     * @param nums
     */
    public static void moveZeroes2(int[] nums) {
        //0的最小位置
        int left = 0, right = 0;

        while (right < nums.length) {
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;

                left++;
            }
            right++;
        }



        //打印将0移到末尾
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}
