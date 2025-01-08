package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
fun findMostRepeatedValue(list: List<Int>): Int? {
    if (list.isEmpty()) {
        return null // 集合为空，返回null或者其他适当的值
    }

    val frequencyMap = mutableMapOf<Int, Int>() // 用于存储每个元素的频率

    for (element in list) {
        // 更新元素的频率
        frequencyMap[element] = frequencyMap.getOrDefault(element, 0) + 1
    }

    var mostRepeatedValue: Int? = null
    var maxFrequency = 0

    for ((value, frequency) in frequencyMap) {
        if (frequency > maxFrequency) {
            maxFrequency = frequency
            mostRepeatedValue = value
        }
    }

    return mostRepeatedValue
}

@RequiresApi(Build.VERSION_CODES.N)
fun main() {
    val list = listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
    val mostRepeated = findMostRepeatedValue(list)

    if (mostRepeated != null) {
        println("重复最多的值是: $mostRepeated")
    } else {
        println("集合为空")
    }
}
