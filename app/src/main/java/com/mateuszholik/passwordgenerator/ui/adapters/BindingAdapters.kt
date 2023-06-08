package com.mateuszholik.passwordgenerator.ui.adapters

import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.passwordgenerator.extensions.getAttrColor
import com.mateuszholik.passwordgenerator.extensions.getAttrColorResId
import com.mateuszholik.passwordgenerator.listeners.OnSwitchChangedValueListener
import com.mateuszholik.passwordgenerator.uicomponents.info.InfoView
import com.mateuszholik.passwordgenerator.uicomponents.progressbar.CircularProgressBar
import java.time.LocalDateTime

object BindingAdapters {

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun setVisibility(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("app:setProgress")
    fun setProgress(chartView: CircularProgressBar, progress: Int) {
        chartView.animateProgress(progress)
    }

    @JvmStatic
    @BindingAdapter("app:onSwitchValueChanged")
    fun setOnSwitchValueChanged(
        switchCompat: SwitchCompat,
        listener: OnSwitchChangedValueListener,
    ) {
        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            listener.onValueChanged(isChecked)
        }
    }

    @JvmStatic
    @BindingAdapter("app:setSwitchValue")
    fun setSwitchValue(switchCompat: SwitchCompat, isChecked: Boolean) {
        switchCompat.isChecked = isChecked
    }

    @JvmStatic
    @BindingAdapter("app:setSecondaryColor")
    fun setSecondaryColor(circularProgressBar: CircularProgressBar, passwordType: PasswordType?) {
        passwordType?.let {
            circularProgressBar.secondaryColor =
                circularProgressBar.context.getAttrColor(it.getAttrColorResId())
        }
    }
}
