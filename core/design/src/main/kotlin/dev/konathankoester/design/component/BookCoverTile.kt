package dev.konathankoester.design.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.konathankoester.design.theme.AccentLight
import dev.konathankoester.design.theme.DividerLight
import dev.konathankoester.design.theme.FontRead
import dev.konathankoester.design.theme.Surface2Light
import dev.konathankoester.design.theme.ShadeSphereTheme

@Composable
fun BookCoverTile(
    title: String,
    author: String,
    meta: String,
    progressFraction: Float,
    coverColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val sp = ShadeSphereTheme.spacing
    Column(
        verticalArrangement = Arrangement.spacedBy(sp.sp2),
        modifier = modifier.clickable(onClick = onClick),
    ) {
        // Cover — title + author overlaid at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(168.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(coverColor)
                .border(1.dp, DividerLight, RoundedCornerShape(4.dp)),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
            ) {
                Text(
                    text = title,
                    fontFamily = FontRead,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = (11 * 1.25).sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = author,
                    fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        // Meta below cover
        Text(
            text = meta,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        // Progress track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Surface2Light),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progressFraction.coerceIn(0f, 1f))
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(AccentLight),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookCoverTilePreview() {
    ShadeSphereTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            BookCoverTile(
                title = "Pale Fire",
                author = "Nabokov",
                meta = "Nabokov · 42%",
                progressFraction = 0.42f,
                coverColor = Color(0xFFD8DCE3),
                modifier = Modifier.weight(1f),
            )
            BookCoverTile(
                title = "Seeing Like a State",
                author = "Scott",
                meta = "Scott · 8%",
                progressFraction = 0.08f,
                coverColor = Color(0xFFE0DACD),
                modifier = Modifier.weight(1f),
            )
        }
    }
}
