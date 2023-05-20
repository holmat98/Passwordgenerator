package com.mateuszholik.passwordgenerator.extensions

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import com.mateuszholik.passwordgenerator.uicomponents.utils.Constants.ANIMATION_DURATION

internal fun View.animateVisibility(isVisible: Boolean) {
    val endValue = if (isVisible) 1.0f else 0.0f

    if (isVisible) {
        this.isVisible = true
    }

    ValueAnimator.ofFloat(this.alpha, endValue).apply {
        duration = ANIMATION_DURATION
        addUpdateListener {
            val animatedAlpha = it.animatedValue as Float
            alpha = animatedAlpha
        }
        addListener(
            onEnd = {
                this@animateVisibility.isVisible = isVisible
            }
        )
    }.start()
}
