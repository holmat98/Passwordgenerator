package com.mateuszholik.passwordgenerator.ui.autofill.createpassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentAutofillCreatePasswordBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import com.mateuszholik.passwordgenerator.ui.autofill.base.AutofillController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AutofillCreatePasswordFragment : Fragment(R.layout.fragment_autofill_create_password) {

    private val binding by viewBinding(FragmentAutofillCreatePasswordBinding::bind)
    private val viewModel: AutofillCreatePasswordViewModel by viewModel {
        parametersOf((activity as? AutofillController)?.getAutofillPackageName())
    }
    private val snackBarProvider: SnackBarProvider by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@AutofillCreatePasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        with(viewModel) {
            savePasswordSuccess.observe(viewLifecycleOwner) {
                val autofillController = activity as? AutofillController

                autofillController?.finishWithSuccess(
                    promptMessage = it.promptMessage,
                    autofillValue = it.autofillValue
                )
            }
            errorOccurred.observe(viewLifecycleOwner) { errorMessage ->
                activity?.let { snackBarProvider.showError(errorMessage, it) }
            }
        }
    }
}
