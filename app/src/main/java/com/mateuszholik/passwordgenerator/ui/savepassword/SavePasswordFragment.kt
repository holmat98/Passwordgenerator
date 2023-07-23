package com.mateuszholik.passwordgenerator.ui.savepassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentSavePasswordBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SavePasswordFragment : Fragment(R.layout.fragment_save_password) {

    private val binding by viewBinding(FragmentSavePasswordBinding::bind)
    private val args: SavePasswordFragmentArgs by navArgs()
    private val viewModel: SavePasswordViewModel by viewModel {
        parametersOf(args.password)
    }
    private val snackBarProvider: SnackBarProvider by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@SavePasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        with(viewModel) {
            savePasswordSuccess.observe(viewLifecycleOwner) { message ->
                activity?.let { snackBarProvider.showSuccess(message, it) }
                findNavController().navigate(R.id.action_savePasswordFragment_to_passwords)
            }
            errorOccurred.observe(viewLifecycleOwner) { message ->
                activity?.let { snackBarProvider.showError(message, it) }
            }
        }
    }
}
