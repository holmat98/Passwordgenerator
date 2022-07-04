package com.mateuszholik.passwordgenerator.uicomponents.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewSettingsButtonBinding

class SettingsButton(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = ViewSettingsButtonBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        setAttributes(attrs)
    }

    private fun setAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SettingsButton
        )

        val newText = typedArray.getString(R.styleable.SettingsButton_buttonText)

        binding.buttonText.text = newText

        typedArray.recycle()
    }
}