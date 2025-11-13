package com.practice.lc.map.kt

class Leetcode1679 {
    fun maxNumKSumPairs(nums: IntArray, k: Int): Int {
        val map = mutableMapOf<Int, Int>()
        var count = 0

        for (num in nums) {
            val v = (map[k - num] ?: 0) - 1
            if (v == -1) {
                map[num] = (map[num] ?: 0) + 1
                continue
            }
            count++
            if (v == 0) {
                map.remove(k - num)
                continue
            }
            map[k - num] = v
        }
        return count
    }
}