package dev.konathankoester.design.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = AccentLight,
    onPrimary = OnAccentLight,
    primaryContainer = AccentContainerLight,
    onPrimaryContainer = AccentLight,
    secondary = Primary,
    onSecondary = PrimaryForeground,
    background = BgLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Surface2Light,
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight,
    outlineVariant = DividerLight,
    error = Destructive,
    onError = BgLight,
)

private val DarkColors = darkColorScheme(
    primary = AccentDark,
    onPrimary = OnAccentDark,
    primaryContainer = AccentContainerDark,
    onPrimaryContainer = AccentDark,
    secondary = Primary,
    onSecondary = PrimaryForeground,
    background = BgDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Surface2Dark,
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark,
    outlineVariant = DividerDark,
    error = DestructiveDark,
    onError = BgDark,
)

@Composable
fun ShadeSphereTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalRadius provides Radius(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = ShadeSphereTypography,
            content = content,
        )
    }
}

object ShadeSphereTheme {
    val spacing: Spacing @Composable get() = LocalSpacing.current
    val radius: Radius @Composable get() = LocalRadius.current
}
