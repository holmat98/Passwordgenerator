package com.mateuszholik.passwordgenerator.ui.imports

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.mateuszholik.domain.utils.ImportExportUtils.BINARY_DATA_MIME_TYPE
import com.mateuszholik.domain.utils.ImportExportUtils.PLAIN_TEXT_MIME_TYPE
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentImportPasswordsBinding
import com.mateuszholik.passwordgenerator.extensions.showDialog
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
        setUpMenu()

        viewModel.importResult.observe(viewLifecycleOwner) { navigateToResultScreen(it) }
    }

    private fun setUpMenu() {
        activity?.let {
            val menuHost = it as MenuHost

            menuHost.addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                        menuInflater.inflate(R.menu.info_menu, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        if (menuItem.itemId == R.id.importInfo) {
                            showInfoDialog()
                        }

                        return true
                    }
                },
                viewLifecycleOwner,
                Lifecycle.State.RESUMED
            )
        }
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

    private fun showInfoDialog() {
        showDialog(
            titleRes = R.string.settings_info_dialog_title,
            messageRes = R.string.settings_info_dialog_message,
            positiveButtonRes = R.string.button_ok,
            doOnPositiveButton = { }
        )
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
