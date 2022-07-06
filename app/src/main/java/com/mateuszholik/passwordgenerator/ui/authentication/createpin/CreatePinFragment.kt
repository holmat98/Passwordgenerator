package com.mateuszholik.passwordgenerator.ui.authentication.createpin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentCreatePinBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class CreatePinFragment : Fragment() {

    private var binding: FragmentCreatePinBinding? = null
    private val viewModel: CreatePinViewModel by viewModel()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePinBinding.inflate(
            inflater,
            container,
            false
        )

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpKeyboard()
        setUpObservers()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setUpKeyboard() {
        binding?.run {
            keyboard.doOnNumberClicked = { value -> pinCode.addPinText(value.toString()) }
            keyboard.doOnUndoClicked = {
                pinCode.removeTextFromPin()
                pinCode.setDefaultStyle()
            }
            keyboard.doOnConfirmedClicked = { viewModel.savePinIfCorrect(pinCode.pin) }
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            pinCreateSuccess.observe(viewLifecycleOwner) {
                goToLoggedUserScreen()
            }
            errorOccurred.observe(viewLifecycleOwner) {
                messageProvider.show(it)
            }
            wrongPin.observe(viewLifecycleOwner) {
                messageProvider.show(it)
                binding?.pinCode?.animateFailure()
            }
        }
    }

    private fun goToLoggedUserScreen() {
        findNavController().navigate(R.id.action_authenticationHostFragment_to_logged_user_nav)
    }

    companion object {
        fun newInstance() = CreatePinFragment()
    }
}