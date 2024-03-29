package com.mateuszholik.passwordgenerator.ui.export

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentExportPasswordsBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExportPasswordsFragment : BaseFragment(R.layout.fragment_export_passwords) {

    private val binding by viewBinding(FragmentExportPasswordsBinding::bind)
    private val viewModel: ExportPasswordsViewModel by viewModel()

    override val isBottomNavVisible: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ExportPasswordsFragment.viewModel
        }

        setUpViewTexts()

        viewModel.exportResult.observe(viewLifecycleOwner) { navigateToResultScreen(it) }
    }

    private fun setUpViewTexts() {
        with(binding.exportForm) {
            header.text = context?.getString(R.string.export_screen_header)
            description.text = context?.getString(R.string.export_screen_description)
        }
    }

    private fun navigateToResultScreen(wasExportSuccessful: Boolean) {
        val action =
            ExportPasswordsFragmentDirections.actionExportPasswordsFragmentToResult(
                wasExportSuccessful,
                if (wasExportSuccessful) {
                    R.string.export_result_screen_success_header
                } else {
                    R.string.export_result_screen_failure_header
                },
                if (wasExportSuccessful) {
                    R.string.export_result_screen_success_description
                } else {
                    R.string.export_result_screen_failure_description
                }
            )
        findNavController().navigate(action)
    }
}
