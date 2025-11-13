package com.practice.ui

import android.content.res.AssetManager

/**
 * Repository to access asset files.
 * @param assets AssetManager to access app assets.
 * @param basePath Base path within assets folder to look for files. If empty, it means the base assets folder itself.
 */
class AssetRepository(private val assets: AssetManager) {
    fun listFiles(basePath: String = "com/practice/lc"): List<String> {
        return try {
            val result = mutableListOf<String>()
            val stack = ArrayDeque<String>()
            stack.addLast(basePath)
            while (stack.isNotEmpty()) {
                val currentBasePath = stack.removeLast()
                val items = assets.list(currentBasePath) ?: continue
                for (item in items.reversed()) {
                    val itemPath = if (currentBasePath.isEmpty()) item else "$currentBasePath/$item"
                    val subItems = assets.list(itemPath)
                    if (!subItems.isNullOrEmpty()) {
                        // item is a directory, push to stack
                        stack.addLast(itemPath)
                    } else {
                        // item is a file, check extension
                        if (item.endsWith(".kt")) {
                            result.add(itemPath)
                        }
                    }
                }
            }
            result
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun readFileSimple(fileName: String): String {
        return try {
            assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            "Error reading file: ${e.message}"
        }
    }

    fun readFile(filePath: String): String {
        return try {
            assets.open(filePath).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            "Error reading file path: ${e.message}"
        }
    }
}