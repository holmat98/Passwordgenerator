package com.mateuszholik.passwordgenerator.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.ui.models.BottomNavController

abstract class BaseFragment : Fragment() {

    abstract val isBottomNavVisible: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeBottomNavVisibility()
    }

    private fun changeBottomNavVisibility() {
        val bottomNavController = activity as? BottomNavController
        bottomNavController?.onBottomNavVisibilityChanged(isBottomNavVisible)
    }
}