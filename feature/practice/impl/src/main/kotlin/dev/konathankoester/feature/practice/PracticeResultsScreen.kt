package dev.konathankoester.feature.practice

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.konathankoester.design.component.StatusChip
import dev.konathankoester.design.theme.AccentLight
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.design.theme.Surface2Light
import dev.konathankoester.feature.practice.model.ResultsUiState
import dev.konathankoester.feature.practice.model.WordProgress
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PracticeResultsScreen(
    sessionId: String,
    onClose: () -> Unit,
    onPracticeAgain: () -> Unit,
    viewModel: PracticeResultsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    PracticeResultsContent(state = state, onClose = onClose, onPracticeAgain = onPracticeAgain)
}

@Composable
internal fun PracticeResultsContent(
    state: ResultsUiState,
    onClose: () -> Unit,
    onPracticeAgain: () -> Unit,
) {
    val sp = ShadeSphereTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Header — close only
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            IconButton(onClick = onClose, modifier = Modifier.size(48.dp)) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close results",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        // Scrollable body
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(sp.sp6),
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = sp.sp4, vertical = sp.sp2),
        ) {
            ScoreRing(score = state.correct, total = state.total)

            // Summary
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = "Session complete",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "${state.total} words reviewed · ${state.durationMinutes} min",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Improved words
            if (state.improved.isNotEmpty()) {
                ImprovedSection(words = state.improved)
            }
        }

        // Action buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(sp.sp2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = sp.sp4, vertical = sp.sp4),
        ) {
            if (state.missed > 0) {
                Button(
                    onClick = onPracticeAgain,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    Text(
                        text = "Practice ${state.missed} missed word${if (state.missed > 1) "s" else ""}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            TextButton(
                onClick = onClose,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                Text(
                    text = "Back to Words",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
private fun ScoreRing(score: Int, total: Int) {
    val accentColor = AccentLight
    val trackColor = Surface2Light
    val fraction = if (total > 0) score.toFloat() / total else 0f

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(168.dp),
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = size.width * 0.09f
            val inset = strokeWidth / 2f
            val arcTopLeft = Offset(inset, inset)
            val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)

            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth),
            )
            drawArc(
                color = accentColor,
                startAngle = -90f,
                sweepAngle = fraction * 360f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$score/$total",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "correct",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ImprovedSection(words: List<WordProgress>) {
    val sp = ShadeSphereTheme.spacing
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "WHAT MOVED FORWARD",
            style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = androidx.compose.ui.unit.TextUnit(
                    0.4f, androidx.compose.ui.unit.TextUnitType.Sp,
                ),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        words.forEach { progress ->
            WordProgressRow(progress = progress, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun WordProgressRow(progress: WordProgress, modifier: Modifier = Modifier) {
    val sp = ShadeSphereTheme.spacing
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .height(56.dp)
            .padding(horizontal = 0.dp),
    ) {
        Text(
            text = progress.word,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(sp.sp2),
        ) {
            StatusChip(status = progress.from)
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
            StatusChip(status = progress.to)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PracticeResultsPreview() {
    ShadeSphereTheme {
        PracticeResultsContent(
            state = previewResults,
            onClose = {},
            onPracticeAgain = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "All Correct")
@Composable
private fun PracticeResultsPerfectPreview() {
    ShadeSphereTheme {
        PracticeResultsContent(
            state = ResultsUiState(correct = 12, total = 12, durationMinutes = 3, improved = previewResults.improved),
            onClose = {},
            onPracticeAgain = {},
        )
    }
}
