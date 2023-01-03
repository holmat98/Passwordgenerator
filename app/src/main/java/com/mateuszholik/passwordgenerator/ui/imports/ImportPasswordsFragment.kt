package com.mateuszholik.passwordgenerator.ui.imports

import android.os.Bundle
import android.view.View
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentImportPasswordsBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImportPasswordsFragment : BaseFragment(R.layout.fragment_import_passwords) {

    private val binding by viewBinding(FragmentImportPasswordsBinding::bind)
    private val viewModel: ImportPasswordsViewModel by viewModel()

    override val isBottomNavVisible: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ImportPasswordsFragment.viewModel
        }

        setUpViewTexts()
    }

    private fun setUpViewTexts() {
        with(binding.importForm) {
            description.text = context?.getString(R.string.import_screen_description)
            encryptionPasswordDescription.text =
                context?.getString(R.string.import_screen_encryption_password_description)
        }
    }
}