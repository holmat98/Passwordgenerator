package com.example.passwordgenerator.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.R
import com.example.passwordgenerator.ViewModel.PasswordViewModel
import kotlinx.android.synthetic.main.activity_add_password.*

class AddPasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: PasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)

        addPasswordBtn3.setOnClickListener {
            val platform: String = platformNameET2.text.toString()
            val password: String = passwordValueET.text.toString()
            if(platform.isNotEmpty() && password.isNotEmpty()){
                viewModel.insert(platformName = platform, password = password)
                Toast.makeText(this, "Added new password", Toast.LENGTH_SHORT).show()
                platformNameET2.text.clear()
                passwordValueET.text.clear()
            }
        }
    }

}