package com.mateuszholik.passwordgenerator.ui

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ActivityMainBinding
import com.mateuszholik.passwordgenerator.factories.AppBarConfigurationFactory
import com.mateuszholik.passwordgenerator.ui.models.BottomNavController
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), BottomNavController {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val appBarConfigurationFactory: AppBarConfigurationFactory by inject()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.addFlags(LayoutParams.FLAG_SECURE)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        title = null

        navController = findNavController(R.id.userFragmentContainerView)

        with(binding) {
            bottomNavigationView.setupWithNavController(navController)
            toolbar.setupWithNavController(navController, appBarConfigurationFactory.create())
        }

        viewModel.shouldLogOut.observe(this) { shouldLogout ->
            if (shouldLogout) {
                logOut()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onBackFromBackground()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onGoToBackground()
    }

    private fun logOut() {
        navController.navigate(R.id.loginTransitionFragment)
    }

    override fun onBottomNavVisibilityChanged(isVisible: Boolean) {
        if (::binding.isInitialized) {
            binding.bottomNavigationView.isVisible = isVisible
        }
    }
}
