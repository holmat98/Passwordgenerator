package com.mateuszholik.passwordgenerator.ui.passworddetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.data.repositories.models.PasswordDetails
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordDetailsBinding
import com.mateuszholik.passwordgenerator.extensions.showDialog
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.mappers.PasswordValidationTypeToTextMapper
import com.mateuszholik.passwordgenerator.providers.SnackBarProvider
import com.mateuszholik.passwordgenerator.ui.adapters.PasswordValidationAdapter
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment.ButtonSetup
import com.mateuszholik.passwordgenerator.ui.dialogs.CustomBottomSheetDialogFragment.Listener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PasswordDetailsFragment : Fragment(R.layout.fragment_password_details) {

    private val navArgs: PasswordDetailsFragmentArgs by navArgs()
    private val typeToTextMapper: PasswordValidationTypeToTextMapper by inject()
    private val snackBarProvider: SnackBarProvider by inject()
    private val viewModel: PasswordDetailsViewModel by viewModel {
        parametersOf(navArgs.passwordId)
    }
    private val binding by viewBinding(FragmentPasswordDetailsBinding::bind)
    private var adapter: PasswordValidationAdapter? = null
    private var currentPasswordDetails: PasswordDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@PasswordDetailsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setUpRecyclerView()
        setUpObservers()
        setUpMenu()
    }

    private fun setUpMenu() {
        activity?.let {
            val menuHost = it as MenuHost

            menuHost.addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                        menuInflater.inflate(R.menu.options_menu, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        if (menuItem.itemId == R.id.menuOptions) {
                            showOptionsDialog()
                        }

                        return true
                    }
                },
                viewLifecycleOwner,
                Lifecycle.State.RESUMED
            )
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
                        currentPasswordDetails?.let { password ->
                            val action =
                                PasswordDetailsFragmentDirections.actionPasswordDetailsFragmentToEditPasswordFragment(
                                    password.id
                                )
                            findNavController().navigate(action)
                        }
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
            passwordDetails.observe(viewLifecycleOwner) {
                currentPasswordDetails = it
                binding.passwordInfoView.passwordValidity = it.passwordValidity
            }
            passwordValidationResult.observe(viewLifecycleOwner) {
                adapter?.addValidationResult(it)
            }
            passwordDeletedSuccessfully.observe(viewLifecycleOwner) {
                if (it) {
                    activity?.let { activity ->
                        snackBarProvider.showSuccess(
                            activity.getString(R.string.password_details_password_deleted),
                            activity
                        )
                    }
                    findNavController().popBackStack()
                }
            }
            errorOccurred.observe(viewLifecycleOwner) { message ->
                activity?.let { snackBarProvider.showError(message, it) }
            }
        }
    }

    private companion object {
        const val OPTIONS_DIALOG_TAG = "OPTIONS_DIALOG_TAG"
    }
}
