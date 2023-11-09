package com.example.libkotlin.util

class BinarySearch {

    /**
     * 二分查找，假设数组a中的元素是从小到大排序的
     * 查找目标值target在数组a中的位置，没有找到则返回-1
     */
    fun binarySearch(a: Array<Int>?, target: Int): Int{
        if (a.isNullOrEmpty()) return -1
        //判断a数组是从小到大排列，还是从大到小排列
        var isSmallToBig = true
        if (a[a.size - 1] < a[0]) {
            isSmallToBig = false
        }
        //i 向右移动, j 向左移动
        var i = 0 //左侧要查找的最小范围位置
        var j = a.size - 1 //右侧要查找的最大范围位置
        var m = 0 //i 和 j的中间位置，向下取整
        while (i <= j) {
            // m = (i + j) / 2 //注意： (i + j) / 2这种写法会存在问题，就是当i + j的值大于Integer.MAX_VALUE时，会得到一个负数,
            // 原因是i+j的值因为超过了Integer.MAX_VALUE，所以最高位二进制位成1了，最高位作为符号位，1表示负数，0表示正数
            // ushr 意思是无符号右移
            m = (i + j) ushr 1
            if (target < a[m]) {
                if (isSmallToBig) {
                    j = m - 1 //左移最大值的指针
                } else {
                    i = m + 1
                }
            } else if (target > a[m]) {
                if (isSmallToBig) {
                    i = m + 1 //右移最小值的指针
                } else {
                    j = m - 1
                }
            } else {
                return m //找到了
            }
        }

        return -1
    }
}

fun main() {
    //val array = arrayOf(1, 2, 3 ,4, 5, 8 ,10 ,13, 16)
    val array = arrayOf(16, 13, 10 ,8, 6, 4 ,3 ,2, 1)
    val target = -1
    var bean = BinarySearch()
    var pos = bean.binarySearch(array, target)
    println("pos =$pos")
}