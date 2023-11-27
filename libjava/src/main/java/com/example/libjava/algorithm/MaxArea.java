package com.example.libjava.algorithm;

/**
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 *
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 返回容器可以储存的最大水量。
 *
 * 说明：你不能倾斜容器。
 *
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * 示例 2：
 *
 * 输入：height = [1,1]
 * 输出：1
 */
public class MaxArea {

    public static void main(String[] args) {
        //int[] array = new int[]{1,8,6,2,5,4,8,3,7};
        int[] array = new int[]{1,1};
        maxArea(array);
    }

    /**
     * 在初始时，左右指针分别指向数组的左右两端，它们可以容纳的水量为 min⁡(1,7)∗8=8\min(1, 7) * 8 = 8min(1,7)∗8=8。
     *
     * 此时我们需要移动一个指针。移动哪一个呢？直觉告诉我们，应该移动对应数字较小的那个指针（即此时的左指针）。这是因为，由于容纳的水量是由
     *
     * 两个指针指向的数字中较小值∗指针之间的距离两个指针指向的数字中较小值 * 指针之间的距离
     * 两个指针指向的数字中较小值∗指针之间的距离
     * 决定的。如果我们移动数字较大的那个指针，那么前者「两个指针指向的数字中较小值」不会增加，后者「指针之间的距离」会减小，那么这个乘积会减小。因此，我们移动数字较大的那个指针是不合理的。因此，我们移动 数字较小的那个指针。
     *
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxArea = 0;
        while (left < right) {
            int area;
            if (height[left] < height[right]) {
                area = (right - left) * height[left];
                left++;
            } else {
                area = (right - left) * height[right];
                right--;
            }
            maxArea = area > maxArea ? area : maxArea;
        }

        System.out.println(maxArea);
        return maxArea;
    }
}
