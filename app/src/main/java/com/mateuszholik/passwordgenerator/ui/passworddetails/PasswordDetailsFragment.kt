package com.mateuszholik.passwordgenerator.ui.passworddetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordDetailsBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.extensions.showDialog
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.mappers.PasswordValidationTypeToTextMapper
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.adapters.PasswordValidationAdapter
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment.ButtonSetup
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment.Listener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PasswordDetailsFragment : Fragment(R.layout.fragment_password_details) {

    private val navArgs: PasswordDetailsFragmentArgs by navArgs()
    private val gsonFactory: GsonFactory by inject()
    private val typeToTextMapper: PasswordValidationTypeToTextMapper by inject()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))
    private val password: Password by lazy {
        gsonFactory.create().fromJson(navArgs.password, Password::class.java)
    }
    private val viewModel: PasswordDetailsViewModel by viewModel {
        parametersOf(password)
    }
    private val binding by viewBinding(FragmentPasswordDetailsBinding::bind)
    private var adapter: PasswordValidationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            password = this@PasswordDetailsFragment.password
            viewModel = this@PasswordDetailsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.passwordInfoView.date = password.expiringDate

        setUpRecyclerView()
        setUpObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuOptions) {
            showOptionsDialog()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerView() {
        adapter = PasswordValidationAdapter { typeToTextMapper.map(it) }
        binding.passwordValidationResultRV.adapter = adapter
    }

    private fun showOptionsDialog() {
        activity?.supportFragmentManager?.let {
            CustomBottomSheetDialogFragment.newInstance(
                title = context?.getString(R.string.options).orEmpty(),
                firstButtonSetup = ButtonSetup(
                    iconResId = R.drawable.ic_copy,
                    textResId = R.string.button_copy
                ),
                secondButtonSetup = ButtonSetup(
                    iconResId = R.drawable.ic_edit,
                    textResId = R.string.button_edit
                ),
                thirdButtonSetup = ButtonSetup(
                    iconResId = R.drawable.ic_delete,
                    textResId = R.string.button_delete
                ),
                listener = object : Listener {
                    override fun onFirstButtonClicked() {
                        viewModel.copyPasswordToClipboard()
                    }

                    override fun onSecondButtonClicked() {
                        val passwordJson = gsonFactory.create().toJson(password)
                        val action =
                            PasswordDetailsFragmentDirections.actionPasswordDetailsFragmentToEditPasswordFragment(
                                passwordJson
                            )
                        findNavController().navigate(action)
                    }

                    override fun onThirdButtonClicked() {
                        showDialog(
                            titleRes = R.string.password_details_delete_password_title,
                            messageRes = R.string.password_details_delete_password_message,
                            negativeButtonRes = R.string.dialog_button_cancel
                        ) { viewModel.deletePassword() }
                    }
                }
            ).show(it, OPTIONS_DIALOG_TAG)
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            passwordValidationResult.observe(viewLifecycleOwner) {
                adapter?.addValidationResult(it)
            }
            passwordDeletedSuccessfully.observe(viewLifecycleOwner) {
                if (it) {
                    messageProvider.show(requireContext().getString(R.string.password_details_password_deleted))
                    findNavController().popBackStack()
                }
            }
            errorOccurred.observe(viewLifecycleOwner) {
                messageProvider.show(it)
            }
        }
    }

    private companion object {
        const val OPTIONS_DIALOG_TAG = "OPTIONS_DIALOG_TAG"
    }
}
