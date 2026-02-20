package com.practice.ui

import android.content.res.AssetManager

/**
 * Repository to access asset files.
 * @param assets AssetManager to access app assets.
 * @param basePath Base path within assets folder to look for files. If empty, it means the base assets folder itself.
 */
class AssetRepository(private val assets: AssetManager) : SourceCodeRepository {
    override fun listFiles(path: String): List<String> {
        return try {
            val result = mutableListOf<String>()
            val stack = ArrayDeque<String>()
            stack.addLast(path)
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

    override fun readFile(path: String): String {
        return try {
            assets.open(path).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            "Error reading file path: ${e.message}"
        }
    }
}