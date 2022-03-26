package com.mateuszholik.passwordgenerator.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentLogInBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : BaseFragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel: LogInViewModel by viewModel()

    override val isBottomNavVisible: Boolean
        get() = false

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
        viewModel.logInSuccess.observe(viewLifecycleOwner) {
            if (it) {
                goToLoggedUserScreen()
            } else {
                binding.pinCode.animateFailure()
            }
        }
    }

    private fun goToLoggedUserScreen() {
        findNavController().navigate(R.id.action_logInFragment_to_logged_user_nav)
    }
}