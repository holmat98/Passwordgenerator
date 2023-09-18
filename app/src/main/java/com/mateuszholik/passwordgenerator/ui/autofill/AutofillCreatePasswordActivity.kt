package com.mateuszholik.passwordgenerator.ui.autofill

import android.app.assist.AssistStructure
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.autofill.AutofillManager
import com.mateuszholik.passwordgenerator.databinding.ActivityAutofillCreatePasswordBinding
import com.mateuszholik.passwordgenerator.ui.autofill.base.BaseAutofillActivity

class AutofillCreatePasswordActivity : BaseAutofillActivity() {

    private lateinit var binding: ActivityAutofillCreatePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutofillCreatePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {

        fun newIntent(
            context: Context,
            assistStructure: AssistStructure,
            packageName: String?,
        ): Intent =
            Intent(context, AutofillCreatePasswordActivity::class.java).apply {
                putExtra(AutofillManager.EXTRA_ASSIST_STRUCTURE, assistStructure)
                putExtra(PACKAGE_NAME_KEY, packageName)
            }
    }
}
