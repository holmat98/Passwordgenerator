package com.mateuszholik.passwordgenerator.ui.generatepassword

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentGeneratePasswordBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment.ButtonSetup
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment.Listener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeneratePasswordFragment : BaseFragment(R.layout.fragment_generate_password) {

    private val binding by viewBinding(FragmentGeneratePasswordBinding::bind)
    private val viewModel: GeneratePasswordViewModel by viewModel()
    private val snackBarProvider: SnackBarProvider by inject()
    private val clipboardManager: ClipboardManager by inject()

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@GeneratePasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setUpObservers()
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearGeneratedPassword()
    }

    private fun showBottomSheetDialog(text: String) {
        activity?.supportFragmentManager?.let {
            CustomBottomSheetDialogFragment.newInstance(
                title = text,
                firstButtonSetup = ButtonSetup(
                    iconResId = R.drawable.ic_copy,
                    textResId = R.string.button_copy
                ),
                secondButtonSetup = ButtonSetup(
                    iconResId = R.drawable.ic_save,
                    textResId = R.string.dialog_button_save
                ),
                listener = object : Listener {
                    override fun onFirstButtonClicked() {
                        clipboardManager.copyToClipboard(
                            label = COPIED_PASSWORD_LABEL,
                            text = text
                        )
                    }

                    override fun onSecondButtonClicked() {
                        goToSavePasswordScreen(text)
                    }

                    override fun onThirdButtonClicked() {}
                }
            ).show(it, BOTTOM_SHEET_DIALOG_TAG)
        }
    }

    private fun goToSavePasswordScreen(password: String?) {
        val action =
            GeneratePasswordFragmentDirections.actionGeneratePasswordToSavePasswordFragment(password)
        findNavController().navigate(action)
    }

    private fun setUpObservers() {
        viewModel.errorOccurred.observe(viewLifecycleOwner) { message ->
            activity?.let { snackBarProvider.showError(message, it) }
        }
        viewModel.generatedPassword.observe(viewLifecycleOwner) {generatedPassword ->
            generatedPassword?.let { showBottomSheetDialog(it) }
        }
    }

    private companion object {
        const val COPIED_PASSWORD_LABEL = "Password"
        const val BOTTOM_SHEET_DIALOG_TAG = "GENERATED_PASSWORD_BOTTOM_SHEET_DIALOG_TAG"
    }
}
