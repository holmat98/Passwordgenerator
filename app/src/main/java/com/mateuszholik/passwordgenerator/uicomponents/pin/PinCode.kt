package com.mateuszholik.passwordgenerator.uicomponents.pin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewPinCodeBinding
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING

class PinCode(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = ViewPinCodeBinding.inflate(
        LayoutInflater.from(context),
        this
    )
    private val inputs = listOf(
        binding.firstPinCodeElement,
        binding.secondPinCodeElement,
        binding.thirdPinCodeElement,
        binding.fourthPinCodeElement
    )

    private var currentIndex: Int = 0

    val pin: String
        get() {
            var result = EMPTY_STRING
            inputs.forEach { result += it.value }
            return result
        }

    init {
        setAttributes(attrs)
    }

    private fun setAttributes(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.PinCode
        )

        val isPinHidden = typedArray.getBoolean(
            R.styleable.PinCode_isPinHidden,
            false
        )

        inputs.forEach { input -> input.shouldHideText = isPinHidden }

        typedArray.recycle()
    }

    fun addPinText(text: String) {
        if (currentIndex < inputs.size) {
            inputs[currentIndex++].value = text
        }
    }

    fun removeTextFromPin() {
        if (currentIndex > 0) {
            inputs[--currentIndex].value = EMPTY_STRING
        }
    }

    fun animateSuccess() {
        inputs.forEach { it.animateSuccess() }
    }

    fun animateFailure() {
        inputs.forEach { it.animateFailure() }
    }

    fun setDefaultStyle() {
        inputs.forEach { it.clearColors() }
    }
}