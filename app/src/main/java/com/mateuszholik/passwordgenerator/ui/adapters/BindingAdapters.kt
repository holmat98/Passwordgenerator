package com.mateuszholik.passwordgenerator.ui.adapters

import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.mateuszholik.data.repositories.models.PasswordDetails
import com.mateuszholik.passwordgenerator.extensions.getAttrColor
import com.mateuszholik.passwordgenerator.extensions.getAttrColorResId
import com.mateuszholik.passwordgenerator.listeners.OnSwitchChangedValueListener
import com.mateuszholik.passwordgenerator.uicomponents.progressbar.CircularProgressBar

object BindingAdapters {

    @BindingAdapter("isVisible")
    @JvmStatic
    fun setVisibility(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("setProgress")
    fun setProgress(chartView: CircularProgressBar, progress: Int) {
        chartView.animateProgress(progress)
    }

    @JvmStatic
    @BindingAdapter("onSwitchValueChanged")
    fun setOnSwitchValueChanged(
        switchCompat: SwitchCompat,
        listener: OnSwitchChangedValueListener,
    ) {
        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            listener.onValueChanged(isChecked)
        }
    }

    @JvmStatic
    @BindingAdapter("setSwitchValue")
    fun setSwitchValue(switchCompat: SwitchCompat, isChecked: Boolean) {
        switchCompat.isChecked = isChecked
    }

    @JvmStatic
    @BindingAdapter("setSecondaryColor")
    fun setSecondaryColor(circularProgressBar: CircularProgressBar, passwordDetails: PasswordDetails?) {
        passwordDetails?.let {
            circularProgressBar.secondaryColor =
                circularProgressBar.context.getAttrColor(it.passwordValidity.getAttrColorResId())
        }
    }
}
