package com.mateuszholik.passwordgenerator.ui.adapters

import android.view.View
import androidx.annotation.RawRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

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
}