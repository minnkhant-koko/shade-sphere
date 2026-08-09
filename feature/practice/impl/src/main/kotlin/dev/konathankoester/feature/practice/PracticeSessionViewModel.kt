package dev.konathankoester.feature.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.konathankoester.design.component.PracticeCardType
import dev.konathankoester.feature.practice.model.PracticeQuestion
import dev.konathankoester.feature.practice.model.SessionUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PracticeSessionViewModel : ViewModel() {

    private val _state = MutableStateFlow(SessionUiState(questions = previewQuestions))
    val state: StateFlow<SessionUiState> = _state.asStateFlow()

    fun selectAnswer(index: Int) {
        if (_state.value.hasAnswered) return
        _state.update { it.copy(selectedAnswer = index) }
        // Auto-advance after showing correct/incorrect for 1.5 s
        viewModelScope.launch {
            delay(1500)
            advance()
        }
    }

    fun skip() = advance()

    private fun advance() {
        val s = _state.value
        val next = s.currentIndex + 1
        if (next >= s.questions.size) {
            _state.update { it.copy(isComplete = true) }
        } else {
            _state.update { it.copy(currentIndex = next, selectedAnswer = null) }
        }
    }
}

internal val previewQuestions = listOf(
    PracticeQuestion(
        type = PracticeCardType.DefinitionToWord,
        prompt = "stated or appearing to be true, but not necessarily so",
        question = "Which word matches this definition?",
        options = listOf("ostensible", "obstinate", "ostentatious", "ostracised"),
        correctIndex = 0,
    ),
    PracticeQuestion(
        type = PracticeCardType.WordToDefinition,
        prompt = "moor",
        question = "Which definition matches this word?",
        options = listOf("secure, berth", "cast off", "capsize", "drift"),
        correctIndex = 0,
    ),
    PracticeQuestion(
        type = PracticeCardType.SentenceFillIn,
        prompt = "The stranger's ______ reason for visiting was the weather.",
        question = "Which word fits the blank?",
        options = listOf("ostensible", "ostensibly", "ostentation", "ostensive"),
        correctIndex = 0,
    ),
    PracticeQuestion(
        type = PracticeCardType.SynonymMatch,
        prompt = "unequivocal",
        question = "Pick the closest synonym.",
        options = listOf("absolute", "ambiguous", "partial", "uncertain"),
        correctIndex = 0,
    ),
    PracticeQuestion(
        type = PracticeCardType.VerbForm,
        prompt = "begin",
        question = "Choose the past tense.",
        options = listOf("began", "begun", "beginned"),
        correctIndex = 0,
    ),
)
