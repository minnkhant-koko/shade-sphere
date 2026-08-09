package dev.konathankoester.feature.news

import androidx.lifecycle.ViewModel
import dev.konathankoester.feature.news.model.Article
import dev.konathankoester.feature.news.model.NewsFeedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewsFeedViewModel : ViewModel() {
    private val _state = MutableStateFlow(previewNewsFeedState)
    val state: StateFlow<NewsFeedUiState> = _state.asStateFlow()

    fun selectCategory(category: String) {
        _state.update { it.copy(selectedCategory = category) }
    }
}

internal val previewArticles = listOf(
    Article("2", "Deep-sea sensors reveal a slower Atlantic current", "Science Daily", "Science", "2 h ago", "Two decades of moored instruments have produced the longest continuous record of the overturning circulation.", 6),
    Article("3", "Lisbon metro expands its night service to outer districts", "Público", "World", "4 h ago", "The extension brings the overnight network to seven new stations.", 3),
    Article("4", "Small-batch coffee roasters face new import tariffs", "Business Insider", "Business", "6 h ago", "Green-bean prices are already at a five-year high.", 4),
)

internal val previewNewsFeedState = NewsFeedUiState(
    leadArticle = Article("1", "Lisbon's night trains return after a decade off the timetable", "Reuters", "World", "4 h ago", "The first service left Santa Apolónia on Tuesday with every berth sold.", 4, hasHeroImage = true),
    articles = previewArticles,
)
