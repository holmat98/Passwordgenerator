package com.mateuszholik.passwordgenerator.ui.editpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentEditPasswordBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class EditPasswordFragment : Fragment() {

    private lateinit var binding: FragmentEditPasswordBinding
    private val navArgs: EditPasswordFragmentArgs by navArgs()
    private val gsonFactory: GsonFactory by inject()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))
    private val password: Password by lazy {
        gsonFactory.create().fromJson(navArgs.password, Password::class.java)
    }
    private val viewModel: EditPasswordViewModel by viewModel {
        parametersOf(password)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentEditPasswordBinding?>(
            inflater,
            R.layout.fragment_edit_password,
            container,
            false
        ).apply {
            viewModel = this@EditPasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.run {
            passwordEditedCorrectly.observe(viewLifecycleOwner) {
                if (it) {
                    messageProvider.show(R.string.edit_password_edited_successfully)
                    findNavController().navigate(R.id.action_editPasswordFragment_to_passwords)
                }
            }
            errorOccurred.observe(viewLifecycleOwner) {
                messageProvider.show(R.string.error_message)
            }
        }
    }
}