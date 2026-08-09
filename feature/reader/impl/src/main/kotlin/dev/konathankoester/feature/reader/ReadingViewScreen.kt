package dev.konathankoester.feature.reader

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.TextFormat
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.delay

@Composable
fun ReadingViewScreen(
    bookId: String,
    onBack: () -> Unit,
    onWordHighlighted: (word: String) -> Unit,
    onContents: () -> Unit = {},
    onSearch: () -> Unit = {},
    onSettings: () -> Unit = {},
) {
    ReadingViewContent(
        bookTitle = "Pale Fire",
        chapterCrumb = "Pale Fire · Ch. 4",
        onBack = onBack,
        onWordHighlighted = onWordHighlighted,
        onContents = onContents,
        onSearch = onSearch,
        onSettings = onSettings,
    )
}

@Composable
internal fun ReadingViewContent(
    bookTitle: String,
    chapterCrumb: String,
    onBack: () -> Unit,
    onWordHighlighted: (word: String) -> Unit,
    onContents: () -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    var chromeVisible by remember { mutableStateOf(true) }
    var toastWord by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(toastWord) {
        if (toastWord != null) {
            delay(3000)
            toastWord = null
        }
    }

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
            // Reading Chrome (immersive — shown/hidden on tap)
            AnimatedVisibility(
                visible = chromeVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                ReadingChrome(
                    crumb = chapterCrumb,
                    onBack = onBack,
                    onContents = onContents,
                    onSearch = onSearch,
                    onSettings = onSettings,
                )
            }

            // Page content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(sp.sp4),
            ) {
                Text(
                    text = "Chapter 4",
                    fontSize = 11.sp, fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Pale Fire",
                    fontFamily = FontRead,
                    fontSize = 24.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                // Paragraph with a highlighted word
                Text(
                    text = buildAnnotatedString {
                        append("Two decades of moored instruments have produced the longest continuous record yet assembled. The decline is small but ")
                        withStyle(SpanStyle(background = HighlightLight)) {
                            append("statistically significant")
                        }
                        append(" across every mooring in the array, the team writes.")
                    },
                    fontFamily = FontRead,
                    fontSize = 18.sp,
                    lineHeight = (18 * 1.6).sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = "Whether the slowdown reflects a long cycle or a lasting shift will take another decade of the same patient measurement.",
                    fontFamily = FontRead,
                    fontSize = 18.sp,
                    lineHeight = (18 * 1.6).sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                )

                // Tap a word to highlight it
                Text(
                    text = buildAnnotatedString {
                        append("The term ")
                        withStyle(SpanStyle(background = HighlightLight)) {
                            append("ostensible")
                        }
                        append(" appears here as an example of a saved word. Tap anywhere to toggle the reading chrome.")
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
                        ) {
                            toastWord = "ostensible"
                            onWordHighlighted("ostensible")
                        },
                )

                Spacer(Modifier.height(80.dp))
            }

            // Reading Footer
            AnimatedVisibility(
                visible = chromeVisible,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it },
            ) {
                ReadingFooter(timeLeft = "18 min left in chapter", progress = "42%")
            }
        }

        // Highlight toast — overlaid at the bottom
        AnimatedVisibility(
            visible = toastWord != null,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 64.dp),
        ) {
            HighlightToast(
                word = toastWord ?: "",
                onUndo = { toastWord = null },
            )
        }
    }
}

@Composable
private fun ReadingChrome(
    crumb: String,
    onBack: () -> Unit,
    onContents: () -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 4.dp),
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back to library", tint = MaterialTheme.colorScheme.onBackground)
        }
        Text(crumb, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row {
            IconButton(onClick = onContents, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Rounded.FormatListBulleted, contentDescription = "Contents", modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onBackground)
            }
            IconButton(onClick = onSearch, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Rounded.Search, contentDescription = "Search", modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onBackground)
            }
            IconButton(onClick = onSettings, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Rounded.TextFormat, contentDescription = "Settings", modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
private fun ReadingFooter(timeLeft: String, progress: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 20.dp),
    ) {
        Text(timeLeft, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(progress, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun HighlightToast(word: String, onUndo: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF2A2A28))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = "“$word” saved to Words",
            fontSize = 14.sp,
            color = Color.White,
        )
        Spacer(Modifier.width(12.dp))
        TextButton(onClick = onUndo) {
            Text(
                text = "UNDO",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.6.sp,
                color = Color(0xFFA9C4E8),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReadingViewPreview() {
    ShadeSphereTheme {
        ReadingViewContent(
            bookTitle = "Pale Fire",
            chapterCrumb = "Pale Fire · Ch. 4",
            onBack = {}, onWordHighlighted = {}, onContents = {}, onSearch = {}, onSettings = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Chrome Hidden")
@Composable
private fun ReadingViewNoChromePreview() {
    ShadeSphereTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text("Pale Fire · Ch. 4", fontSize = 11.sp, letterSpacing = 1.2.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = buildAnnotatedString {
                        append("Two decades of moored instruments. The decline is small but ")
                        withStyle(SpanStyle(background = HighlightLight)) { append("statistically significant") }
                        append(" across every mooring.")
                    },
                    fontFamily = FontRead, fontSize = 18.sp, lineHeight = (18 * 1.6).sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
