package dev.konathankoester.feature.words

import androidx.lifecycle.ViewModel
import dev.konathankoester.design.component.WordStatus
import dev.konathankoester.feature.words.model.WordEntry
import dev.konathankoester.feature.words.model.WordsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WordsDashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow(previewWordsState)
    val state: StateFlow<WordsUiState> = _state.asStateFlow()
}

internal val previewWordEntries = listOf(
    WordEntry("1", "ostensible", "adj. · stated as true but not necessarily so", WordStatus.New, "Pale Fire"),
    WordEntry("2", "unequivocal", "adj. · leaving no doubt", WordStatus.Learning, "News"),
    WordEntry("3", "moor", "verb · to secure a vessel in place", WordStatus.Mastered, "News"),
    WordEntry("4", "timetable", "noun · a schedule of departures", WordStatus.New, "News"),
)

internal val previewWordsState = WordsUiState(
    newCount = 128,
    learningCount = 46,
    masteredCount = 312,
    dueCount = 12,
    hasEnrichmentError = true,
    enrichmentErrorCount = 3,
    recentWords = previewWordEntries,
    allWords = previewWordEntries,
)
