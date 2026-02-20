package com.practice.lc

class Solution {
    fun findDuplicate(paths: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        for (path in paths) {
            val pathParts: List<String> = path.split(" ")
            for (i in 1 until pathParts.size) {
                val regex = Regex("""(.*)\((.*)\)""")
                val match = regex.find(pathParts[i])
                if (match != null) {
                    map.getOrPut(match.groupValues[2]) { mutableListOf() }
                        .add("${pathParts[0]}/${match.groupValues[1]}")
                }
            }
        }
        return map.values.filter { it.size > 1}.toList()
    }
}