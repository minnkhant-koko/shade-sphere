package dev.konathankoester.feature.news.model

data class Article(
    val id: String,
    val title: String,
    val source: String,
    val category: String,
    val timeAgo: String,
    val snippet: String,
    val readMinutes: Int,
    val hasHeroImage: Boolean = false,
)

data class NewsFeedUiState(
    val categories: List<String> = listOf("For you", "World", "Science", "Business"),
    val selectedCategory: String = "For you",
    val leadArticle: Article? = null,
    val articles: List<Article> = emptyList(),
)

data class NewsDetailUiState(
    val article: Article? = null,
    val kicker: String = "",
    val headline: String = "",
    val byline: String = "",
    val paragraphs: List<String> = emptyList(),
)
