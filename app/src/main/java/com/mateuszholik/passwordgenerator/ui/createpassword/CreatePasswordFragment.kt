package com.mateuszholik.passwordgenerator.ui.createpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.databinding.FragmentCreatePasswordBinding
import com.mateuszholik.passwordgenerator.ui.loggeduser.UserActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreatePasswordBinding
    private val viewModel: CreatePasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePasswordBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpKeyboard()
        setUpObservers()
    }

    private fun setUpKeyboard() {
        with(binding) {
            keyboard.doOnNumberClicked = { value -> pinCode.addPinText(value.toString()) }
            keyboard.doOnUndoClicked = {
                pinCode.removeTextFromPin()
                pinCode.setDefaultStyle()
            }
            keyboard.doOnConfirmedClicked = { viewModel.createPin(pinCode.pin) }
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            pinCreateSuccess.observe(viewLifecycleOwner) {
                openUserActivity()
            }
            pinCreateError.observe(viewLifecycleOwner) {
                binding.pinCode.animateFailure()
            }
        }
    }

    private fun openUserActivity() {
        UserActivity.newInstance(requireContext())
        requireActivity().finish()
    }
}