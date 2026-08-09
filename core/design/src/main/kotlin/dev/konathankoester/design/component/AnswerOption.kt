package dev.konathankoester.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.konathankoester.design.theme.ShadeSphereTheme

enum class AnswerState { Idle, Selected, Correct, Incorrect }

@Composable
fun AnswerOption(
    keyLabel: String,
    text: String,
    state: AnswerState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val sp = ShadeSphereTheme.spacing
    val shape = RoundedCornerShape(12.dp)

    val containerColor = when (state) {
        AnswerState.Idle -> MaterialTheme.colorScheme.background
        AnswerState.Selected -> MaterialTheme.colorScheme.primaryContainer
        AnswerState.Correct -> MaterialTheme.colorScheme.primaryContainer
        AnswerState.Incorrect -> MaterialTheme.colorScheme.errorContainer
    }
    val borderColor = when (state) {
        AnswerState.Idle -> MaterialTheme.colorScheme.outline
        AnswerState.Selected -> MaterialTheme.colorScheme.primary
        AnswerState.Correct -> MaterialTheme.colorScheme.primary
        AnswerState.Incorrect -> MaterialTheme.colorScheme.error
    }
    val badgeBg = when (state) {
        AnswerState.Idle -> MaterialTheme.colorScheme.surfaceVariant
        AnswerState.Selected, AnswerState.Correct -> MaterialTheme.colorScheme.primaryContainer
        AnswerState.Incorrect -> MaterialTheme.colorScheme.errorContainer
    }
    val badgeFg = when (state) {
        AnswerState.Idle -> MaterialTheme.colorScheme.onSurfaceVariant
        AnswerState.Selected, AnswerState.Correct -> MaterialTheme.colorScheme.primary
        AnswerState.Incorrect -> MaterialTheme.colorScheme.error
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(containerColor)
            .border(1.dp, borderColor, shape)
            .clickable(enabled = state == AnswerState.Idle, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        // Key badge — 28×28 rounded square
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(badgeBg),
        ) {
            Text(
                text = keyLabel,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = badgeFg,
            )
        }
        Spacer(Modifier.width(sp.sp3))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4f),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AnswerOptionPreview() {
    ShadeSphereTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            AnswerOption("A", "ostensible", AnswerState.Idle)
            Spacer(Modifier.padding(top = 8.dp))
            AnswerOption("B", "obstinate", AnswerState.Selected)
            Spacer(Modifier.padding(top = 8.dp))
            AnswerOption("C", "ostentatious", AnswerState.Correct)
            Spacer(Modifier.padding(top = 8.dp))
            AnswerOption("D", "ostracised", AnswerState.Incorrect)
        }
    }
}
