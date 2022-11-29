package com.mateuszholik.passwordgenerator.ui.passwords

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordsBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.factories.ViewHolderFactory
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import com.mateuszholik.passwordgenerator.ui.passwords.adapters.PasswordsAdapter
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PasswordsFragment : BaseFragment(R.layout.fragment_passwords) {

    private val binding by viewBinding(FragmentPasswordsBinding::bind)
    private val viewModel: PasswordsViewModel by viewModel()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))
    private val clipboardManager: ClipboardManager by inject()
    private val gsonFactory: GsonFactory by inject()
    private val viewHolderFactory: ViewHolderFactory by inject()
    private var adapter: PasswordsAdapter? = null

    override val isBottomNavVisible: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@PasswordsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.goToCreatePasswordScreenBtn.setOnClickListener {
            navigateToCreatePasswordScreen()
        }

        setUpObservers()
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
        viewModel.getAllPasswords()
    }

    override fun onPause() {
        super.onPause()
        binding.recyclerView.adapter = null
        adapter = null
    }

    private fun setUpRecyclerView() {
        adapter = PasswordsAdapter(
            copyToClipboard = { label, password ->
                clipboardManager.copyToClipboard(label, password)
            },
            navigateToPasswordDetails = { navigateToPasswordDetails(it) },
            createPasswordViewHolder = { viewGroup, viewType ->
                viewHolderFactory.create(
                    viewGroup,
                    viewType
                )
            }
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setUpObservers() {
        with(viewModel) {
            passwords.observe(viewLifecycleOwner) { adapter?.addPasswords(it) }
            errorOccurred.observe(viewLifecycleOwner) { messageProvider.show(it) }
        }
    }

    private fun navigateToPasswordDetails(password: Password) {
        val passwordJson = gsonFactory.create().toJson(password)
        val action =
            PasswordsFragmentDirections.actionPasswordsToPasswordDetailsFragment(passwordJson)
        findNavController().navigate(action)
    }

    private fun navigateToCreatePasswordScreen() {
        val action = PasswordsFragmentDirections.actionPasswordsToSavePasswordFragment(EMPTY_STRING)
        findNavController().navigate(action)
    }
}