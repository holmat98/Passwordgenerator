package com.mateuszholik.passwordgenerator.providers

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.google.android.material.snackbar.Snackbar
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ItemSnackbarBinding
import com.mateuszholik.passwordgenerator.extensions.getAttrColor

interface SnackBarProvider {

    fun showError(message: String, activity: Activity)

    fun showSuccess(message: String, activity: Activity)
}

internal class SnackBarProviderImpl : SnackBarProvider {

    override fun showError(message: String, activity: Activity) {
        show(
            message = message,
            backgroundColorAttrResId = R.attr.colorError,
            foregroundColorAttrResId = R.attr.colorOnError,
            activity = activity
        )
    }

    override fun showSuccess(message: String, activity: Activity) {
        show(
            message = message,
            backgroundColorAttrResId = R.attr.colorPrimaryContainer,
            foregroundColorAttrResId = R.attr.colorOnPrimaryContainer,
            activity = activity
        )
    }

    private fun show(
        message: String,
        @AttrRes backgroundColorAttrResId: Int,
        @AttrRes foregroundColorAttrResId: Int,
        activity: Activity,
    ) {
        val snackBar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        )

        val binding = ItemSnackbarBinding.inflate(LayoutInflater.from(activity))

        val foregroundColor = activity.getAttrColor(foregroundColorAttrResId)
        val backgroundColor = activity.getAttrColor(backgroundColorAttrResId)

        binding.apply {
            root.setBackgroundColor(backgroundColor)
            messageTV.text = message
            messageTV.setTextColor(foregroundColor)
            closeButton.setColorFilter(foregroundColor)
            closeButton.setOnClickListener { snackBar.dismiss() }
        }

        (snackBar.view as? Snackbar.SnackbarLayout)?.run {
            setPadding(
                SNACK_BAR_CUSTOM_PADDING,
                SNACK_BAR_CUSTOM_PADDING,
                SNACK_BAR_CUSTOM_PADDING,
                SNACK_BAR_CUSTOM_PADDING
            )
            addView(binding.root)

            (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.TOP
        }

        snackBar.show()
    }

    private companion object {
        const val SNACK_BAR_CUSTOM_PADDING = 0
    }
}
