package com.example.passwordgenerator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.ActivityPasswordsBinding

class PasswordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}