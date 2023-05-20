package com.mateuszholik.passwordgenerator.uicomponents.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewSettingsButtonBinding

class SettingsButton(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private val binding = ViewSettingsButtonBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var onClick: () -> Unit = {}

    init {
        setAttributes(attrs)
        setUpButton()
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

    private fun setUpButton() {
        binding.button.setOnClickListener { onClick() }
    }
}
