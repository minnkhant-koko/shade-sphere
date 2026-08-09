package dev.konathankoester.feature.news

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.material.icons.rounded.TextFormat
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.theme.FontRead
import dev.konathankoester.design.theme.HighlightLight
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.feature.news.model.Article

@Composable
fun NewsDetailScreen(
    articleId: String,
    onBack: () -> Unit,
    onWordHighlighted: (word: String) -> Unit,
) {
    NewsDetailContent(
        article = previewNewsFeedState.leadArticle!!,
        onBack = onBack,
        onWordHighlighted = onWordHighlighted,
    )
}

@Composable
internal fun NewsDetailContent(
    article: Article,
    onBack: () -> Unit,
    onWordHighlighted: (word: String) -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    var chromeVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) { chromeVisible = !chromeVisible },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Article chrome
            AnimatedVisibility(
                visible = chromeVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                ArticleChrome(onBack = onBack)
            }

            // Scrollable body
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            ) {
                // Hero image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(196.dp)
                        .clip(RectangleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(sp.sp4),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = sp.sp4),
                ) {
                    // Kicker
                    Text(
                        text = "${article.category.uppercase()} · ${article.readMinutes} MIN READ",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    // Headline
                    Text(
                        text = article.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = (24 * 1.25).sp,
                    )
                    // Byline
                    Text(
                        text = "${article.source} · ${article.timeAgo}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    // Body paragraphs
                    Text(
                        text = article.snippet,
                        fontFamily = FontRead,
                        fontSize = 18.sp,
                        lineHeight = (18 * 1.6).sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = "Two decades of moored instruments have produced the longest continuous record yet assembled. The decline is small but statistically significant across every mooring in the array, the team writes.",
                        fontFamily = FontRead,
                        fontSize = 18.sp,
                        lineHeight = (18 * 1.6).sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Whether the slowdown reflects a long cycle or a lasting shift will take another decade of the same patient measurement. The ")
                            withStyle(SpanStyle(background = HighlightLight)) {
                                append("ostensible")
                            }
                            append(" consensus among researchers is cautious: this is a signal worth watching, not yet a trend worth forecasting.")
                        },
                        fontFamily = FontRead,
                        fontSize = 18.sp,
                        lineHeight = (18 * 1.6).sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                            ) { onWordHighlighted("ostensible") },
                    )
                    Text(
                        text = "The monitoring array, established in 2004, spans the Atlantic at 26°N and is the only continuous record of its kind. Researchers emphasize that a single decade of data remains insufficient to distinguish a permanent weakening from natural variability.",
                        fontFamily = FontRead,
                        fontSize = 18.sp,
                        lineHeight = (18 * 1.6).sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Spacer(Modifier.height(sp.sp8))
                }
            }
        }
    }
}

@Composable
private fun ArticleChrome(onBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 4.dp),
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
            Icon(
                Icons.Rounded.TextFormat,
                contentDescription = "Text size",
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
            Icon(
                Icons.Rounded.IosShare,
                contentDescription = "Share",
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewsDetailPreview() {
    ShadeSphereTheme {
        NewsDetailContent(
            article = previewNewsFeedState.leadArticle!!,
            onBack = {},
            onWordHighlighted = {},
        )
    }
}
