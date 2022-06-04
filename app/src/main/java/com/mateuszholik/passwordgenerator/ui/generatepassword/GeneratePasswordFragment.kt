package com.mateuszholik.passwordgenerator.ui.generatepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentGeneratePasswordBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class GeneratePasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentGeneratePasswordBinding
    private val viewModel: GeneratePasswordViewModel by viewModel()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentGeneratePasswordBinding>(
            inflater,
            R.layout.fragment_generate_password,
            container,
            false
        ).apply {
            viewModel = this@GeneratePasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToSavePasswordScreenButton.setOnClickListener {
            goToSavePasswordScreen(binding.generatedPasswordTV.text.toString())
        }

        setUpObservers()
    }

    private fun goToSavePasswordScreen(password: String?) {
        val action =
            GeneratePasswordFragmentDirections.actionGeneratePasswordToSavePasswordFragment(password)
        findNavController().navigate(action)
    }

    private fun setUpObservers() {
        viewModel.errorOccurred.observe(viewLifecycleOwner) {
            messageProvider.show(it)
        }
    }
}