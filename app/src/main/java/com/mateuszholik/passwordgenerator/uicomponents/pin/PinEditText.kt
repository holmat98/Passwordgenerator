package com.mateuszholik.passwordgenerator.uicomponents.pin

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.changeDrawableBackgroundColor

class PinEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private var defaultBackgroundColor: Int = 0
    private var defaultTextColor: Int = 0

    var value: String
        get() = text.toString()
        set(value) {
            setText(value)
        }

    init {
        setAttributes(attrs)
    }

    private fun setAttributes(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.PinEditText
        )

        val backgroundColor = typedArray.getColor(
            R.styleable.PinEditText_pinBackgroundColor,
            ContextCompat.getColor(context, R.color.primary)
        )
        val textColor = typedArray.getColor(
            R.styleable.PinEditText_pinTextColor,
            ContextCompat.getColor(context, R.color.white)
        )

        changeBackground(backgroundColor = backgroundColor)
        changeTextColor(textColor)

        defaultBackgroundColor = backgroundColor

        typedArray.recycle()
    }

    fun changeTextColor(newColor: Int) {
        defaultTextColor = newColor
        setTextColor(newColor)
    }

    private fun shake() {
        val animShake = AnimationUtils.loadAnimation(context, R.anim.shaking_anim)
        startAnimation(animShake)
    }

    private fun animateTextColor(@ColorRes newTextColor: Int) {
        ValueAnimator.ofArgb(
            textColors.defaultColor,
            ContextCompat.getColor(context, newTextColor)
        ).apply {
            addUpdateListener {
                val value = it.animatedValue as Int
                setTextColor(value)
            }
            duration = ANIMATION_DURATION
        }.start()
    }

    fun changeBackground(
        @DrawableRes drawableRes: Int = R.drawable.pin_background,
        backgroundColor: Int
    ) {
        this.changeDrawableBackgroundColor(
            drawableRes = drawableRes,
            itemId = R.id.pinBackground,
            newBackgroundColor = backgroundColor
        )
    }

    fun animateSuccess() {
        changeBackground(R.drawable.pin_background_success, defaultBackgroundColor)
        animateTextColor(R.color.pin_edittext_stroke_success)
        shake()
    }

    fun animateFailure() {
        changeBackground(R.drawable.pin_background_error, defaultBackgroundColor)
        animateTextColor(R.color.pin_edittext_stroke_error)
        shake()
    }

    fun setDefaultStyle() {
        changeBackground(backgroundColor = defaultBackgroundColor)
        setTextColor(defaultTextColor)
    }

    private companion object {
        private const val ANIMATION_DURATION = 200L
    }
}