package com.mateuszholik.passwordgenerator.uicomponents.warnings

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mateuszholik.passwordgenerator.databinding.ViewWarningBinding

class ErrorView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = ViewWarningBinding.inflate(
        LayoutInflater.from(context),
        this
    )
}