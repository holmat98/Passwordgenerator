package com.mateuszholik.passwordgenerator.ui.savepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentSavePasswordBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SavePasswordFragment : Fragment() {

    private lateinit var binding: FragmentSavePasswordBinding
    private val args: SavePasswordFragmentArgs by navArgs()
    private val viewModel: SavePasswordViewModel by viewModel {
        parametersOf(args.password)
    }
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentSavePasswordBinding?>(
            inflater,
            R.layout.fragment_save_password,
            container,
            false
        ).apply {
            viewModel = this@SavePasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpObservers() {
        with(viewModel) {
            savedPassword.observe(viewLifecycleOwner) {
                messageProvider.show(it)
                findNavController().navigate(R.id.action_savePasswordFragment_to_passwords)
            }
            errorOccurred.observe(viewLifecycleOwner) {
                messageProvider.show(it)
            }
        }
    }
}