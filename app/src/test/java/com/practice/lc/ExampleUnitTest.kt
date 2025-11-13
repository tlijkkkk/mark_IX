package com.practice.lc

import com.practice.lc.recursion.kt.Leetcode394
import org.junit.Test
import org.junit.Assert.*

class Leetcode394Test {
    private var leetcode394 = Leetcode394()

    @Test
    fun testHappyPath() {
        val result = this.leetcode394.decodeString("abc5[cde2[l]]")
        assertEquals("abccdellcdellcdellcdellcdell", result)
    }
}