package com.practice.lc.tree

import androidx.compose.ui.graphics.findFirstRoot

data class TreeNode(
    val name: String,
    val children: MutableMap<String, TreeNode>
)

class BuildPathExpandTree {
    /**
     * Builds a tree from a given path string where each segment of the path
     * represents a node in the tree. The path segments are separated by '/'.
     */
    public fun buildTree(paths: List<String>?): List<TreeNode> {
        paths ?: return mutableListOf()

        val root = TreeNode(name = "dummyHead", children = mutableMapOf())

        paths.forEach { path ->
            val segments: List<String> = path.split('/')
            var curr = root
            segments.forEach { segment ->
                curr = curr.children[segment] ?: TreeNode(name = segment, children = mutableMapOf())
            }
        }

        return root.children.values.toList()
    }
}