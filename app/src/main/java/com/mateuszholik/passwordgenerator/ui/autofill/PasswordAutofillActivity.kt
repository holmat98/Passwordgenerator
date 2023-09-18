package com.mateuszholik.passwordgenerator.ui.autofill

import android.app.assist.AssistStructure
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.autofill.AutofillManager.EXTRA_ASSIST_STRUCTURE
import com.mateuszholik.passwordgenerator.databinding.ActivityPasswordAutofillBinding
import com.mateuszholik.passwordgenerator.ui.autofill.base.BaseAutofillActivity

class PasswordAutofillActivity : BaseAutofillActivity() {

    private lateinit var binding: ActivityPasswordAutofillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordAutofillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = null

        with(binding) {
            linearLayout.clipToOutline = true

            closeButton.setOnClickListener {
                finishWithCancel()
            }
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            assistStructure: AssistStructure,
            packageName: String?,
        ): Intent =
            Intent(context, PasswordAutofillActivity::class.java).apply {
                putExtra(EXTRA_ASSIST_STRUCTURE, assistStructure)
                putExtra(PACKAGE_NAME_KEY, packageName)
            }
    }
}
