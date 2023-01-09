package com.mateuszholik.passwordgenerator.uicomponents.checkbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mateuszholik.passwordgenerator.databinding.ViewAnimatedCheckboxBinding
import com.mateuszholik.passwordgenerator.R

class AnimatedCheckbox(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = ViewAnimatedCheckboxBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var text: String = ""
        set(value) {
            binding.textView.text = value
            field = value
        }

    var isPositive: Boolean = false
        set(value) {
            startAnimation(value)
            field = value
        }

    init {
        setAttributes(attrs)
    }

    private fun setAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AnimatedCheckbox
        )

        val text = typedArray.getString(R.styleable.AnimatedCheckbox_checkBoxText)

        binding.textView.text = text

        typedArray.recycle()
    }

    private fun startAnimation(isPositive: Boolean) {
        binding.checkMarkAnimation.apply {
            setAnimation(
                if (isPositive) {
                    R.raw.passed
                } else {
                    R.raw.failed
                }
            )
        }.playAnimation()
    }

}