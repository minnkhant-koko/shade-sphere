package dev.konathankoester.feature.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.theme.FontRead
import dev.konathankoester.design.theme.ShadeSphereTheme

private enum class TypefacePick(val label: String, val family: FontFamily) {
    Literata("Literata", FontRead),
    Inter("Inter", FontFamily.SansSerif),
    Atkinson("Atkinson", FontFamily.Default),
}

private enum class SpacingPick(val label: String) { Compact("Compact"), Normal("Normal"), Loose("Loose") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingSettingsSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        ReadingSettingsContent(onDismiss = onDismiss)
    }
}

@Composable
internal fun ReadingSettingsContent(onDismiss: () -> Unit) {
    val sp = ShadeSphereTheme.spacing
    var typeface by remember { mutableStateOf(TypefacePick.Literata) }
    var fontSize by remember { mutableIntStateOf(18) }
    var spacing by remember { mutableStateOf(SpacingPick.Normal) }

    Column {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = sp.sp4, vertical = sp.sp3),
        ) {
            Text("Display", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
            IconButton(onClick = onDismiss, modifier = Modifier.size(44.dp)) {
                Icon(Icons.Rounded.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onBackground)
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        Column(
            verticalArrangement = Arrangement.spacedBy(sp.sp6),
            modifier = Modifier.padding(horizontal = sp.sp4, vertical = sp.sp4),
        ) {
            // Typeface
            SettingsSection(label = "Typeface") {
                Row(horizontalArrangement = Arrangement.spacedBy(sp.sp2), modifier = Modifier.fillMaxWidth()) {
                    TypefacePick.entries.forEach { pick ->
                        val selected = typeface == pick
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                                .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                .clickable { typeface = pick },
                        ) {
                            Text(pick.label, fontFamily = pick.family, fontSize = 14.sp, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }

            // Font size stepper
            SettingsSection(label = "Text Size") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    IconButton(onClick = { if (fontSize > 12) fontSize-- }, modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface)) {
                        Icon(Icons.Rounded.Remove, contentDescription = "Decrease", tint = MaterialTheme.colorScheme.onSurface)
                    }
                    Text("$fontSize pt", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                    IconButton(onClick = { if (fontSize < 28) fontSize++ }, modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface)) {
                        Icon(Icons.Rounded.Add, contentDescription = "Increase", tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            // Spacing
            SettingsSection(label = "Spacing") {
                Row(horizontalArrangement = Arrangement.spacedBy(sp.sp2), modifier = Modifier.fillMaxWidth()) {
                    SpacingPick.entries.forEach { pick ->
                        val selected = spacing == pick
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                                .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                .clickable { spacing = pick },
                        ) {
                            Text(pick.label, fontSize = 14.sp, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(sp.sp8))
    }
}

@Composable
private fun SettingsSection(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSettingsPreview() {
    ShadeSphereTheme { ReadingSettingsContent(onDismiss = {}) }
}
