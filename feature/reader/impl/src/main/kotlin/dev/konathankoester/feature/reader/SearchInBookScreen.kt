package dev.konathankoester.feature.reader

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.theme.AccentLight
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.feature.reader.model.SearchResult

private val previewResults = listOf(
    SearchResult("Chapter 4", "…the ostensible reason for the visit…", "ostensible", 4, 0),
    SearchResult("Chapter 7", "…her ostensible purpose concealed…", "ostensible", 7, 0),
    SearchResult("Chapter 12", "…what appeared ostensible to the court…", "ostensible", 12, 0),
    SearchResult("Chapter 15", "…an ostensible friendship, nothing more…", "ostensible", 15, 0),
)

@Composable
fun SearchInBookScreen(
    bookId: String,
    onBack: () -> Unit,
    onResultClick: (chapterIndex: Int, offset: Int) -> Unit,
) {
    SearchInBookContent(onBack = onBack, onResultClick = onResultClick)
}

@Composable
internal fun SearchInBookContent(
    onBack: () -> Unit,
    onResultClick: (chapterIndex: Int, offset: Int) -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    var query by remember { mutableStateOf("") }
    val results = if (query.isNotBlank()) previewResults else emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = sp.sp2),
    ) {
        // Search bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
            }
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search in book…", fontSize = 15.sp) },
                singleLine = true,
                trailingIcon = {
                    if (query.isNotBlank()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Rounded.Clear, contentDescription = "Clear")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {}),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = sp.sp2),
            )
        }

        Spacer(Modifier.height(sp.sp2))

        if (query.isNotBlank() && results.isEmpty()) {
            Text(
                text = "No results for $query",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = sp.sp4, vertical = sp.sp4),
            )
        } else if (results.isNotEmpty()) {
            Text(
                text = "${results.size} results",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = sp.sp4, vertical = sp.sp2),
            )
            LazyColumn {
                items(results) { result ->
                    SearchResultRow(result = result, query = query, onClick = { onResultClick(result.chapterIndex, result.offset) })
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}

@Composable
private fun SearchResultRow(result: SearchResult, query: String, onClick: () -> Unit) {
    val sp = ShadeSphereTheme.spacing
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = sp.sp4, vertical = sp.sp3),
    ) {
        Text(result.chapterTitle, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            text = buildAnnotatedString {
                val lower = result.snippet.lowercase()
                val matchStart = lower.indexOf(query.lowercase())
                if (matchStart >= 0) {
                    append(result.snippet.substring(0, matchStart))
                    withStyle(SpanStyle(color = AccentLight, fontWeight = FontWeight.SemiBold)) {
                        append(result.snippet.substring(matchStart, matchStart + query.length))
                    }
                    append(result.snippet.substring(matchStart + query.length))
                } else {
                    append(result.snippet)
                }
            },
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchInBookPreview() {
    ShadeSphereTheme {
        SearchInBookContent(onBack = {}, onResultClick = { _, _ -> })
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "With Results")
@Composable
private fun SearchInBookResultsPreview() {
    ShadeSphereTheme {
        Column(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
            Text(
                text = "4 results",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            previewResults.forEachIndexed { i, result ->
                SearchResultRow(result = result, query = "ostensible", onClick = {})
                HorizontalDivider()
            }
        }
    }
}
