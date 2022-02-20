package com.mateuszholik.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mateuszholik.uicomponents.databinding.ItemPinCodeBinding

class PinCode(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    private val binding = ItemPinCodeBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private val inputs = listOf(
        binding.firstPinCodeElement,
        binding.secondPinCodeElement,
        binding.thirdPinCodeElement,
        binding.fourthPinCodeElement
    )
}