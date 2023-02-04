package com.mateuszholik.passwordgenerator.ui.adapters

import android.view.View
import androidx.annotation.RawRes
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.mateuszholik.passwordgenerator.listeners.OnSwitchChangedValueListener
import com.mateuszholik.passwordgenerator.uicomponents.checkbox.AnimatedCheckbox
import com.mateuszholik.passwordgenerator.uicomponents.progressbar.CircularProgressBar

object BindingAdapters {

    @BindingAdapter("app:animation")
    @JvmStatic
    fun setAnimation(view: LottieAnimationView, @RawRes resource: Int) {
        view.setAnimation(resource)
    }

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun setVisibility(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("app:changeAnimation")
    fun changeAnimation(animatedCheckbox: AnimatedCheckbox, value: Boolean) {
        animatedCheckbox.isPositive = value
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
        listener: OnSwitchChangedValueListener
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
}
