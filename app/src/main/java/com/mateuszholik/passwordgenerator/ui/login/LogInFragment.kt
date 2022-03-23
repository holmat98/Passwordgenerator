package com.mateuszholik.passwordgenerator.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.databinding.FragmentLogInBinding
import com.mateuszholik.passwordgenerator.ui.loggeduser.UserActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel: LogInViewModel by viewModel()

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
                openUserActivity()
            } else {
                binding.pinCode.animateFailure()
            }
        }
    }

    private fun openUserActivity() {
        UserActivity.newInstance(requireContext())
        requireActivity().finish()
    }
}