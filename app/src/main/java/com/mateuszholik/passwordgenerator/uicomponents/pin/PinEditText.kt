package com.mateuszholik.passwordgenerator.uicomponents.pin

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.mateuszholik.passwordgenerator.R

class PinEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private var defaultTextColor: Int = 0

    var value: String
        get() = text.toString()
        set(value) {
            setText(value)
        }

    init {
        isFocusable = false
        setAttributes(attrs)
    }

    private fun setAttributes(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.PinEditText
        )

        val textColor = typedArray.getColor(
            R.styleable.PinEditText_pinTextColor,
            ContextCompat.getColor(context, R.color.white)
        )

        defaultTextColor = textColor
        changeTextColor(textColor)
        changeBackground()

        typedArray.recycle()
    }

    fun changeTextColor(newColor: Int) {
        setTextColor(newColor)
    }

    private fun shake() {
        val animShake = AnimationUtils.loadAnimation(context, R.anim.shaking_anim)
        startAnimation(animShake)
    }

    private fun changeBackground(
        @DrawableRes drawableRes: Int = R.drawable.pin_background
    ) {
        val drawable = ResourcesCompat.getDrawable(
            context.resources,
            drawableRes,
            null
        )

        this.background = drawable
    }

    fun animateSuccess() {
        changeBackground(R.drawable.pin_background_success)
        setTextColor(ContextCompat.getColor(context, R.color.pin_edittext_stroke_success))
        shake()
    }

    fun animateFailure() {
        changeBackground(R.drawable.pin_background_error)
        setTextColor(ContextCompat.getColor(context, R.color.pin_edittext_stroke_error))
        shake()
    }

    fun setDefaultStyle() {
        changeBackground()
        setTextColor(defaultTextColor)
    }
}