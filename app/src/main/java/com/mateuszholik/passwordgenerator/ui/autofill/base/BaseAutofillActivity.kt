package com.mateuszholik.passwordgenerator.ui.autofill.base

import android.app.assist.AssistStructure
import android.content.Intent
import android.view.autofill.AutofillManager
import androidx.appcompat.app.AppCompatActivity
import com.mateuszholik.passwordgenerator.autofill.factories.DatasetFactory
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser
import com.mateuszholik.passwordgenerator.extensions.fromParcelable
import org.koin.android.ext.android.inject

interface AutofillController {

    fun getAutofillPackageName(): String?

    fun finishWithSuccess(promptMessage: String, autofillValue: String)

    fun finishWithCancel()
}

abstract class BaseAutofillActivity : AppCompatActivity(), AutofillController {

    private val datasetFactory: DatasetFactory by inject()
    private val structureParser: StructureParser by inject()

    override fun getAutofillPackageName(): String? =
        intent.extras?.getString(PACKAGE_NAME_KEY)

    override fun finishWithSuccess(promptMessage: String, autofillValue: String) {
        val structure: AssistStructure? =
            intent.fromParcelable(AutofillManager.EXTRA_ASSIST_STRUCTURE)
        val parsedStructure = structure?.let { structureParser.parse(it) }

        parsedStructure?.let {
            val dataset = datasetFactory.create(
                autofillId = it.autofillId,
                promptMessage = promptMessage,
                autofillValue = autofillValue,
                packageName = packageName
            )

            val replyIntent = Intent().apply {
                putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, dataset)
            }

            setResult(RESULT_OK, replyIntent)
            finish()
        }
    }

    override fun finishWithCancel() {
        setResult(RESULT_CANCELED)
        finish()
    }

    companion object {
        const val PACKAGE_NAME_KEY = "PACKAGE_NAME_KEY"
    }
}
