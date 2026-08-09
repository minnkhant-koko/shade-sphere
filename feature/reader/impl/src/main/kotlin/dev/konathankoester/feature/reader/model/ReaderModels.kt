package dev.konathankoester.feature.reader.model

import androidx.compose.ui.graphics.Color

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val progressFraction: Float,
    val currentChapter: String,
    val timeLeftInChapter: String,
    val coverColor: Color,
) {
    val meta get() = "$author · ${(progressFraction * 100).toInt()}%"
}

data class BookshelfUiState(
    val currentBook: Book? = null,
    val books: List<Book> = emptyList(),
)

data class Chapter(val index: Int, val title: String)

data class SearchResult(
    val chapterTitle: String,
    val snippet: String,
    val matchWord: String,
    val chapterIndex: Int,
    val offset: Int,
)
