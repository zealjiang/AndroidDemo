package com.example.main.util

import android.util.Log

object QuickSort {

    fun test() {
        val arr = intArrayOf(5,2,9,1,3)
        quickSort(arr)
        println("排序后的数组：")
        Log.d("QuickSort", "排序后的数组：")
        for (i in arr) {
            println(i)
            Log.d("QuickSort", i.toString())
        }
    }
    fun quickSort(arr: IntArray) {
        quickSort(arr, 0, arr.size - 1)
    }

    fun quickSort(arr: IntArray, low: Int, high: Int) {
        if (low < high) {
            val pivotIndex = partition(arr, low, high)
            quickSort(arr, low, pivotIndex - 1)
            quickSort(arr, pivotIndex + 1, high)
        }
    }

    fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1

        for (j in low until high) {
            if (arr[j] <= pivot) {
                i++
                swap(arr, i, j)
            }
        }

        swap(arr, i + 1, high)
        return i + 1
    }

    fun swap(arr: IntArray, i: Int, j: Int) {
        Log.d("QuickSort", "swap: i =" + i + ", j =" + j)
        if (i == j) {
            return
        }
        val temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
    }
}