package com.mateuszholik.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.mateuszholik.uicomponents.databinding.ItemPinCodeElementBinding

class PinCodeElement(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private val binding = ItemPinCodeElementBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    val codeValue: String
        get() = binding.editText.text.toString()
}