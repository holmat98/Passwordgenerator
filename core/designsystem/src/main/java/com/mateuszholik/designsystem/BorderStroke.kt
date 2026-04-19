package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BorderStroke(
    val s: Dp = 1.dp,
    val m: Dp = 2.dp,
    val l: Dp = 3.dp,
)

internal val LocalBorderStroke = staticCompositionLocalOf { BorderStroke() }

val MaterialTheme.borderStroke: BorderStroke
    @Composable
    @ReadOnlyComposable
    get() = LocalBorderStroke.current
