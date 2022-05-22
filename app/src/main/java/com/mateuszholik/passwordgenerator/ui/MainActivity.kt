package com.mateuszholik.passwordgenerator.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ActivityMainBinding
import com.mateuszholik.passwordgenerator.factories.AppBarConfigurationFactory
import com.mateuszholik.passwordgenerator.ui.models.BottomNavController
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), BottomNavController {

    private lateinit var binding: ActivityMainBinding
    private val appBarConfigurationFactory: AppBarConfigurationFactory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.userFragmentContainerView)

        with(binding) {
            bottomNavigationView.setupWithNavController(navController)
            toolbar.setupWithNavController(navController, appBarConfigurationFactory.create())
        }
    }

    override fun onBottomNavVisibilityChanged(isVisible: Boolean) {
        if (::binding.isInitialized) {
            binding.bottomNavigationView.isVisible = isVisible
        }
    }
}