package com.mateuszholik.passwordgenerator.ui.autofill.selectpassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentSelectPasswordBinding
import com.mateuszholik.passwordgenerator.extensions.showDialog
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.autofill.base.AutofillController
import com.mateuszholik.passwordgenerator.ui.autofill.selectpassword.adapter.SelectPasswordAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectPasswordFragment : Fragment(R.layout.fragment_select_password) {

    private val binding by viewBinding(FragmentSelectPasswordBinding::bind)
    private val viewModel: SelectPasswordViewModel by viewModel()
    private var adapter: SelectPasswordAdapter? = null
    private var selectedPassword: AutofillPasswordDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPasswords()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.confirmSelectedPasswordButton.setOnClickListener {
            selectedPassword?.let { password ->
                val autofillController = activity as? AutofillController
                val packageName = autofillController?.getAutofillPackageName()

                if (packageName != null && password.packageName.isNullOrEmpty()) {
                    showDialog(
                        titleRes = R.string.autofill_dialog_save_package_name_title,
                        messageRes = R.string.autofill_dialog_save_package_name_message,
                        negativeButtonRes = R.string.dialog_button_no,
                        positiveButtonRes = R.string.dialog_button_yes,
                        doOnNegativeButton = { autofillData() },
                        doOnPositiveButton = {
                            viewModel.updatePackageName(
                                password.id,
                                packageName
                            )
                        }
                    )
                } else {
                    autofillData()
                }
            }
        }

        viewModel.passwords.observe(viewLifecycleOwner) {
            adapter?.addPasswords(it)
        }
        viewModel.packageNameUpdateCompleted.observe(viewLifecycleOwner) {
            if (it) {
                autofillData()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter = SelectPasswordAdapter {
            selectedPassword = it
            binding.confirmSelectedPasswordButton.apply {
                isEnabled = true
            }
        }
        binding.selectablePasswordsRecyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        binding.selectablePasswordsRecyclerView.adapter = null
        adapter = null
    }

    private fun getPasswords() {
        val autofillController = activity as? AutofillController
        val packageName = autofillController?.getAutofillPackageName()

        viewModel.getPasswords(packageName)
    }

    private fun autofillData() {
        val autofillController = activity as? AutofillController

        selectedPassword?.let {
            autofillController?.finishWithSuccess(
                promptMessage = it.platformName,
                autofillValue = it.password
            )
        }
    }
}
