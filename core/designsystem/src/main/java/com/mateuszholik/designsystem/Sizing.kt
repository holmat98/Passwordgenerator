package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizing(
    val xxs: Dp = 12.dp,
    val xs: Dp = 16.dp,
    val s: Dp = 24.dp,
    val xm: Dp = 36.dp,
    val m: Dp = 48.dp,
    val l: Dp = 64.dp,
    val xl: Dp = 96.dp,
    val xxl: Dp = 128.dp,
    val xxxl: Dp = 192.dp,
)

internal val LocalSizing = staticCompositionLocalOf { Sizing() }

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current
