package com.example.libkotlin.util

object SumIndex {
    fun twoSum(nums: List<Int>, target: Int): IntArray {
        for (i in 0 until nums.size - 1) {
            for (j in i + 1 until nums.size) {
                if (nums[i] + nums[j] == target) {
                    return intArrayOf(i,j)
                }
            }
        }
        return intArrayOf()
    }

    fun twoSum2(nums: List<Int>, target: Int): IntArray {
        val hashMap = HashMap<Int,Int>(nums.size)

        for (i in 0 until nums.size) {
            val value = target - nums[i]
            if (value >= 0) {
                if (!hashMap.containsKey(value)) {
                    hashMap.put(nums[i], i);
                } else {
                    //找到了
                    return intArrayOf(hashMap.get(value) ?: -1, i)
                }
            }
        }
        return intArrayOf()
    }

    private fun testTwoSum() {
        val list = listOf(1,2,3,4,5)
        var target = 5
        var intArray = SumIndex.twoSum(list, target)
        println("sum =$target, ${intArray.asList()}")

        println("------------------------")

        target = 0
        intArray = SumIndex.twoSum(list, target)
        println("sum =$target, ${intArray.asList()}")

        println("------------------------")

        target = 8
        intArray = SumIndex.twoSum(list, target)
        println("sum =$target, ${intArray.asList()}")

        println("------------------------")

        target = 20
        intArray = SumIndex.twoSum(list, target)
        println("sum =$target, ${intArray.asList()}")
    }

    fun testTwoSum2() {
        val list = listOf(1,2,3,4,5)
        var target = 5
        var intArray = SumIndex.twoSum2(list, target)
        println("sum =$target, ${intArray.asList()}")

        println("------------------------")

        target = 0
        intArray = SumIndex.twoSum2(list, target)
        println("sum =$target, ${intArray.asList()}")

        println("------------------------")

        target = 8
        intArray = SumIndex.twoSum2(list, target)
        println("sum =$target, ${intArray.asList()}")

        println("------------------------")

        target = 20
        intArray = SumIndex.twoSum2(list, target)
        println("sum =$target, ${intArray.asList()}")
    }
}

fun main() {
    SumIndex.testTwoSum2()
}
