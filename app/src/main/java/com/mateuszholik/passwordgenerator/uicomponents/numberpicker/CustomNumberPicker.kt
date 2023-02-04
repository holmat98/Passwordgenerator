package com.mateuszholik.passwordgenerator.uicomponents.numberpicker

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import com.mateuszholik.passwordgenerator.R

class CustomNumberPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : NumberPicker(context, attrs) {

    init {
        attrs?.let { setAttributes(it) }
    }

    private fun setAttributes(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CustomNumberPicker
        )

        val min = typedArray.getInt(R.styleable.CustomNumberPicker_min, DEFAULT_MIN_VALUE)
        val max = typedArray.getInt(R.styleable.CustomNumberPicker_max, DEFAULT_MAX_VALUE)

        minValue = min
        maxValue = max

        typedArray.recycle()
    }

    private companion object {
        const val DEFAULT_MIN_VALUE = 0
        const val DEFAULT_MAX_VALUE = 10
    }
}
