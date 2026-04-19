package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class TextSizing(
    val xs: TextUnit = 8.sp,
    val s: TextUnit = 12.sp,
    val m: TextUnit = 16.sp,
    val l: TextUnit = 24.sp,
    val xl: TextUnit = 30.sp,
)

internal val LocalTextSizing = staticCompositionLocalOf { TextSizing() }

val MaterialTheme.textSizing: TextSizing
    @Composable
    @ReadOnlyComposable
    get() = LocalTextSizing.current