package com.mateuszholik.passwordgenerator.extensions

import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible

private const val DURATION = 400L

fun View.hide() {
    val initialWidth = measuredWidth
    ValueAnimator.ofInt(initialWidth, 0).apply {
        duration = DURATION
        addUpdateListener {
            val value = it.animatedValue as Int
            val params = layoutParams
            params.width = value
            layoutParams = params
        }
        doOnEnd {
            isVisible = false
            layoutParams.width = initialWidth
            clearAnimation()
        }
    }.start()
}

fun View.show() {
    ValueAnimator.ofInt(0, layoutParams.width).apply {
        duration = DURATION
        addUpdateListener {
            val value = it.animatedValue as Int
            val params = layoutParams
            params.width = value
            layoutParams = params
        }
        doOnStart { isVisible = true }
        doOnEnd { clearAnimation() }
    }.start()
}

fun View.changeDrawableBackgroundColor(
    @DrawableRes drawableRes: Int,
    @IdRes itemId: Int,
    newBackgroundColor: Int
) {
    val drawable = ResourcesCompat.getDrawable(
        context.resources,
        drawableRes,
        null
    )
    val layerDrawable = drawable as LayerDrawable
    val gradientDrawable =
        layerDrawable.findDrawableByLayerId(itemId) as GradientDrawable
    gradientDrawable.setColor(newBackgroundColor)

    this.background = drawable
}