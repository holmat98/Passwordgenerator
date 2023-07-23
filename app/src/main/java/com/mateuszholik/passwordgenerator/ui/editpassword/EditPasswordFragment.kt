package com.mateuszholik.passwordgenerator.ui.editpassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentEditPasswordBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditPasswordFragment : Fragment(R.layout.fragment_edit_password) {

    private val binding by viewBinding(FragmentEditPasswordBinding::bind)
    private val navArgs: EditPasswordFragmentArgs by navArgs()
    private val snackBarProvider: SnackBarProvider by inject()
    private val viewModel: EditPasswordViewModel by viewModel {
        parametersOf(navArgs.passwordId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@EditPasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.run {
            passwordEditedCorrectly.observe(viewLifecycleOwner) {
                if (it) {
                    activity?.let { activity ->
                        snackBarProvider.showSuccess(
                            activity.getString(R.string.edit_password_edited_successfully),
                            activity
                        )
                    }
                    findNavController().navigate(R.id.action_editPasswordFragment_to_passwords)
                }
            }
            errorOccurred.observe(viewLifecycleOwner) { message ->
                activity?.let { snackBarProvider.showError(message, it) }
            }
        }
    }
}
