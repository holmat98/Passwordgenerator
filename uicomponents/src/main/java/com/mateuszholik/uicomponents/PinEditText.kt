package com.mateuszholik.uicomponents

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class PinEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private var defaultBackgroundColor: Int = 0
    private var defaultTextColor: Int = 0

    var codeValue: String
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
            ContextCompat.getColor(context, R.color.pin_edittext_background)
        )
        val textColor = typedArray.getColor(
            R.styleable.PinEditText_pinTextColor,
            ContextCompat.getColor(context, R.color.white)
        )

        changeBackground(backgroundColor = backgroundColor)
        setTextColor(textColor)

        defaultBackgroundColor = backgroundColor
        defaultTextColor = textColor

        typedArray.recycle()
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
        val drawable = ResourcesCompat.getDrawable(
            context.resources,
            drawableRes,
            null
        )
        val layerDrawable = drawable as LayerDrawable
        val gradientDrawable =
            layerDrawable.findDrawableByLayerId(R.id.pinBackground) as GradientDrawable
        gradientDrawable.setColor(backgroundColor)

        this.background = drawable
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

    fun clear() {
        changeBackground(backgroundColor = defaultBackgroundColor)
        setTextColor(defaultTextColor)
    }

    private companion object {
        private const val ANIMATION_DURATION = 200L
    }
}