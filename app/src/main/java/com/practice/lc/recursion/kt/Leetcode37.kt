package com.practice.lc.recursion.kt

class Leetcode37 {

    fun sudokuSolver(board: Array<CharArray>): Unit {
        val rows = List(9) { mutableSetOf<Char>() }
        val cols = List(9) { mutableSetOf<Char>() }
        val nineGrids = mutableMapOf<Pair<Int, Int>, MutableSet<Char>>()

        // init to record those already on the board
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == '.') continue
                rows[row].add(board[row][col])
                cols[col].add(board[row][col])
                val p = Pair(row / 3 * 3, col / 3 * 3)
                val grid = nineGrids.getOrPut(p) { mutableSetOf() }
                grid.add(board[row][col])
            }
        }

        fun isValid(row: Int, col: Int): Boolean {
            if (rows[row].contains(board[row][col])) return false
            if (cols[col].contains(board[row][col])) return false
            val head = Pair(row / 3 * 3, col / 3 * 3)
            return nineGrids[head]?.contains(board[row][col]) != true
        }


        fun doDfs(row: Int, col: Int): Boolean {
            if (row == 9) return true // terminate case
            val nextRow = if (col == 8) row + 1 else row
            val nextCol = if (col == 8) 0 else col + 1
            val headGrid = Pair(row / 3 * 3, col / 3 * 3)

            if (board[row][col] == '.') {
                for (i in 1..9) {
                    board[row][col] = '0' + i
                    if (!isValid(row, col)) continue

                    rows[row].add(board[row][col])
                    cols[col].add(board[row][col])
                    val tmpSet = nineGrids.getOrPut(headGrid) { mutableSetOf() }
                    tmpSet.add(board[row][col])

                    if (doDfs(row = nextRow, col = nextCol)) {
                        return true
                    }

                    rows[row].remove(board[row][col])
                    cols[col].remove(board[row][col])
                    nineGrids[headGrid]!!.remove(board[row][col])
                }
                board[row][col] = '.'
                return false
            }

            return doDfs(nextRow, nextCol)
        }

        doDfs(0, 0)
    }

}