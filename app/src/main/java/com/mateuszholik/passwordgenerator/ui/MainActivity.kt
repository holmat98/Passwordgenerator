package com.mateuszholik.passwordgenerator.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ActivityMainBinding
import com.mateuszholik.passwordgenerator.ui.models.BottomNavController

class MainActivity : AppCompatActivity(), BottomNavController {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.userFragmentContainerView)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onBottomNavVisibilityChanged(isVisible: Boolean) {
        if (::binding.isInitialized) {
            binding.bottomNavigationView.isVisible = isVisible
        }
    }
}