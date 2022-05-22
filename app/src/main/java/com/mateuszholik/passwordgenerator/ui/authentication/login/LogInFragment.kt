package com.mateuszholik.passwordgenerator.ui.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mateuszholik.domain.models.PinState
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentLogInBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.authentication.AuthenticationHostViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel: LogInViewModel by viewModel()
    private val hostViewModel: AuthenticationHostViewModel by sharedViewModel()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpKeyboard()
    }

    private fun setUpKeyboard() {
        with(binding) {
            keyboard.doOnNumberClicked = { value -> pinCode.addPinText(value.toString()) }
            keyboard.doOnUndoClicked = {
                pinCode.removeTextFromPin()
                pinCode.setDefaultStyle()
            }
            keyboard.doOnConfirmedClicked = { viewModel.logIn(binding.pinCode.pin) }
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            logInResult.observe(viewLifecycleOwner) {
                when (it) {
                    PinState.CORRECT -> viewModel.getIfShouldUseBiometricAuth()
                    PinState.WRONG -> binding.pinCode.animateFailure()
                    else -> error("Illegal argument")
                }
            }
            errorOccurred.observe(viewLifecycleOwner) {
                messageProvider.show(it)
            }
            shouldUseBiometricAuthentication.observe(viewLifecycleOwner) {
                if (it) {
                    goToBiometricAuthenticationScreen()
                } else {
                    goToLoggedUserScreen()
                }
            }
        }
    }

    private fun goToLoggedUserScreen() {
        findNavController().navigate(R.id.action_authenticationHostFragment_to_logged_user_nav)
    }

    private fun goToBiometricAuthenticationScreen() {
        hostViewModel.changeScreen(AuthenticationScreens.BIOMETRIC_LOG_IN)
    }

    companion object {
        fun newInstance() = LogInFragment()
    }
}