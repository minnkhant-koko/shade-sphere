package dev.konathankoester.feature.words

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.component.BottomNavItem
import dev.konathankoester.design.component.ShadeSphereAppBar
import dev.konathankoester.design.component.ShadeSphereBottomNav
import dev.konathankoester.design.component.StatTile
import dev.konathankoester.design.component.WordRow
import dev.konathankoester.design.theme.AccentLight
import dev.konathankoester.design.theme.ErrorBgLight
import dev.konathankoester.design.theme.ErrorFgLight
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.feature.words.model.WordEntry
import dev.konathankoester.feature.words.model.WordsUiState
import org.koin.compose.viewmodel.koinViewModel

private val bottomNavItems = listOf(
    BottomNavItem("Reader", Icons.Rounded.MenuBook),
    BottomNavItem("News", Icons.Rounded.Newspaper),
    BottomNavItem("Words", Icons.Rounded.School),
)

@Composable
fun WordsDashboardScreen(
    onStartPractice: () -> Unit,
    onWordClick: (wordId: String) -> Unit,
    onNavSelect: (index: Int) -> Unit = {},
    viewModel: WordsDashboardViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    WordsDashboardContent(
        state = state,
        onStartPractice = onStartPractice,
        onWordClick = onWordClick,
        onNavSelect = onNavSelect,
    )
}

@Composable
internal fun WordsDashboardContent(
    state: WordsUiState,
    onStartPractice: () -> Unit,
    onWordClick: (wordId: String) -> Unit,
    onNavSelect: (index: Int) -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    Scaffold(
        topBar = { ShadeSphereAppBar(title = "Words") },
        bottomBar = {
            ShadeSphereBottomNav(
                items = bottomNavItems,
                selectedIndex = 2,
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
            // Enrichment error banner
            if (state.hasEnrichmentError) {
                EnrichmentErrorBanner(
                    count = state.enrichmentErrorCount,
                    onRetry = {},
                    modifier = Modifier.padding(horizontal = sp.sp4),
                )
            }

            // Stat tiles row
            Row(
                horizontalArrangement = Arrangement.spacedBy(sp.sp3),
                modifier = Modifier.padding(horizontal = sp.sp4),
            ) {
                StatTile(label = "New", value = state.newCount.toString(), modifier = Modifier.weight(1f))
                StatTile(label = "Learning", value = state.learningCount.toString(), modifier = Modifier.weight(1f))
                StatTile(label = "Mastered", value = state.masteredCount.toString(), modifier = Modifier.weight(1f))
            }

            // Practice CTA
            if (state.dueCount > 0) {
                Button(
                    onClick = onStartPractice,
                    colors = ButtonDefaults.buttonColors(containerColor = AccentLight),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = sp.sp4)
                        .height(52.dp),
                ) {
                    Icon(
                        Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp),
                    )
                    Text(
                        text = "Practice ${state.dueCount} due words",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            // Recent words strip
            if (state.recentWords.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(sp.sp2)) {
                    Text(
                        text = "Recently added",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = sp.sp4),
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(sp.sp2),
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = sp.sp4),
                    ) {
                        state.recentWords.forEach { entry ->
                            RecentWordChip(entry = entry, onClick = { onWordClick(entry.id) })
                        }
                    }
                }
            }

            // All words list
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = sp.sp4, vertical = sp.sp2),
                ) {
                    Text(
                        text = "All words",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = "${state.allWords.size} saved",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                state.allWords.forEach { entry ->
                    WordRow(
                        word = entry.word,
                        gloss = entry.gloss,
                        status = entry.status,
                        onClick = { onWordClick(entry.id) },
                    )
                }
            }

            Spacer(Modifier.height(sp.sp4))
        }
    }
}

@Composable
private fun EnrichmentErrorBanner(
    count: Int,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sp = ShadeSphereTheme.spacing
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ErrorBgLight)
            .padding(horizontal = sp.sp4, vertical = sp.sp3),
    ) {
        Icon(
            Icons.Rounded.Warning,
            contentDescription = null,
            tint = ErrorFgLight,
            modifier = Modifier.size(20.dp),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = sp.sp3),
        ) {
            Text(
                text = "$count words couldn't be enriched",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ErrorFgLight,
            )
            Text(
                text = "Tap Retry to try again.",
                fontSize = 13.sp,
                color = ErrorFgLight,
            )
        }
        TextButton(onClick = onRetry) {
            Text(
                text = "Retry",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = ErrorFgLight,
            )
        }
    }
}

@Composable
private fun RecentWordChip(entry: WordEntry, onClick: () -> Unit) {
    val sp = ShadeSphereTheme.spacing
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = sp.sp3, vertical = sp.sp2),
    ) {
        Text(
            text = entry.word,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = entry.source,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WordsDashboardPreview() {
    ShadeSphereTheme {
        WordsDashboardContent(
            state = previewWordsState,
            onStartPractice = {},
            onWordClick = {},
            onNavSelect = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "No Error")
@Composable
private fun WordsDashboardNoErrorPreview() {
    ShadeSphereTheme {
        WordsDashboardContent(
            state = previewWordsState.copy(hasEnrichmentError = false),
            onStartPractice = {},
            onWordClick = {},
            onNavSelect = {},
        )
    }
}
