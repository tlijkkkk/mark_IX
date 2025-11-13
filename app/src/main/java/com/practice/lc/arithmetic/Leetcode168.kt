package com.practice.lc.arithmetic

class Leetcode168 {
    fun excelSheetColumnTitle(columnNumber: Int): String {
        val result = StringBuilder()
        var tmp = columnNumber

        while (tmp > 0) {
            var curr = (tmp - 1) % 26 
            tmp = (tmp - 1) / 26
            result.append('A' + curr)
        }

        return result.reverse().toString()
    }
}