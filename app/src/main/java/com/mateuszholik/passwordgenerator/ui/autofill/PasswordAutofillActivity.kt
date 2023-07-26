package com.mateuszholik.passwordgenerator.ui.autofill

import android.app.assist.AssistStructure
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.autofill.AutofillManager.EXTRA_ASSIST_STRUCTURE
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

        fun newIntent(context: Context, assistStructure: AssistStructure): Intent =
            Intent(context, PasswordAutofillActivity::class.java).apply {
                this.putExtra(EXTRA_ASSIST_STRUCTURE, assistStructure)
            }
    }
}
