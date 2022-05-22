package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordvalidationresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordValidationResultBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PasswordValidationResultFragment : Fragment() {

    private lateinit var binding: FragmentPasswordValidationResultBinding

    private val password: String by lazy {
        requireArguments().getString(PASSWORD_KEY, EMPTY_STRING)
    }

    private val viewModel: PasswordValidationResultViewModel by viewModel {
        parametersOf(password)
    }
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentPasswordValidationResultBinding?>(
            inflater,
            R.layout.fragment_password_validation_result,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@PasswordValidationResultFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.errorOccurred.observe(viewLifecycleOwner) {
            messageProvider.show(it)
        }
    }

    companion object {
        private const val PASSWORD_KEY = "PASSWORD_KEY"
        fun newInstance(password: String) = PasswordValidationResultFragment().apply {
            arguments = Bundle().apply {
                putString(PASSWORD_KEY, password)
            }
        }
    }
}