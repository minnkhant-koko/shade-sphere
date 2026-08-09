package dev.konathankoester.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import dev.konathankoester.design.theme.ShadeSphereTheme

// Maps to the 5 design variants in the Practice Card — Format Variants board.
enum class PracticeCardType(val label: String) {
    DefinitionToWord("DEFINITION → WORD"),
    WordToDefinition("WORD → DEFINITION"),
    SentenceFillIn("FILL THE BLANK"),
    SynonymMatch("CLOSEST SYNONYM"),
    VerbForm("VERB FORM"),
}

/**
 * Shell composable shared by all 5 practice card variants.
 *
 * @param type       determines the type badge label
 * @param prompt     the word, definition, or sentence shown as the stimulus
 * @param question   the instruction line ("Which word matches this definition?", etc.)
 * @param options    2–4 answer strings; index maps to keyboard labels A/B/C/D
 * @param selectedIndex  currently tapped option index, null if none yet
 * @param correctIndex   revealed correct answer index; null while card is unanswered
 */
@Composable
fun PracticeCard(
    type: PracticeCardType,
    prompt: String,
    question: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    selectedIndex: Int? = null,
    correctIndex: Int? = null,
    onOptionSelected: (Int) -> Unit = {},
) {
    val sp = ShadeSphereTheme.spacing
    val r = ShadeSphereTheme.radius
    val keyLabels = listOf("A", "B", "C", "D")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(r.md))
            .background(MaterialTheme.colorScheme.background)
            .padding(sp.sp6),
    ) {
        // Type badge
        Text(
            text = type.label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(sp.sp6))

        // Prompt stimulus
        Text(
            text = prompt,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(sp.sp3))

        // Instruction
        Text(
            text = question,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(sp.sp6))

        // Answer options
        options.take(4).forEachIndexed { index, text ->
            val state = when {
                correctIndex != null && index == correctIndex -> AnswerState.Correct
                correctIndex != null && index == selectedIndex && index != correctIndex -> AnswerState.Incorrect
                index == selectedIndex -> AnswerState.Selected
                else -> AnswerState.Idle
            }
            AnswerOption(
                keyLabel = keyLabels[index],
                text = text,
                state = state,
                onClick = { onOptionSelected(index) },
            )
            if (index < options.lastIndex) Spacer(Modifier.height(sp.sp2))
        }
    }
}
