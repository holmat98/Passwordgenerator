package com.mateuszholik.passwordgenerator.ui.authentication.biometric

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.callbacks.BiometricAuthenticationCallback
import com.mateuszholik.passwordgenerator.databinding.FragmentBiometricAuthenticationBinding
import com.mateuszholik.passwordgenerator.factories.BiometricPromptFactory
import com.mateuszholik.passwordgenerator.factories.CancellationSignalFactory
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class BiometricAuthenticationFragment : BaseFragment() {

    override val isBottomNavVisible: Boolean = false

    private lateinit var binding: FragmentBiometricAuthenticationBinding

    private val biometricPromptFactory: BiometricPromptFactory by inject()
    private val cancellationSignalFactory: CancellationSignalFactory by inject()
    private val biometricAuthenticationCallback: BiometricAuthenticationCallback by inject {
        parametersOf(::goToNextScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBiometricAuthenticationBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBiometricPrompt()

        binding.authenticateWithBiometricBtn.setOnClickListener {
            showBiometricPrompt()
        }
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_biometricAuthenticationFragment_to_logged_user_nav)
    }

    private fun showBiometricPrompt() {
        activity?.let {
            val biometricPrompt = biometricPromptFactory.create(it)

            biometricPrompt.authenticate(
                cancellationSignalFactory.create(),
                it.mainExecutor,
                biometricAuthenticationCallback
            )
        }
    }
}