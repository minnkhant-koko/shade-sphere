package dev.konathankoester.feature.reader

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dev.konathankoester.feature.reader.model.Book
import dev.konathankoester.feature.reader.model.BookshelfUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookshelfViewModel : ViewModel() {
    private val _state = MutableStateFlow(previewBookshelfState)
    val state: StateFlow<BookshelfUiState> = _state.asStateFlow()
}

internal val previewBooks = listOf(
    Book("1", "The Master and Margarita", "Bulgakov", 0.42f, "Chapter 12", "18 min", Color(0xFFD8DCE3)),
    Book("2", "Seeing Like a State", "Scott", 0.08f, "Chapter 2", "32 min", Color(0xFFE0DACD)),
    Book("3", "The Left Hand of Darkness", "Le Guin", 1.0f, "Finished", "-", Color(0xFFD6DFD8)),
    Book("4", "Norwegian Wood", "Murakami", 0.61f, "Chapter 8", "22 min", Color(0xFFE4D7D7)),
    Book("5", "A Brief History of Time", "Hawking", 0.0f, "Not started", "-", Color(0xFFD9D9DD)),
    Book("6", "The Emigrants", "Sebald", 0.23f, "Chapter 3", "41 min", Color(0xFFDEDCD2)),
)

internal val previewBookshelfState = BookshelfUiState(
    currentBook = previewBooks.first(),
    books = previewBooks,
)
