package com.mateuszholik.passwordgenerator.ui.adapters

import android.view.View
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.mateuszholik.passwordgenerator.extensions.setIsVisibility

object BindingAdapters {

    @BindingAdapter("app:animation")
    @JvmStatic
    fun setAnimation(view: LottieAnimationView, @RawRes resource: Int) {
        view.setAnimation(resource)
    }

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun setVisibility(view: View, isVisible: Boolean) {
        view setIsVisibility isVisible
    }
}