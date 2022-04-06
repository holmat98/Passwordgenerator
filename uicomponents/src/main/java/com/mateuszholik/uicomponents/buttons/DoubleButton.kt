package com.mateuszholik.uicomponents.buttons

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.mateuszholik.uicomponents.R
import com.mateuszholik.uicomponents.databinding.ViewDoubleButtonBinding

class DoubleButton(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private val binding = ViewDoubleButtonBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        setAttributes(attrs)
    }

    var firstText: String = ""
        set(value) {
            binding.firstButton.text = value
            field = value
        }

    var secondText: String = ""
        set(value) {
            binding.secondButton.text = value
            field = value
        }

    var onFirstButtonClicked: () -> Unit = {}
    var onSecondButtonClicked: () -> Unit = {}

    private fun setAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.DoubleButton
        )

        val firstText = typedArray.getString(R.styleable.DoubleButton_firstText)
        val secondText = typedArray.getString(R.styleable.DoubleButton_secondText)
        val firstBackground =
            typedArray.getColor(R.styleable.DoubleButton_firstBackground, Color.BLACK)
        val secondBackground =
            typedArray.getColor(R.styleable.DoubleButton_secondBackground, Color.WHITE)
        val firstTextColor =
            typedArray.getColor(R.styleable.DoubleButton_firstTextColor, Color.WHITE)
        val secondTextColor =
            typedArray.getColor(R.styleable.DoubleButton_secondTextColor, Color.BLACK)

        with(binding) {
            firstButton.apply {
                text = firstText
                setBackgroundColor(firstBackground)
                setTextColor(firstTextColor)
                setOnClickListener { onFirstButtonClicked() }
            }
            secondButton.apply {
                text = secondText
                setBackgroundColor(secondBackground)
                setTextColor(secondTextColor)
                setOnClickListener { onSecondButtonClicked() }
            }
        }

        typedArray.recycle()
    }

}