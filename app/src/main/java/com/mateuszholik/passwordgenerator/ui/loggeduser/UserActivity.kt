package com.mateuszholik.passwordgenerator.ui.loggeduser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ActivityLoggedUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoggedUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.userFragmentContainerView)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, UserActivity::class.java)
            context.startActivity(intent)
        }
    }
}