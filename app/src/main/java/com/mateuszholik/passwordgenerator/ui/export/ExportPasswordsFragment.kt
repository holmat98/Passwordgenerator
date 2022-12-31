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

        viewModel.exportResult.observe(viewLifecycleOwner) { navigateToExportResult(it) }
    }

    private fun navigateToExportResult(wasExportSuccessful: Boolean) {
        val action =
            ExportPasswordsFragmentDirections.actionExportPasswordsFragmentToPasswordsExportResult(
                wasExportSuccessful
            )
        findNavController().navigate(action)
    }
}