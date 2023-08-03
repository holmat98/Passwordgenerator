package com.mateuszholik.passwordgenerator.ui.autofill

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mateuszholik.passwordgenerator.databinding.ActivityPasswordAutofillBinding

class PasswordAutofillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordAutofillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordAutofillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linearLayout.clipToOutline = true

        binding.toolbar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    companion object {

        fun newIntent(context: Context): Intent =
            Intent(context, PasswordAutofillActivity::class.java)
    }
}
