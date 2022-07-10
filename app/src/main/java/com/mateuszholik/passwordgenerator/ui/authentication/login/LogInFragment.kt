package com.mateuszholik.passwordgenerator.ui.authentication.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.callbacks.BiometricAuthenticationCallback
import com.mateuszholik.passwordgenerator.databinding.FragmentLogInBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.managers.BiometricManager
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class LogInFragment : Fragment(R.layout.fragment_log_in) {

    private val binding by viewBinding(FragmentLogInBinding::bind)
    private val viewModel: LogInViewModel by viewModel()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))
    private val biometricAuthenticationCallback: BiometricAuthenticationCallback by inject {
        parametersOf(::goToLoggedUserScreen)
    }
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
            }
            keyboard.doOnConfirmedClicked = { viewModel.logIn(pinCode.pin) }
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            loginFailed.observe(viewLifecycleOwner) {
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
            biometricManager.showBiometricPrompt(it, biometricAuthenticationCallback)
        }
    }

    private fun goToLoggedUserScreen() {
        findNavController().navigate(R.id.action_authenticationHostFragment_to_logged_user_nav)
    }

    companion object {
        fun newInstance() = LogInFragment()
    }
}