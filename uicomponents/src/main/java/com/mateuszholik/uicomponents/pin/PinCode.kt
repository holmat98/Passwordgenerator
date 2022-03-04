package com.mateuszholik.uicomponents.pin

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.mateuszholik.uicomponents.R
import com.mateuszholik.uicomponents.databinding.ViewPinCodeBinding

class PinCode(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = ViewPinCodeBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private var currentIndex: Int = 0

    private val inputs by lazy {
        listOf(
            binding.firstPinCodeElement,
            binding.secondPinCodeElement,
            binding.thirdPinCodeElement,
            binding.fourthPinCodeElement
        )
    }

    init {
        setAttributes(attrs)
    }

    private fun setAttributes(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.PinCode
        )

        val areItemsFocusable = typedArray.getBoolean(
            R.styleable.PinCode_areInputsFocusable,
            false
        )
        val backgroundColor = typedArray.getColor(
            R.styleable.PinCode_pinInputsBackgroundColor,
            ContextCompat.getColor(context, R.color.pin_edittext_background)
        )
        val isPinHidden = typedArray.getBoolean(
            R.styleable.PinCode_isPinHidden,
            false
        )
        val textColor = typedArray.getColor(
            R.styleable.PinCode_pinInputsTextColor,
            ContextCompat.getColor(context, R.color.white)
        )

        for (input in inputs) {
            input.apply {
                isFocusable = areItemsFocusable
                changeBackground(backgroundColor = backgroundColor)
                setTextColor(textColor)
                if (isPinHidden) {
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        }

        typedArray.recycle()
    }

    fun addPinText(text: String) {
        if (currentIndex < inputs.size) {
            inputs[currentIndex++].codeValue = text
        }
    }

    fun removeTextFromPin() {
        if (currentIndex > 0) {
            inputs[--currentIndex].codeValue = ""
        }
    }

    fun animateSuccess() {
        for (input in inputs) {
            input.animateSuccess()
        }
    }

    fun animateFailure() {
        for (input in inputs) {
            input.animateFailure()
        }
    }

    fun clear() {
        for (input in inputs) {
            input.clear()
        }
    }
}