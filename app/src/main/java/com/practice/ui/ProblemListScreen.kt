package com.practice.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.nio.file.Paths

// Simple tree node representing directories and files
private class TreeNode(
    val name: String,
    val fullPath: String = "",
    val children: MutableMap<String, TreeNode> = mutableMapOf(),
    val isFile: Boolean = false
)

private fun buildTree(paths: List<String>?): List<TreeNode> {
    paths ?: return listOf()

    val root = TreeNode(name = "dummyHead")

    paths.forEach { path ->
        var curr = root
        var currFullPath = Paths.get("")

        Paths.get(path).forEach { part ->
            currFullPath = currFullPath.resolve(part.toString())
            curr = curr.children.getOrPut(part.toString()) {
                TreeNode(
                    name = part.toString(),
                    fullPath = currFullPath.toString(),
                    isFile = part.toString().endsWith(".kt")
                )
            }
        }
    }

    return root.children.values.toList()
}

@Composable
private fun FileTree(
    nodes: List<TreeNode>,
    level: Int = 0,
    expandedState: MutableState<Set<String>>,
    selectedPath: String?,
    onFileClick: (String) -> Unit
) {
    Column {
        nodes.sortedBy { it.name }.forEach { node ->
            val isExpanded = expandedState.value.contains(node.fullPath)
            val textColor = if (node.fullPath == selectedPath) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = (level * 12).dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                val indicator = when {
                    node.isFile -> " " // no arrow for files
                    isExpanded -> "▾"
                    else -> "▸"
                }

                Text(
                    text = indicator,
                    modifier = Modifier
                        .width(20.dp)
                        .clickable {
                            if (!node.isFile) {
                                expandedState.value = if (isExpanded) expandedState.value - node.fullPath else expandedState.value + node.fullPath
                            }
                        }
                )

                Text(
                    text = node.name,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (node.isFile) {
                                onFileClick(node.fullPath)
                            } else {
                                expandedState.value = if (isExpanded) expandedState.value - node.fullPath else expandedState.value + node.fullPath
                            }
                        }
                )
            }

            if (!node.isFile && isExpanded && node.children.isNotEmpty()) {
                FileTree(
                    nodes = node.children.values.toList(),
                    level = level + 1,
                    expandedState = expandedState,
                    selectedPath = selectedPath,
                    onFileClick = onFileClick
                )
            }
        }
    }
}

@Composable
fun ProblemListScreen(viewModel: ProblemListViewModel = viewModel()) {
    val state = viewModel.state.collectAsState().value

    var isExpanded by remember { mutableStateOf(false) }
    val leftWidth by animateDpAsState(targetValue = if (isExpanded) 0.dp else 240.dp)
    val expandedNodes = remember { mutableStateOf(setOf<String>()) }

    Row(modifier = Modifier.fillMaxSize()) {
        // Left column: hierarchical file list (hidden when expanded)
        if (!isExpanded) {
            Column(
                modifier = Modifier
                    .width(leftWidth)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                if (state.isLoading && state.files.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    val tree = remember(state.files) { buildTree(state.files) }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        FileTree(
                            nodes = tree,
                            expandedState = expandedNodes,
                            selectedPath = state.selectedFileName,
                            onFileClick = { path -> viewModel.process(UiIntent.SelectFile(path)) }
                        )
                    }
                }
            }
        }

        // Right column: code viewer with horizontal swipe to expand/collapse
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .pointerInput(Unit) {
                    var drag = 0f
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            drag += dragAmount.x
                        },
                        onDragEnd = {
                            if (drag < -100f) {
                                // left swipe -> expand right column
                                isExpanded = true
                            } else if (drag > 100f) {
                                // right swipe -> collapse (show left column)
                                isExpanded = false
                            }
                            drag = 0f
                        },
                        onDragCancel = {
                            drag = 0f
                        }
                    )
                }
        ) {
            if (state.isLoading && state.selectedContent == null) {
                CircularProgressIndicator()
            } else if (state.selectedContent != null) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    Text(
                        text = state.selectedContent,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text(text = state.error ?: "Select a file to view")
                }
            }
        }
    }
}
