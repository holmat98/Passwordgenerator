package com.mateuszholik.passwordgenerator.ui.passwordscore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordScoreBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.extensions.removeFragment
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.passworddetails.PasswordDetailsFragment
import com.mateuszholik.passwordgenerator.ui.passwordvalidationresult.PasswordValidationResultFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PasswordScoreFragment : Fragment() {

    private var binding: FragmentPasswordScoreBinding? = null
    private val args: PasswordScoreFragmentArgs by navArgs()
    private val viewModel: PasswordScoreViewModel by viewModel {
        parametersOf(args.password)
    }
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPasswordScoreBinding>(
            inflater,
            R.layout.fragment_password_score,
            container,
            false
        ).apply {
            password = args.password
            viewModel = this@PasswordScoreFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorOccurred.observe(viewLifecycleOwner) {
            messageProvider.show(it)
            findNavController().popBackStack()
        }

        displayPasswordValidationResultFragment()
    }

    override fun onDestroyView() {
        binding?.let {
            activity?.removeFragment(
                it.passwordValidationResult.id,
                VALIDATION_RESULT_FRAGMENT_TAG
            )
        }
        binding = null
        super.onDestroyView()
    }

    private fun displayPasswordValidationResultFragment() {
        val binding = binding ?: return
        requireActivity().supportFragmentManager.commit {
            replace(
                binding.passwordValidationResult.id,
                PasswordValidationResultFragment.newInstance(args.password),
                VALIDATION_RESULT_FRAGMENT_TAG
            )
        }
    }

    private companion object {
        const val VALIDATION_RESULT_FRAGMENT_TAG = "VALIDATION_RESULT_FRAGMENT"
    }
}