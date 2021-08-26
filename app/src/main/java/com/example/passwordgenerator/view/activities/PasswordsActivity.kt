package com.example.passwordgenerator.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.ActivityPasswordsBinding

class PasswordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentHost)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.createPassword, R.id.testPassword, R.id.yourPasswords))

        //setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setupWithNavController(navController)

    }
}