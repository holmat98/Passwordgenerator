package com.mateuszholik.designsystem

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GoldenPrimary,
    onPrimary = GoldenOnPrimary,
    primaryContainer = GoldenPrimaryContainer,
    onPrimaryContainer = GoldenOnPrimaryContainer,

    secondary = GoldenSecondary,
    onSecondary = GoldenOnSecondary,
    secondaryContainer = GoldenSecondaryContainer,
    onSecondaryContainer = GoldenOnSecondaryContainer,

    tertiary = GoldenTertiary,
    onTertiary = GoldenOnTertiary,
    tertiaryContainer = GoldenTertiaryContainer,
    onTertiaryContainer = GoldenOnTertiaryContainer,

    error = GoldenError,
    onError = GoldenOnError,
    errorContainer = GoldenErrorContainer,
    onErrorContainer = GoldenOnErrorContainer,

    background = GoldenBackground,
    onBackground = GoldenOnBackground,

    surface = GoldenSurface,
    onSurface = GoldenOnSurface,
    surfaceVariant = GoldenSurfaceVariant,
    onSurfaceVariant = GoldenOnSurfaceVariant,

    outline = GoldenOutline
)

@Composable
fun PasswordManagerTheme(
    content: @Composable () -> Unit,
) {
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                window.statusBarColor = colorScheme.surface.toArgb()
                window.navigationBarColor = colorScheme.surface.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalSizing provides Sizing(),
        LocalBorderStroke provides BorderStroke(),
        LocalCornerRadius provides CornerRadius(),
        LocalTextSizing provides TextSizing(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
