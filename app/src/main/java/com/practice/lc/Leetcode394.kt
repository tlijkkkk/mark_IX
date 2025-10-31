package com.practice.lc

import kotlin.text.buildString
import java.lang.StringBuilder

class Leetcode394 {
    public fun decodeString(s: String): String {
        val stack = ArrayDeque<Pair<StringBuilder, Int>>()
        var decodeStrSoFar = StringBuilder()
        var digits = StringBuilder()

        for (c in s) {
            if (c in 'a'..'z') {
                decodeStrSoFar.append(c)
                continue
            }

            if (c in '0'..'9') {
                digits.append(c)
                continue
            }

            if (c == '[') {
                stack.addLast(Pair<StringBuilder, Int>(decodeStrSoFar, digits.toString().toInt()))
                decodeStrSoFar = StringBuilder()
                digits = StringBuilder()
                continue
            }

            if (c == ']') {
                val (savedSb, savedInt) = stack.removeLast()
                val tempStr = buildString {
                    for (i in 0 until savedInt) {
                        append(decodeStrSoFar)
                    }
                }
                savedSb.append(tempStr)
                decodeStrSoFar = savedSb
            }
        }

        return decodeStrSoFar.toString()
    }
}