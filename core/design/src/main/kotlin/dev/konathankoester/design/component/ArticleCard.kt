package dev.konathankoester.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.konathankoester.design.theme.ShadeSphereTheme

@Composable
fun ArticleCard(
    title: String,
    source: String,
    timeAgo: String,
    modifier: Modifier = Modifier,
    thumbnailContent: @Composable () -> Unit = {},
    onClick: () -> Unit = {},
) {
    val sp = ShadeSphereTheme.spacing
    val r = ShadeSphereTheme.radius
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = sp.sp3),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = source,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(sp.sp1))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3,
            )
            Spacer(Modifier.height(sp.sp2))
            Text(
                text = timeAgo,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.width(sp.sp3))
        Box(
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(r.sm))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            thumbnailContent()
        }
    }
}
