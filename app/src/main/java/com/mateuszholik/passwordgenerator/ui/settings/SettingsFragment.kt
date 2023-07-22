package com.mateuszholik.passwordgenerator.ui.settings

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentSettingsBinding
import com.mateuszholik.passwordgenerator.extensions.showNumberPickerDialog
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.managers.BiometricManager
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override val isBottomNavVisible: Boolean = true

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModel()
    private val snackBarProvider: SnackBarProvider by inject()
    private val biometricManager: BiometricManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()

        binding.apply {
            viewModel = this@SettingsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            exportPasswordsButton.apply {
                onClick = {
                    findNavController().navigate(R.id.action_settings_to_exportPasswordsFragment)
                }
                setOnClickListener {
                    findNavController().navigate(R.id.action_settings_to_exportPasswordsFragment)
                }
            }

            importPasswordsButton.apply {
                onClick = {
                    findNavController().navigate(R.id.action_settings_to_importPasswordsFragment)
                }
                setOnClickListener {
                    findNavController().navigate(R.id.action_settings_to_importPasswordsFragment)
                }
            }

            passwordValidityButton.apply {
                onClick = { showPasswordValidityDialog() }
                setOnClickListener { showPasswordValidityDialog() }
            }

            goToLicensesButton.apply {
                onClick = {
                    findNavController().navigate(R.id.action_settings_to_licensesFragment)
                }
                setOnClickListener {
                    findNavController().navigate(R.id.action_settings_to_licensesFragment)
                }
            }

            activity?.let {
                shouldUseBiometricAuthBtn.isVisible = biometricManager.isBiometricAvailable(it)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.errorOccurred.observe(viewLifecycleOwner) { message ->
            activity?.let { snackBarProvider.showError(message, it) }
        }
    }

    private fun showPasswordValidityDialog() {
        showNumberPickerDialog(
            R.string.settings_dialog_password_validity,
            minValue = MIN_PASSWORD_VALIDITY_IN_DAYS,
            maxValue = MAX_PASSWORD_VALIDITY_IN_DAYS
        ) {
            viewModel.savePasswordValidity(it.toLong())
        }
    }

    private companion object {
        const val MIN_PASSWORD_VALIDITY_IN_DAYS = 30
        const val MAX_PASSWORD_VALIDITY_IN_DAYS = 120
    }
}
