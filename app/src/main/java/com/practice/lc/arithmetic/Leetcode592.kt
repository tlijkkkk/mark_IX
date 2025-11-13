package com.practice.lc.arithmetic

class Leetcode592 {
    data class Fraction(var numerator: Int, var denominator: Int) {
        override fun toString(): String {
            return "$numerator/$denominator"
        }
    }

    fun fractionAdditionSubtraction(expression: String): String {
        val matches = """([+,-]?)([\d]+)/([\d]+)""".toRegex().findAll(expression)

        val fractions = matches.map {
            val sign = it.groupValues[1]
            val tmp = it.groupValues[2].toInt()
            val num = if (sign == "-") -tmp else tmp 
            val denom = it.groupValues[3].toInt()
            Fraction(num, denom)
        }.toList<Fraction>()  

        if (fractions.size == 1) {
            return fractions[0].toString()
        }

        for (i in 1 until fractions.size) {
            var pre = fractions[i - 1]
            var cur = fractions[i]
            cur.numerator *= pre.denominator
            pre.numerator *= cur.denominator
            cur.denominator *= pre.denominator 
            cur.numerator += pre.numerator
            cur.apply { 
                for (k in listOf(2, 3, 5, 7, 9)) {
                    if (Math.abs(numerator) < k || Math.abs(denominator) < k) {
                        break 
                    }
                    while ((numerator % k == 0) && (denominator % k == 0)) {
                        numerator /= k
                        denominator /= k
                    }
                }
             }
        }


        return if (fractions.last().numerator == 0) "0/1" else fractions.last().toString()
    }
}