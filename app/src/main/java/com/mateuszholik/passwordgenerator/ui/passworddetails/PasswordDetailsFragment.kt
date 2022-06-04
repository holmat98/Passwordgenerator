package com.mateuszholik.passwordgenerator.ui.passworddetails

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
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordDetailsBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.extensions.showDialog
import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.passwordvalidationresult.PasswordValidationResultFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PasswordDetailsFragment : Fragment() {

    private val navArgs: PasswordDetailsFragmentArgs by navArgs()
    private val gsonFactory: GsonFactory by inject()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))
    private val password: Password by lazy {
        gsonFactory.create().fromJson(navArgs.password, Password::class.java)
    }
    private val viewModel: PasswordDetailsViewModel by viewModel {
        parametersOf(password)
    }
    private lateinit var binding: FragmentPasswordDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentPasswordDetailsBinding?>(
            inflater,
            R.layout.fragment_password_details,
            container,
            false
        ).apply {
            password = this@PasswordDetailsFragment.password
            viewModel = this@PasswordDetailsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayPasswordValidationResultFragment()
        setUpPasswordActionButtons()
        setUpObservers()
    }

    private fun displayPasswordValidationResultFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                binding.passwordValidationResult.id,
                PasswordValidationResultFragment.newInstance(password.password)
            )
            .commit()
    }

    private fun setUpPasswordActionButtons() {
        binding.deletePasswordBtn.setOnClickListener {
            showDialog(
                titleRes = R.string.password_details_delete_password_title,
                messageRes = R.string.password_details_delete_password_message,
                negativeButtonRes = R.string.dialog_button_cancel
            ) { viewModel.deletePassword() }
        }
        binding.editPasswordBtn.setOnClickListener {
            val passwordJson = gsonFactory.create().toJson(password)
            val action =
                PasswordDetailsFragmentDirections.actionPasswordDetailsFragmentToEditPasswordFragment(
                    passwordJson
                )
            findNavController().navigate(action)
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            passwordDeletedSuccessfully.observe(viewLifecycleOwner) {
                if (it) {
                    messageProvider.show(R.string.password_details_password_deleted)
                    findNavController().popBackStack()
                }
            }
            errorOccurred.observe(viewLifecycleOwner) {
                messageProvider.show(it)
            }
        }
    }
}