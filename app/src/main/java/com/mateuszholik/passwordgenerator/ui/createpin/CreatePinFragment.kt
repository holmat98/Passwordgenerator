package com.mateuszholik.passwordgenerator.ui.createpin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentCreatePinBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePinFragment : BaseFragment() {

    private lateinit var binding: FragmentCreatePinBinding
    private val viewModel: CreatePinViewModel by viewModel()

    override val isBottomNavVisible: Boolean
        get() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePinBinding.inflate(
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
                goToLoggedUserScreen()
            }
            pinCreateError.observe(viewLifecycleOwner) {
                binding.pinCode.animateFailure()
            }
        }
    }

    private fun goToLoggedUserScreen() {
        findNavController().navigate(R.id.action_createPinFragment_to_logged_user_nav)
    }
}