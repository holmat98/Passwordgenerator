package com.mateuszholik.passwordgenerator.ui.login

import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentLogInBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.managers.BiometricManager
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class LogInFragment : BaseFragment(R.layout.fragment_log_in) {

    private val binding by viewBinding(FragmentLogInBinding::bind)
    private val viewModel: LogInViewModel by viewModel()
    private val messageProvider: MessageProvider by inject(named(NamedConstants.TOAST_MESSAGE_PROVIDER))
    private val textProvider: TextProvider by inject()
    private val biometricManager: BiometricManager by inject()

    override val isBottomNavVisible: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@LogInFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setUpObservers()
        setUpKeyboard()
    }

    private fun setUpKeyboard() {
        binding.run {
            keyboard.doOnNumberClicked = { value -> pinCode.addPinText(value.toString()) }
            keyboard.doOnUndoClicked = {
                pinCode.removeTextFromPin()
                pinCode.setDefaultStyle()
            }
            keyboard.doOnConfirmedClicked = { viewModel?.onSubmitButtonClicked(pinCode.pin) }
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            wrongPin.observe(viewLifecycleOwner) {
                messageProvider.show(it)
                binding.pinCode.animateFailure()
            }
            errorOccurred.observe(viewLifecycleOwner) {
                messageProvider.show(it)
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
                biometricAuthenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)

                        goToLoggedUserScreen()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)

                        messageProvider.show(textProvider.provide(MessageType.BIOMETRIC_AUTH_ERROR))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                        messageProvider.show(textProvider.provide(MessageType.BIOMETRIC_AUTH_FAILED))
                    }
                }
            )
        }
    }

    private fun goToLoggedUserScreen() {
        findNavController().navigate(R.id.action_logInFragment_to_logged_user_nav)
    }
}
