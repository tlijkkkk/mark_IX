package com.practice.lc.arithmetic

class Leetcode8 {
    fun atoi(s: String): Int {
        val ss = s.trim()
        var i = 0
        var sign = 1
        if (ss.startsWith('-')) {
            sign = -1
            i++
        } else if (ss.startsWith('+')) {
            i++ 
        } 
        
        var num = 0
        while (i < ss.length) {
            if (ss[i] !in '0'..'9') {
                break
            }
            
            val currentDigit = ss[i].digitToInt()
            
            if (sign * num > (Int.MAX_VALUE - currentDigit) / 10) {
                return Int.MAX_VALUE
            } else if (sign * num < (Int.MIN_VALUE + currentDigit) / 10) {
                return Int.MIN_VALUE
            }
            
            num = num * 10 + currentDigit
            
            i++
        }

        return sign * num
    } 
}