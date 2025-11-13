package com.practice.lc.arithmetic

class Leetcode43 {
    fun multiplyStrings(num1: String, num2: String): String {
        if (num1 == "0" || num2 == "0") {
            return "0"
        } 

        var current  = ArrayDeque<Int>()

        for (j in num2.length - 1 downTo 0) {
            val prior = current
            current = ArrayDeque<Int>()
            var carry = 0
            
            for (i in num1.length - 1 downTo 0) {
                var int1 = num1[i].digitToInt()
                var int2 = num2[j].digitToInt()
                val sum = int1 * int2 + carry
                var remaining = sum % 10
                carry = sum / 10
                current.addFirst(remaining)
            }
            if (carry > 0) {
                current.addFirst(carry)
            }
            for (k in 0 until num2.length - 1 - j) {
                current.addLast(0)
            }
            current = this.addString(prior, current) 
        }
        
        return buildString { 
            for (num in current) {
                append(num.toString())
            }
        }.toString()
    }

    fun addString(num1: ArrayDeque<Int>, num2: ArrayDeque<Int>): ArrayDeque<Int> {
        val maxLen = Math.max(num1.size, num2.size)
        var carry = 0
        val result = ArrayDeque<Int>()
        
        for (i in 0 until maxLen) {
            val int1 = if (num1.size - 1 - i >= 0) num1.get(num1.size - 1 - i) else 0
            val int2 = if (num2.size - 1 - i >= 0) num2.get(num2.size - 1 - i) else 0
            val sum = int1 + int2 + carry
            carry = sum / 10
            val remaining = sum % 10
            result.addFirst(remaining)
        }
        
        if (carry == 1) {
            result.addFirst(1)
        }
        return result 
    }

}