package com.mateuszholik.passwordgenerator.uicomponents.text

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewHiddenTextBinding

class HiddenTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val binding = ViewHiddenTextBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private var isTextVisible: Boolean = false
        set(value) {
            field = value

            binding.text.text = if (value) {
                text
            } else {
                invisibleText
            }
        }

    private var invisibleText: String = ""
        set(value) {
            field = value
            if (!isTextVisible) {
                binding.text.text = value
            }
        }

    var text: String = ""
        set(value) {
            field = value
            invisibleText = value.createHiddenText()

            if (isTextVisible) {
                binding.text.text = value
            }
        }

    init {
        binding.changeTextVisibilityButton.setOnClickListener {
            isTextVisible = isTextVisible.not()

            val drawable = if (isTextVisible) {
                R.drawable.ic_invisible
            } else {
                R.drawable.ic_visible
            }

            binding.changeTextVisibilityButton.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    drawable
                )
            )
        }
        background = ContextCompat.getDrawable(context, R.drawable.rounded_outline)
    }

    private fun String.createHiddenText(): String =
        this.map { "*" }.joinToString("")
}
