package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordvalidationresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordValidationResultBinding
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PasswordValidationResultFragment : Fragment() {

    private lateinit var binding: FragmentPasswordValidationResultBinding

    private val password: String by lazy {
        requireArguments().getString(PASSWORD_KEY, EMPTY_STRING)
    }

    private val viewModel: PasswordValidationResultViewModel by viewModel {
        parametersOf(password)
    }

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

    companion object {
        private const val PASSWORD_KEY = "PASSWORD_KEY"
        fun newInstance(password: String) = PasswordValidationResultFragment().apply {
            arguments = Bundle().apply {
                putString(PASSWORD_KEY, password)
            }
        }
    }
}