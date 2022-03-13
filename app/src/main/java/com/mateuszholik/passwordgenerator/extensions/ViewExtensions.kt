package com.mateuszholik.passwordgenerator.extensions

import android.animation.ValueAnimator
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

private const val DURATION = 400L

infix fun View.setIsVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}

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
            setIsVisibleOrGone(false)
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
        doOnStart { setIsVisibleOrGone(true) }
        doOnEnd { clearAnimation() }
    }.start()
}