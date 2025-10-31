package com.practice.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// MVI intents
sealed interface UiIntent {
    object LoadFiles : UiIntent
    data class SelectFile(val name: String) : UiIntent
}

// UI state
data class UiState(
    val files: List<String> = emptyList(),
    val selectedFileName: String? = null,
    val selectedContent: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProblemListViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = AssetRepository(application.assets)

    private val _state = MutableStateFlow(UiState(isLoading = true))
    val state: StateFlow<UiState> = _state

    init {
        process(UiIntent.LoadFiles)
    }

    fun process(intent: UiIntent) {
        when (intent) {
            is UiIntent.LoadFiles -> loadFiles()
            is UiIntent.SelectFile -> selectFile(intent.name)
        }
    }

    private fun loadFiles() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val files = repo.listFiles()
                _state.value = _state.value.copy(files = files, isLoading = false)
            } catch (t: Throwable) {
                _state.value = _state.value.copy(isLoading = false, error = t.message ?: "Unknown")
            }
        }
    }

    private fun selectFile(name: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, selectedFileName = name, selectedContent = null)
            val content = repo.readFile(name)
            _state.value = _state.value.copy(selectedContent = content, isLoading = false)
        }
    }
}
