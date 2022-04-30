package com.mateuszholik.passwordgenerator.ui.adapters

import android.util.Log
import android.view.View
import androidx.annotation.RawRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.slider.Slider
import com.mateuszholik.passwordgenerator.listeners.OnValueChangedListener
import com.mateuszholik.passwordgenerator.uicomponents.chart.ProgressChartView
import com.mateuszholik.passwordgenerator.uicomponents.checkbox.AnimatedCheckbox
import com.mateuszholik.passwordgenerator.uicomponents.expandable.ExpandableView
import kotlin.math.exp

object BindingAdapters {

    @BindingAdapter("app:animation")
    @JvmStatic
    fun setAnimation(view: LottieAnimationView, @RawRes resource: Int) {
        view.setAnimation(resource)
    }

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun setVisibility(view: View, isVisible: Boolean) {
        Log.d("TEST", "Test: $isVisible")
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter(value = ["onValueChangeListener"])
    fun setOnValueChangeListener(slider: Slider, listener: OnValueChangedListener) {
        slider.addOnChangeListener { _, value, _ ->
            listener.onValueChanged(value)
        }
    }

    @JvmStatic
    @BindingAdapter("app:changeAnimation")
    fun changeAnimation(animatedCheckbox: AnimatedCheckbox, value: Boolean) {
        animatedCheckbox.isPositive = value
    }

    @JvmStatic
    @BindingAdapter("app:setProggress")
    fun setProgress(chartView: ProgressChartView, progress: Int) {
        chartView.progress = progress
    }

    @JvmStatic
    @BindingAdapter("app:setText")
    fun setText(expandableView: ExpandableView, text: String) {
        expandableView.setText(text)
    }
}