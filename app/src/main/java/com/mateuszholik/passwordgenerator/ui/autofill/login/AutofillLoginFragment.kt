package com.mateuszholik.passwordgenerator.ui.autofill.login

import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentLogInBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.managers.BiometricManager
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.login.LogInViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AutofillLoginFragment : Fragment(R.layout.fragment_log_in) {

    private val binding by viewBinding(FragmentLogInBinding::bind)
    private val viewModel: LogInViewModel by viewModel()
    private val snackBarProvider: SnackBarProvider by inject()
    private val textProvider: TextProvider by inject()
    private val biometricManager: BiometricManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpKeyboard()
    }

    private fun setUpKeyboard() {
        binding.run {
            keyboard.doOnNumberClicked = { value -> pinCode.addPinText(value.toString()) }
            keyboard.doOnUndoClicked = {
                pinCode.removeTextFromPin()
                pinCode.setDefaultStyle()
                keyboard.background =
                    ContextCompat.getDrawable(keyboard.context, R.drawable.view_background)
            }
            keyboard.doOnConfirmedClicked = { viewModel.logIn(pinCode.pin) }
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            wrongPin.observe(viewLifecycleOwner) {
                activity?.let { activity ->
                    snackBarProvider.showError(it, activity)
                }
                binding.pinCode.animateFailure()
                binding.keyboard.apply {
                    background =
                        ContextCompat.getDrawable(context, R.drawable.view_background_error)
                }
            }
            errorOccurred.observe(viewLifecycleOwner) {
                activity?.let { activity ->
                    snackBarProvider.showError(it, activity)
                }
            }
            shouldUseBiometricAuthentication.observe(viewLifecycleOwner) {
                if (it) {
                    showBiometricPrompt()
                } else {
                    goToLoggedUserScreen()
                }
            }
        }
    }

    private fun showBiometricPrompt() {
        activity?.let {
            biometricManager.showBiometricPrompt(
                activity = it,
                biometricAuthenticationCallback = object :
                    BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)

                        goToLoggedUserScreen()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)

                        activity?.let { activity ->
                            snackBarProvider.showError(
                                textProvider.provide(MessageType.BIOMETRIC_AUTH_ERROR),
                                activity
                            )
                        }
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                        activity?.let { activity ->
                            snackBarProvider.showError(
                                textProvider.provide(MessageType.BIOMETRIC_AUTH_ERROR),
                                activity
                            )
                        }
                    }
                }
            )
        }
    }

    private fun goToLoggedUserScreen() {
        findNavController().navigate(R.id.action_autofillLoginFragment_to_selectPasswordFragment)
    }
}
