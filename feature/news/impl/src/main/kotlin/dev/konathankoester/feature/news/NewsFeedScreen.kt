package dev.konathankoester.feature.news

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.component.ArticleCard
import dev.konathankoester.design.component.BottomNavItem
import dev.konathankoester.design.component.ShadeSphereAppBar
import dev.konathankoester.design.component.ShadeSphereBottomNav
import dev.konathankoester.design.theme.AccentLight
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.feature.news.model.Article
import dev.konathankoester.feature.news.model.NewsFeedUiState
import org.koin.compose.viewmodel.koinViewModel

private val bottomNavItems = listOf(
    BottomNavItem("Reader", Icons.Rounded.MenuBook),
    BottomNavItem("News", Icons.Rounded.Newspaper),
    BottomNavItem("Words", Icons.Rounded.School),
)

@Composable
fun NewsFeedScreen(
    onArticleClick: (articleId: String) -> Unit,
    onNavSelect: (index: Int) -> Unit = {},
    viewModel: NewsFeedViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    NewsFeedContent(
        state = state,
        onArticleClick = onArticleClick,
        onNavSelect = onNavSelect,
        onCategorySelect = viewModel::selectCategory,
    )
}

@Composable
internal fun NewsFeedContent(
    state: NewsFeedUiState,
    onArticleClick: (articleId: String) -> Unit,
    onNavSelect: (index: Int) -> Unit,
    onCategorySelect: (String) -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    Scaffold(
        topBar = { ShadeSphereAppBar(title = "News") },
        bottomBar = {
            ShadeSphereBottomNav(
                items = bottomNavItems,
                selectedIndex = 1,
                onItemSelected = onNavSelect,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding),
        ) {
            // Category chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(sp.sp2),
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = sp.sp4, vertical = sp.sp3),
            ) {
                state.categories.forEach { category ->
                    CategoryChip(
                        label = category,
                        selected = category == state.selectedCategory,
                        onClick = { onCategorySelect(category) },
                    )
                }
            }

            // Lead story
            state.leadArticle?.let { article ->
                LeadStoryCard(
                    article = article,
                    onClick = { onArticleClick(article.id) },
                    modifier = Modifier.padding(horizontal = sp.sp4),
                )
                Spacer(Modifier.height(sp.sp4))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(horizontal = sp.sp4),
                )
            }

            // Article list
            state.articles.forEachIndexed { index, article ->
                ArticleCard(
                    title = article.title,
                    source = "${article.source} · ${article.timeAgo}",
                    timeAgo = "${article.readMinutes} min read",
                    onClick = { onArticleClick(article.id) },
                    modifier = Modifier.padding(horizontal = sp.sp4),
                )
                if (index < state.articles.lastIndex) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.padding(horizontal = sp.sp4),
                    )
                }
            }

            Spacer(Modifier.height(sp.sp4))
        }
    }
}

@Composable
private fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val sp = ShadeSphereTheme.spacing
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (selected) AccentLight else MaterialTheme.colorScheme.background)
            .border(
                width = 1.dp,
                color = if (selected) AccentLight else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(999.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = sp.sp3, vertical = 6.dp),
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun LeadStoryCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sp = ShadeSphereTheme.spacing
    Column(
        verticalArrangement = Arrangement.spacedBy(sp.sp3),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        // Hero image placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(132.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        )
        // Kicker
        Text(
            text = "${article.category.uppercase()} · ${article.readMinutes} min read",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        // Title
        Text(
            text = article.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = (20 * 1.3).sp,
        )
        // Snippet
        Text(
            text = article.snippet,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = (14 * 1.5).sp,
        )
        // Source row
        Text(
            text = "${article.source} · ${article.timeAgo}",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewsFeedPreview() {
    ShadeSphereTheme {
        NewsFeedContent(
            state = previewNewsFeedState,
            onArticleClick = {},
            onNavSelect = {},
            onCategorySelect = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Science Category")
@Composable
private fun NewsFeedSciencePreview() {
    ShadeSphereTheme {
        NewsFeedContent(
            state = previewNewsFeedState.copy(selectedCategory = "Science"),
            onArticleClick = {},
            onNavSelect = {},
            onCategorySelect = {},
        )
    }
}
