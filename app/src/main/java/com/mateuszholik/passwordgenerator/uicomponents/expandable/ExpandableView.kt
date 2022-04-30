package com.mateuszholik.passwordgenerator.uicomponents.expandable

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewExpandableViewBinding
import com.mateuszholik.passwordgenerator.extensions.rotate

class ExpandableView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ViewExpandableViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private var isExpanded = false

    init {
        setAttributes(attrs)
    }

    private fun setAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ExpandableView
        )

        val text = typedArray.getString(R.styleable.ExpandableView_expandableViewText)

        text?.let { setText(it) }

        typedArray.recycle()
    }

    fun setText(text: String) {
        binding.textTV.text = text
    }

    fun rotate() {
        binding.expandedIconIV.rotate(if (isExpanded) 90f else -90f)
    }
}