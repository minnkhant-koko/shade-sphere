package dev.konathankoester.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.konathankoester.design.theme.ChipFailedBgDark
import dev.konathankoester.design.theme.ChipFailedBgLight
import dev.konathankoester.design.theme.ChipFailedFgDark
import dev.konathankoester.design.theme.ChipFailedFgLight
import dev.konathankoester.design.theme.ChipLearningBgDark
import dev.konathankoester.design.theme.ChipLearningBgLight
import dev.konathankoester.design.theme.ChipLearningFgDark
import dev.konathankoester.design.theme.ChipLearningFgLight
import dev.konathankoester.design.theme.ChipMasteredBgDark
import dev.konathankoester.design.theme.ChipMasteredBgLight
import dev.konathankoester.design.theme.ChipMasteredFgDark
import dev.konathankoester.design.theme.ChipMasteredFgLight
import dev.konathankoester.design.theme.ChipNewBgDark
import dev.konathankoester.design.theme.ChipNewBgLight
import dev.konathankoester.design.theme.ChipNewFgDark
import dev.konathankoester.design.theme.ChipNewFgLight
import dev.konathankoester.design.theme.ShadeSphereTheme

enum class WordStatus { New, Learning, Mastered, Failed }

@Composable
fun StatusChip(
    status: WordStatus,
    modifier: Modifier = Modifier,
) {
    val dark = isSystemInDarkTheme()
    val (bg, fg, label) = when (status) {
        WordStatus.New -> Triple(
            if (dark) ChipNewBgDark else ChipNewBgLight,
            if (dark) ChipNewFgDark else ChipNewFgLight,
            "New",
        )
        WordStatus.Learning -> Triple(
            if (dark) ChipLearningBgDark else ChipLearningBgLight,
            if (dark) ChipLearningFgDark else ChipLearningFgLight,
            "Learning",
        )
        WordStatus.Mastered -> Triple(
            if (dark) ChipMasteredBgDark else ChipMasteredBgLight,
            if (dark) ChipMasteredFgDark else ChipMasteredFgLight,
            "Mastered",
        )
        WordStatus.Failed -> Triple(
            if (dark) ChipFailedBgDark else ChipFailedBgLight,
            if (dark) ChipFailedFgDark else ChipFailedFgLight,
            "Failed",
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(fg),
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            ),
            color = fg,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusChipPreview() {
    ShadeSphereTheme {
        Row(modifier = Modifier.padding(12.dp)) {
            WordStatus.entries.forEach { status ->
                StatusChip(status = status)
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}
