package com.practice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProblemListScreen(viewModel: ProblemListViewModel = viewModel()) {
    val state = viewModel.state.collectAsState().value

    Row(modifier = Modifier.fillMaxSize()) {
        // Left column: file list
        Column(modifier = Modifier.width(240.dp).fillMaxHeight().background(MaterialTheme.colorScheme.surface)) {
            if (state.isLoading && state.files.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    items(state.files) { file ->
                        val isSelected = file == state.selectedFileName
                        Text(
                            text = file,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 8.dp)
                                .clickable { viewModel.process(UiIntent.SelectFile(file)) },
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Right column: code viewer
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
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
