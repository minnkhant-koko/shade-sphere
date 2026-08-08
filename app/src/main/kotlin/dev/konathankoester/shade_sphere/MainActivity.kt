package dev.konathankoester.shade_sphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dev.konathankoester.data.WordEnrichmentRepository
import dev.konathankoester.shade_sphere.ui.theme.ShadesphereTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    // TODO remove
    private val repository: WordEnrichmentRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO remove
        lifecycleScope.launch {
            repository.configureAiClient(BuildConfig.GEMINI_API_KEY)
            val wordId = repository.seedTestWord(
                word = "Commonplace",
                contextSentence = "Unknown to Stevenson, his experience that fall evening was remarkably reminiscent of a form of sleep that was once commonplace"
            )
            repository.enrichWord(wordId)
        }

        enableEdgeToEdge()
        setContent {
            ShadesphereTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShadesphereTheme {
        Greeting("Android")
    }
}
