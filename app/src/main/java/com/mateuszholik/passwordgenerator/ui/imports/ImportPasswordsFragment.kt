package com.mateuszholik.passwordgenerator.ui.imports

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.mateuszholik.domain.utils.ImportExportUtils.BINARY_DATA_MIME_TYPE
import com.mateuszholik.domain.utils.ImportExportUtils.PLAIN_TEXT_MIME_TYPE
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentImportPasswordsBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImportPasswordsFragment : BaseFragment(R.layout.fragment_import_passwords) {

    private val binding by viewBinding(FragmentImportPasswordsBinding::bind)
    private val viewModel: ImportPasswordsViewModel by viewModel()
    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { viewModel.importPasswords(it) }
        }

    override val isBottomNavVisible: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ImportPasswordsFragment.viewModel
        }

        setUpViewTexts()
        setUpButton()

        viewModel.importResult.observe(viewLifecycleOwner) { navigateToResultScreen(it) }
    }

    private fun setUpViewTexts() {
        with(binding.importForm) {
            header.text = context?.getString(R.string.import_screen_header)
            description.text = context?.getString(R.string.import_screen_description)
        }
    }

    private fun setUpButton() {
        binding.confirmButton.setOnClickListener {
            openFilePicker()
        }
    }

    private fun openFilePicker() {
        val mimeType = if (binding.importForm.shouldDataBeEncryptedSwitch.isChecked) {
            BINARY_DATA_MIME_TYPE
        } else {
            PLAIN_TEXT_MIME_TYPE
        }

        filePickerLauncher.launch(mimeType)
    }
    private fun navigateToResultScreen(wasImportSuccessful: Boolean) {
        val action =
            ImportPasswordsFragmentDirections.actionImportPasswordsFragmentToResult(
                wasImportSuccessful,
                if (wasImportSuccessful) {
                    R.string.import_result_screen_success_header
                } else {
                    R.string.import_result_screen_failure_header
                },
                if (wasImportSuccessful) {
                    R.string.import_result_screen_success_description
                } else {
                    R.string.import_result_screen_failure_description
                }
            )
        findNavController().navigate(action)
    }
}
