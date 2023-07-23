package com.mateuszholik.passwordgenerator.ui.createpin

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentCreatePinBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePinFragment : BaseFragment(R.layout.fragment_create_pin) {

    private val binding by viewBinding(FragmentCreatePinBinding::bind)
    private val viewModel: CreatePinViewModel by viewModel()
    private val snackBarProvider: SnackBarProvider by inject()

    override val isBottomNavVisible: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpKeyboard()
    }

    private fun setUpObservers() {
        with(viewModel) {
            wrongPin.observe(viewLifecycleOwner) { message ->
                activity?.let { snackBarProvider.showError(message, it) }
                binding.pinCode.animateFailure()
            }
            errorOccurred.observe(viewLifecycleOwner) { message ->
                activity?.let { snackBarProvider.showError(message, it) }
            }
            savedPinSuccessfully.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.action_createPinFragment_to_logged_user_nav)
                }
            }
        }
    }

    private fun setUpKeyboard() {
        binding.run {
            keyboard.doOnNumberClicked = { value -> pinCode.addPinText(value.toString()) }
            keyboard.doOnUndoClicked = {
                pinCode.removeTextFromPin()
                pinCode.setDefaultStyle()
            }
            keyboard.doOnConfirmedClicked = { viewModel.savePinIfCorrect(pinCode.pin) }
        }
    }
}
