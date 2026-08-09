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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.konathankoester.design.component.PracticeCard
import dev.konathankoester.design.theme.AccentLight
import dev.konathankoester.design.theme.ShadeSphereTheme
import dev.konathankoester.design.theme.Surface2Light
import dev.konathankoester.feature.practice.model.SessionUiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PracticeSessionScreen(
    sessionId: String,
    onClose: () -> Unit,
    onSessionComplete: (sessionId: String) -> Unit,
    viewModel: PracticeSessionViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) onSessionComplete(sessionId)
    }

    PracticeSessionContent(
        state = state,
        onClose = onClose,
        onSkip = viewModel::skip,
        onAnswer = viewModel::selectAnswer,
    )
}

@Composable
internal fun PracticeSessionContent(
    state: SessionUiState,
    onClose: () -> Unit,
    onSkip: () -> Unit,
    onAnswer: (Int) -> Unit,
) {
    val sp = ShadeSphereTheme.spacing
    val question = state.current ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        SessionHeader(
            currentIndex = state.currentIndex,
            total = state.questions.size,
            hasAnswered = state.hasAnswered,
            onClose = onClose,
            onSkip = onSkip,
        )

        SegmentedProgress(
            total = state.questions.size,
            filled = state.currentIndex,
            modifier = Modifier.padding(horizontal = sp.sp4),
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = sp.sp4, end = sp.sp4, bottom = 64.dp),
        ) {
            PracticeCard(
                type = question.type,
                prompt = question.prompt,
                question = question.question,
                options = question.options,
                selectedIndex = state.selectedAnswer,
                correctIndex = if (state.hasAnswered) question.correctIndex else null,
                onOptionSelected = onAnswer,
            )
        }

        SessionFooter()
    }
}

@Composable
private fun SessionHeader(
    currentIndex: Int,
    total: Int,
    hasAnswered: Boolean,
    onClose: () -> Unit,
    onSkip: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 4.dp),
    ) {
        IconButton(onClick = onClose, modifier = Modifier.size(48.dp)) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "End practice session",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        Text(
            text = "${currentIndex + 1} of $total",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        TextButton(onClick = onSkip, modifier = Modifier.height(48.dp)) {
            Text(
                text = if (hasAnswered) "Next" else "Skip",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun SegmentedProgress(
    total: Int,
    filled: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        repeat(total) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (index < filled) AccentLight else Surface2Light),
            )
        }
    }
}

@Composable
private fun SessionFooter() {
    val sp = ShadeSphereTheme.spacing
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = sp.sp4),
    ) {
        Icon(
            imageVector = Icons.Rounded.Lightbulb,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp),
        )
        Spacer(Modifier.width(sp.sp2))
        Text(
            text = "Tap an answer, or press A–D on a keyboard",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PracticeSessionIdlePreview() {
    ShadeSphereTheme {
        PracticeSessionContent(
            state = SessionUiState(questions = previewQuestions, currentIndex = 3),
            onClose = {}, onSkip = {}, onAnswer = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Answered")
@Composable
private fun PracticeSessionAnsweredPreview() {
    ShadeSphereTheme {
        PracticeSessionContent(
            state = SessionUiState(questions = previewQuestions, currentIndex = 0, selectedAnswer = 2),
            onClose = {}, onSkip = {}, onAnswer = {},
        )
    }
}
