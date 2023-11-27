package com.example.main.util

import android.util.Log

object SelectSort {

    fun test() {
        val arr = intArrayOf(5, 2, 9, 1, 3)
        selectionSort(arr)

        println("排序结果：")
        for (element in arr) {
            println(element)
            Log.d("SelectSort", element.toString())
        }
    }

    fun selectionSort(arr: IntArray) {
        val n = arr.size

        for (i in 0 until n - 1) {
            var minIndex = i

            // 寻找未排序部分的最小元素
            for (j in i + 1 until n) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }

            // 将最小元素与当前位置交换
            val temp = arr[i]
            arr[i] = arr[minIndex]
            arr[minIndex] = temp
        }
    }
}