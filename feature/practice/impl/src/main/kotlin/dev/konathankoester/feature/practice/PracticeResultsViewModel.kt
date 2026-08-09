package dev.konathankoester.feature.practice

import androidx.lifecycle.ViewModel
import dev.konathankoester.design.component.WordStatus
import dev.konathankoester.feature.practice.model.ResultsUiState
import dev.konathankoester.feature.practice.model.WordProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PracticeResultsViewModel : ViewModel() {

    private val _state = MutableStateFlow(previewResults)
    val state: StateFlow<ResultsUiState> = _state.asStateFlow()
}

internal val previewResults = ResultsUiState(
    correct = 9,
    total = 12,
    durationMinutes = 4,
    improved = listOf(
        WordProgress("ostensible", WordStatus.New, WordStatus.Learning),
        WordProgress("moor", WordStatus.New, WordStatus.Learning),
        WordProgress("unequivocal", WordStatus.Learning, WordStatus.Mastered),
    ),
)
