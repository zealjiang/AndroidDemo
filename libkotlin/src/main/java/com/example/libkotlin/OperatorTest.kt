package com.example.libkotlin

import android.text.TextUtils
import com.example.libkotlin.util.TimeUtils
import java.util.*
import kotlin.random.Random

fun main() {
/*    var showH = getHeight() ?:0  - 20
    //var showH = windowMetrics?.bounds?.height()?.minus(navH) ?: 0
    println("showH ="+showH)

    val arrays = arrayOf("a","b","c","","e")
    for (index in 1..arrays.size - 1) {
        println("index ="+index+"  value ="+arrays[index])
    }

    val array2 = arrayOf("a", "b" , "c")
    val index = Random.Default.nextInt(0, array2.size)
    println("index ="+index)

    val copyArray = array2.copyOf()
    copyArray[0] = "null"
    println("array2[0] ="+array2[0]+"  copyArray[0] ="+copyArray[0])*/

    testArray()
    saveArray()
    testArray()
    saveArray()
    testArray()
    saveArray()
    testArray()
    saveArray()
    testArray()
    saveArray()
    testArray()
}

fun saveArray() {
    if (!isEmpty(usedIndex)) {
        var indexArray = usedIndex!!.split("_")
        if (indexArray[indexArray.size - 1].toInt() >= 10000) {
            if (usedIndex.lastIndexOf("_") == -1) {
                usedIndex = (indexArray[indexArray.size - 1].toInt() - 10000).toString()
            } else {
                val newIndexs = usedIndex.substring(0, usedIndex.lastIndexOf("_")) + "_" + (indexArray[indexArray.size - 1].toInt() - 10000)
                usedIndex = newIndexs
            }
        }
    }
}


var usedIndex = ""
fun testArray() {
    var content = ""
    val lastHour = 8
    val array = arrayOf("a","b","c","d","e")
    //每天7点到8点之间第一次显示时采用固定文案，之后从剩下的池中任取一个，显示后，从池中移除当前显示的文案，下次从池中剩余的任取其一
    var noEmptySize = array.size
    println("usedIndex = "+usedIndex)
    if (lastHour == 7) {
        val day7ShowNum = "7_0_0"
        val sevenDateNum = day7ShowNum?.split("_")
        val isToday = TimeUtils.isToday(Date(sevenDateNum?.get(1)?.toLong() ?: 0))
        if (isToday) {
            if (sevenDateNum?.get(2)?.toInt() ?: 0 == 0) {
                content = "water"
            }
        } else {
            content = "water"
        }
        array[0] = ""
        noEmptySize--
    }

    if (content == null || content.length == 0) {
        val baseNum = 10000
        val copyArray = array.copyOf()
        if (!((usedIndex == null || usedIndex.length == 0))) {
            var listString = usedIndex!!.split("_")
            if (listString.size >= noEmptySize) {
                listString = ArrayList()
            }

            for (item in listString) {
                var pos = item.toInt()
                if (pos > baseNum) continue //如果是大于10000值，说明是一个临时值，非使用过，所以过滤掉
                copyArray[pos] = ""
            }
        }

        //最后一个加10000,为获取当前将要使用的
        try {
            val newArrays = ArrayList<String>()
            //筛选出可用的
            for (item in copyArray){
                if (!isEmpty(item)) {
                    newArrays.add(item)
                }
            }
            if (newArrays.size <= 0) {
                //如果从使用过的列表中过滤后没有可用的数据了，重新填充数据
                newArrays.addAll(array)
                for (item in array){
                    if (!isEmpty(item)) {
                        newArrays.add(item)
                    }
                }
            }
            //随机选出一个
            val index = Random.nextInt(0, newArrays.size)
            content = newArrays[index]
            //对应在原数组中的位置
            val indexInArray = array.indexOf(content)
            println("newArrays = $newArrays  noEmptySize =$noEmptySize")
            println("content = $content  index =$index  indexInArray = $indexInArray")

            //将挑选出的值做特殊处理，并暂存
            if (!isEmpty(usedIndex)) {
                var listString = usedIndex!!.split("_")
                if (listString[listString.size - 1].toInt() < baseNum) {
                    if (listString.size >= noEmptySize) {
                        usedIndex = (indexInArray+baseNum).toString()
                    } else {
                        usedIndex = usedIndex+"_"+(indexInArray+baseNum)
                    }
                }
            } else {
                usedIndex = (indexInArray+baseNum).toString()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    println("content = "+content+" usedIndex = "+ usedIndex)
}

fun isEmpty(str: CharSequence?): Boolean {
    return str == null || str.length == 0
}

fun getHeight() : Int{
    return 120
}