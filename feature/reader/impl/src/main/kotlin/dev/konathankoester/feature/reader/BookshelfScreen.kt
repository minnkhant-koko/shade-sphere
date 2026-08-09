package dev.konathankoester.feature.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.component.BookCoverTile
import dev.konathankoester.design.component.BottomNavItem
import dev.konathankoester.design.component.ShadeSphereAppBar
import dev.konathankoester.design.component.ShadeSphereBottomNav
import dev.konathankoester.design.theme.AccentLight
import dev.konathankoester.design.theme.Surface2Light
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.feature.reader.model.Book
import dev.konathankoester.feature.reader.model.BookshelfUiState
import org.koin.compose.viewmodel.koinViewModel

private val bottomNavItems = listOf(
    BottomNavItem("Reader", Icons.Rounded.MenuBook),
    BottomNavItem("News", Icons.Rounded.Newspaper),
    BottomNavItem("Words", Icons.Rounded.School),
)

@Composable
fun BookshelfScreen(
    onBookClick: (bookId: String) -> Unit,
    onNavSelect: (index: Int) -> Unit = {},
    viewModel: BookshelfViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    BookshelfContent(state = state, onBookClick = onBookClick, onNavSelect = onNavSelect)
}

@Composable
internal fun BookshelfContent(
    state: BookshelfUiState,
    onBookClick: (bookId: String) -> Unit,
    onNavSelect: (index: Int) -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    Scaffold(
        topBar = {
            ShadeSphereAppBar(
                title = "Library",
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Add, contentDescription = "Import book")
                    }
                },
            )
        },
        bottomBar = {
            ShadeSphereBottomNav(
                items = bottomNavItems,
                selectedIndex = 0,
                onItemSelected = onNavSelect,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(sp.sp6),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(vertical = sp.sp4),
        ) {
            // Continue Reading
            state.currentBook?.let { book ->
                ContinueReadingCard(
                    book = book,
                    onClick = { onBookClick(book.id) },
                    modifier = Modifier.padding(horizontal = sp.sp4),
                )
            }

            // Section header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = sp.sp4),
            ) {
                Text("All books", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text("${state.books.size} imported", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // Book grid — 3 columns, chunked into rows
            state.books.chunked(3).forEach { rowBooks ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(sp.sp3),
                    modifier = Modifier.padding(horizontal = sp.sp4),
                ) {
                    rowBooks.forEach { book ->
                        BookCoverTile(
                            title = book.title,
                            author = book.author,
                            meta = book.meta,
                            progressFraction = book.progressFraction,
                            coverColor = book.coverColor,
                            modifier = Modifier.weight(1f),
                            onClick = { onBookClick(book.id) },
                        )
                    }
                    // Fill empty slots in last row
                    repeat(3 - rowBooks.size) { Spacer(Modifier.weight(1f)) }
                }
            }

            Spacer(Modifier.height(sp.sp4))
        }
    }
}

@Composable
private fun ContinueReadingCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sp = ShadeSphereTheme.spacing
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sp.sp3),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(sp.sp3),
    ) {
        // Mini cover
        Box(
            modifier = Modifier
                .width(58.dp)
                .height(84.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(book.coverColor),
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
            Text("CONTINUE READING", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(book.title, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Text("${book.currentChapter} · ${book.timeLeftInChapter} left", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            // Progress track
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Surface2Light),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(book.progressFraction)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(AccentLight),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookshelfPreview() {
    ShadeSphereTheme {
        BookshelfContent(
            state = previewBookshelfState,
            onBookClick = {},
            onNavSelect = {},
        )
    }
}
