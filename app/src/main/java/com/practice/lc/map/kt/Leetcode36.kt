package com.practice.lc.map.kt

class Leetcode36 {
    fun validSudoku(board: Array<CharArray>): Boolean {
        val rows = 9
        val cols = 9

        val rowSet = mutableSetOf<Char>()
        val colSets = Array(9) { mutableSetOf<Char>()}
        val nineCells = mutableMapOf<Pair<Int, Int>, MutableSet<Char>>()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val valChar = board[row][col]
                if (valChar == '.') continue
                if (rowSet.contains(valChar)) return false
                rowSet.add(valChar)

                if (colSets[col].contains(valChar)) return false
                colSets[col].add(valChar)

                val startingCell = Pair(row / 3 * 3, col / 3 * 3)
                nineCells[startingCell] = nineCells[startingCell] ?: mutableSetOf()
                val nineCell = nineCells.getOrPut(startingCell) { mutableSetOf()}
                if (nineCell.contains(valChar)) return false
                nineCell.add(valChar)
            }
            rowSet.clear()
        }
        return true
    }
}