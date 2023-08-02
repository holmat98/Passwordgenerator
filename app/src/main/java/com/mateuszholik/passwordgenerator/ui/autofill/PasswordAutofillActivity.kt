package com.mateuszholik.passwordgenerator.ui.autofill

import android.app.Activity
import android.app.assist.AssistStructure
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.autofill.AutofillManager.EXTRA_ASSIST_STRUCTURE
import android.view.autofill.AutofillManager.EXTRA_AUTHENTICATION_RESULT
import androidx.appcompat.app.AppCompatActivity
import com.mateuszholik.passwordgenerator.autofill.builders.FillResponseBuilder
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser
import com.mateuszholik.passwordgenerator.databinding.ActivityPasswordAutofillBinding
import com.mateuszholik.passwordgenerator.extensions.fromParcelable
import org.koin.android.ext.android.inject

interface AutofillController {

    fun getAutofillPackageName(): String?

    fun finishWithSuccess(promptMessage: String, autofillValue: String)

    fun finishWithCancel()
}

class PasswordAutofillActivity : AppCompatActivity(), AutofillController {

    private lateinit var binding: ActivityPasswordAutofillBinding
    private val fillResponseBuilder: FillResponseBuilder by inject()
    private val structureParser: StructureParser by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordAutofillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = null

        with(binding) {
            linearLayout.clipToOutline = true

            closeButton.setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    override fun getAutofillPackageName(): String? =
        intent.extras?.getString(PACKAGE_NAME_KEY)

    override fun finishWithSuccess(promptMessage: String, autofillValue: String) {
        val structure: AssistStructure? = intent.fromParcelable(EXTRA_ASSIST_STRUCTURE)
        val parsedStructure = structure?.let { structureParser.parse(it) }

        parsedStructure?.let {
            val fillResponse = fillResponseBuilder.addDataset(
                autofillId = it.autofillId,
                promptMessage = promptMessage,
                autofillValue = autofillValue,
                packageName = packageName
            ).build()

            val replyIntent = Intent().apply {
                putExtra(EXTRA_AUTHENTICATION_RESULT, fillResponse)
            }

            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    override fun finishWithCancel() {
        setResult(RESULT_CANCELED)
        finish()
    }

    companion object {

        private const val PACKAGE_NAME_KEY = "PACKAGE_NAME_KEY"

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
