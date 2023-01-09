package com.mateuszholik.passwordgenerator.ui.passwordscore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordScoreBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.mappers.PasswordValidationTypeToTextMapper
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.adapters.PasswordValidationAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PasswordScoreFragment : Fragment(R.layout.fragment_password_score) {

    private val binding by viewBinding(FragmentPasswordScoreBinding::bind)
    private val args: PasswordScoreFragmentArgs by navArgs()
    private val viewModel: PasswordScoreViewModel by viewModel {
        parametersOf(args.password)
    }
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))
    private val validationTypeToTextMapper: PasswordValidationTypeToTextMapper by inject()
    private var adapter: PasswordValidationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            password = args.password
            viewModel = this@PasswordScoreFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setUpRecyclerView()
        setUpObservers()
    }

    private fun setUpRecyclerView() {
        adapter = PasswordValidationAdapter { validationTypeToTextMapper.map(it) }
        binding.passwordValidationResultRV.adapter = adapter
    }

    private fun setUpObservers() = with(viewModel) {
        errorOccurred.observe(viewLifecycleOwner) {
            messageProvider.show(it)
            findNavController().popBackStack()
        }
        validationResult.observe(viewLifecycleOwner) { adapter?.addValidationResult(it) }
    }
}