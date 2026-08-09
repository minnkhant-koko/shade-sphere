package dev.konathankoester.design.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val sp1: Dp = 4.dp,
    val sp2: Dp = 8.dp,
    val sp3: Dp = 12.dp,
    val sp4: Dp = 16.dp,
    val sp6: Dp = 24.dp,
    val sp8: Dp = 32.dp,
)

data class Radius(
    val none: Dp = 0.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val pill: Dp = 999.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
val LocalRadius = staticCompositionLocalOf { Radius() }
