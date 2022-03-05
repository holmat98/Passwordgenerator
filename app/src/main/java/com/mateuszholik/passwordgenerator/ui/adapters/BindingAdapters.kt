package com.mateuszholik.passwordgenerator.ui.adapters

import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

object BindingAdapters {

    @BindingAdapter("app:animation")
    @JvmStatic
    fun setAnimation(view: LottieAnimationView, @RawRes resource: Int) {
        view.setAnimation(resource)
    }
}