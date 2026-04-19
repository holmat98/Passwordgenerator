package com.mateuszholik.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface Icon {

    data class AsyncResource(@param:DrawableRes val drawableRes: Int) : Icon

    data class Resource(@param:DrawableRes val drawableRes: Int) : Icon

    data class Vector(val imageVector: ImageVector): Icon

    data class Graphic(@param:DrawableRes val drawableRes: Int) : Icon

    data class Path(val path: String) : Icon

    data class Uri(val uri: android.net.Uri) : Icon
}

object PasswordManagerIcons {

}
