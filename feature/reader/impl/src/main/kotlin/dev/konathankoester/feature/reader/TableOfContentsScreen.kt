package dev.konathankoester.feature.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.feature.reader.model.Chapter

private val previewChapters = listOf(
    Chapter(1, "Foreword"), Chapter(2, "Commentary"), Chapter(3, "Index"),
    Chapter(4, "Pale Fire"), Chapter(5, "Notes"), Chapter(6, "Appendix"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableOfContentsSheet(
    bookId: String,
    onDismiss: () -> Unit,
    onChapterClick: (chapterIndex: Int) -> Unit,
    chapters: List<Chapter> = previewChapters,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        TableOfContentsContent(chapters = chapters, onChapterClick = onChapterClick, onDismiss = onDismiss)
    }
}

@Composable
internal fun TableOfContentsContent(
    chapters: List<Chapter>,
    onChapterClick: (chapterIndex: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    Column {
        // Sheet header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = sp.sp4, vertical = sp.sp3),
        ) {
            Text("Contents", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
            IconButton(onClick = onDismiss, modifier = Modifier.size(44.dp)) {
                Icon(Icons.Rounded.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onBackground)
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        chapters.forEachIndexed { index, chapter ->
            ChapterRow(chapter = chapter, onClick = { onChapterClick(chapter.index) })
            if (index < chapters.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
        Spacer(Modifier.height(sp.sp8))
    }
}

@Composable
private fun ChapterRow(chapter: Chapter, onClick: () -> Unit) {
    val sp = ShadeSphereTheme.spacing
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = sp.sp4, vertical = sp.sp4),
    ) {
        Text(
            text = "${chapter.index}",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(28.dp),
        )
        Text(chapter.title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Preview(showBackground = true)
@Composable
private fun TableOfContentsPreview() {
    ShadeSphereTheme {
        TableOfContentsContent(chapters = previewChapters, onChapterClick = {}, onDismiss = {})
    }
}
