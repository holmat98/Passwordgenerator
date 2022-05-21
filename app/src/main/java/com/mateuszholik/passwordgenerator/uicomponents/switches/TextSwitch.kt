package com.mateuszholik.passwordgenerator.uicomponents.switches

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewTextSwitchBinding

class TextSwitch(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = ViewTextSwitchBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var doOnSwitchChangedValue: (Boolean) -> Unit = {}
    var switchValue: Boolean
        get() = binding.switchView.isChecked
        set(value) {
            binding.switchView.isChecked = value
        }

    init {
        setAttributes(attrs)
        setUpListener()
    }

    private fun setAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TextSwitch
        )

        val newText = typedArray.getString(R.styleable.TextSwitch_text)

        binding.textValue.text = newText

        typedArray.recycle()
    }

    fun setUpListener() {
        binding.switchView.setOnCheckedChangeListener { _, isChecked ->
            doOnSwitchChangedValue(isChecked)
        }
    }
}