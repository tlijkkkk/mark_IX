package com.practice.lc.arithmetic

class Leetcode415 {
    fun addStrings(num1: String, num2: String): String {
        val result = StringBuilder()
        val maxLen = Math.max(num1.length, num2.length)
        var carry = 0

        for (i in 0 until maxLen) {
            val int1 = if (num1.length - 1 - i >= 0) num1[num1.length - 1 - i].digitToInt() else 0
            val int2 = if (num2.length - 1 - i >= 0) num2[num2.length - 1 - i].digitToInt() else 0
            
            val temp = int1 + int2 + carry
            carry = temp / 10
            val remaining = temp % 10
            result.append(remaining.toString())
        }
        if (carry  == 1) result.append("1")
        return result.reverse().toString()
    }
}