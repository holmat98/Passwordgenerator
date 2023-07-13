package com.mateuszholik.passwordgenerator.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.ui.models.BottomNavController

abstract class BaseFragment(@LayoutRes contentLayoutRes: Int) : Fragment(contentLayoutRes) {

    abstract val isBottomNavVisible: Boolean

    private fun changeBottomNavVisibility() {
        val bottomNavController = activity as? BottomNavController
        bottomNavController?.onBottomNavVisibilityChanged(isBottomNavVisible)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeBottomNavVisibility()
    }
}
