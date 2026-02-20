package com.practice.ui

interface SourceCodeRepository {
    fun listFiles(path: String) : List<String>
    fun readFile(path: String): String
}