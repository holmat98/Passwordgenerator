package com.mateuszholik.passwordgenerator.ui.imports

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.mateuszholik.domain.utils.ImportExportUtils.MIME_TYPE
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentImportPasswordsBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImportPasswordsFragment : BaseFragment(R.layout.fragment_import_passwords) {

    private val binding by viewBinding(FragmentImportPasswordsBinding::bind)
    private val viewModel: ImportPasswordsViewModel by viewModel()
    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
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
            description.text = context?.getString(R.string.import_screen_description)
        }
    }

    private fun setUpButton() {
        binding.confirmButton.setOnClickListener {
            filePickerLauncher.launch(MIME_TYPE)
        }
    }

    private fun navigateToResultScreen(wasImportSuccessful: Boolean) {
        val action =
            ImportPasswordsFragmentDirections.actionImportPasswordsFragmentToResult(
                wasImportSuccessful,
                R.string.import_result_screen_success,
                R.string.import_result_screen_failure
            )
        findNavController().navigate(action)
    }
}